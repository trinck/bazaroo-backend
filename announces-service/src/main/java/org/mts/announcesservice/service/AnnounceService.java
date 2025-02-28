package org.mts.announcesservice.service;

import org.modelmapper.ModelMapper;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.repositories.AnnounceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceService implements IAnnounceService{

    private final AnnounceRepository repository;
    private final ElasticsearchOperations operations;
    private final ModelMapper mapper;

    public AnnounceService(AnnounceRepository repository, ElasticsearchOperations operations, ModelMapper mapper) {
        this.repository = repository;
        this.operations = operations;
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
        this.operations.save(docToSave);
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
