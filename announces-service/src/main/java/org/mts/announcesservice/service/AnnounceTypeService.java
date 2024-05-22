package org.mts.announcesservice.service;


import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.repositories.AnnounceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceTypeService implements IAnnounceTypeService{


    @Autowired
    private AnnounceTypeRepository repository;

    /**
     * @param announceType
     * @return
     */
    @Override
    public AnnounceType create(AnnounceType announceType) {
        return this.repository.save(announceType);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public AnnounceType getByID(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public AnnounceType deleteById(String id) {

        AnnounceType type = this.repository.findById(id).orElseThrow();
        this.repository.deleteById(id);
        return type;
    }

    /**
     * @param announceType
     * @return
     */
    @Override
    public AnnounceType update(AnnounceType announceType) {
        return this.repository.save(announceType);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<AnnounceType> getAnnounceTypes(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<AnnounceType> getAll() {
        return this.repository.findAll();
    }

    /**
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Page<AnnounceType> getByName(String name, Pageable pageable) {
        return this.repository.findAllByNameContains(name,pageable);
    }
}
