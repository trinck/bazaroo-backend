package org.mts.announcesservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.dtos.FieldOutputDTO;
import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.enums.AdvertEventType;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.mts.announcesservice.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class AnnounceService implements IAnnounceService{

    private final AnnounceRepository repository;
    private final ElasticsearchOperations operations;
    private final ModelMapper mapper;
    private final StreamBridge bridge;
    @Value("${app.broker.topics.advert}")
    private String TOPIC_ADVERT;
    @Value("${app.broker.topics.ad-create-error}")
    private String TOPIC_AD_CREATE_ERROR;

    public AnnounceService(AnnounceRepository repository, ElasticsearchOperations operations, ModelMapper mapper, StreamBridge bridge) {
        this.repository = repository;
        this.operations = operations;
        this.mapper = mapper;
        this.bridge = bridge;
    }

    /**
     * @param announce
     * @return
     */
    @Override
    @Transactional
    public Announce create(Announce announce) {

        Announce saved = null;

        try {
            saved = this.repository.save(announce);
            AnnounceDocument docToSave = this.mapper.map(saved, AnnounceDocument.class);
            docToSave.getFields().forEach(FieldOutputDTO::prepareForIndexing);
            this.operations.save(docToSave);
        } catch (Exception e) {
            Map<String,Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("adId", announce.getId());
            log.error("Error saving announce {} with message {}",announce.getId(), e.getMessage());
            this.bridge.send(TOPIC_AD_CREATE_ERROR, map);
            throw new RuntimeException(e);
        }
        // send notification to broker
        this.bridge.send(this.TOPIC_ADVERT, Map.of("adId",saved.getId(), "type",AdvertEventType.NEW.name()));
        return saved;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Announce deleteById(String id) {

        Announce announce = this.repository.findById(id).orElseThrow();
        this.repository.deleteById(id);
        return announce;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Announce getByID(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    /**
     * @param ids
     * @return
     */
    @Override
    public List<Announce> getByIds(List<String> ids) {
        return this.repository.findByIdIn(ids);
    }

    /**
     * @param announce
     * @return
     */
    @Override
    public Announce update(Announce announce) {
        try {
            Announce saved = this.repository.save(announce);
            ModelMapper modelMapper = new ModelMapper();
            // Converter personnalisé : Hibernate PersistentBag -> ArrayList
            Converter<Collection<?>, List<?>> collectionToListConverter =
                    ctx -> ctx.getSource() == null ? null : new ArrayList<>(ctx.getSource());
            modelMapper.addConverter(collectionToListConverter);

            // Condition : ignorer les PersistentCollection non initialisées
            Condition<?, ?> skipUninitializedCollections = (Condition<Object, Object>) context -> {
                Object source = context.getSource();
                if (source instanceof PersistentCollection) {
                    return ((PersistentCollection<?>) source).wasInitialized();
                }
                return source != null;
            };
            modelMapper.getConfiguration().setPropertyCondition(skipUninitializedCollections);

            AnnounceDocument docToSave = modelMapper.map(saved, AnnounceDocument.class);
            this.operations.update(docToSave);
            // send notification to broker
            this.bridge.send(this.TOPIC_ADVERT, Map.of("adId",saved.getId(), "type",AdvertEventType.UPDATED.name()));
            return saved;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Announce> getAnnounces(Pageable pageable, String search) {
        if (!StringUtils.hasText(search)) {
            return repository.findAll(pageable); // no spec needed
        }
        Specification<Announce> spec = buildSearchSpec(search);
        return this.repository.findAll(spec, pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Announce> getAll() {
        return this.repository.findAll();
    }


    /**
     * @return
     */
    @Override
    public List<Announce> getAllByUserId(String userId, AnnounceStatus status) {
        return this.repository.findAnnouncesByUserIdAndStatus(userId, status);
    }

    /**
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Announce> getByTitle(String title, Pageable pageable) {
        return this.repository.findAllByTitleContains(title, pageable);
    }

    private Specification<Announce> buildSearchSpec(String search) {
        if (!StringUtils.hasText(search)) return null; // no filter -> all

        final String like = "%" + search.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("id")), like),
                cb.like(cb.lower(root.get("title")), like),
                cb.like(cb.lower(root.get("status")), like),
                cb.like(cb.lower(root.get("tel")), like)
        );
    }

}
