package org.mts.announcesservice.service;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.repositories.AnnounceRepository;
import org.mts.announcesservice.repositories.ElasticAnnounceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceService implements IAnnounceService{

    private final AnnounceRepository repository;
    private final ElasticAnnounceRepo elasticAnnounceRepo;
    private final ModelMapper mapper;

    public AnnounceService(AnnounceRepository repository, ElasticAnnounceRepo elasticAnnounceRepo, ModelMapper mapper) {
        this.repository = repository;
        this.elasticAnnounceRepo = elasticAnnounceRepo;
        this.mapper = mapper;
    }

    /**
     * @param announce
     * @return
     */
    @Override
    public Announce create(Announce announce) {

        Announce saved = this.repository.save(announce);
        AnnounceDocument docToSave = this.mapper.map(saved, AnnounceDocument.class);
        this.elasticAnnounceRepo.save(docToSave);
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
        return this.repository.save(announce);
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
