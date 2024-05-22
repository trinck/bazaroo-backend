package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.repositories.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceService implements IAnnounceService{

    @Autowired
  private AnnounceRepository repository;
    /**
     * @param announce
     * @return
     */
    @Override
    public Announce create(Announce announce) {
        return this.repository.save(announce);
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
