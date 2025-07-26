package org.mts.announcesservice.service;

import org.modelmapper.ModelMapper;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.dtos.FieldOutputDTO;
import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.enums.AdvertEventType;
import org.mts.announcesservice.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnnounceService implements IAnnounceService{

    private final AnnounceRepository repository;
    private final ElasticsearchOperations operations;
    private final ModelMapper mapper;
    private final StreamBridge bridge;
    @Value("${app.broker.topics.advert}")
    private String TOPIC_ADVERT;

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
    public Announce create(Announce announce) {

        Announce saved = this.repository.save(announce);
        AnnounceDocument docToSave = this.mapper.map(saved, AnnounceDocument.class);
        docToSave.getFields().forEach(FieldOutputDTO::prepareForIndexing);
        this.operations.save(docToSave);

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
     * @param announce
     * @return
     */
    @Override
    public Announce update(Announce announce) {
        try {
            Announce saved = this.repository.save(announce);
            AnnounceDocument docToSave = this.mapper.map(saved, AnnounceDocument.class);
            this.operations.save(docToSave);
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
    public Page<Announce> getAnnounces(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Announce> getAll() {
        return this.repository.findAll();
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
}
