package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.CheckUnit;
import org.mts.announcesservice.repositories.CheckUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckUnitService implements  ICheckUnitService{

    @Autowired
    private CheckUnitRepository repository;
    /**
     * @param checkUnit
     * @return
     */
    @Override
    public CheckUnit create(CheckUnit checkUnit) {
        return this.repository.save(checkUnit);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CheckUnit getByID(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CheckUnit deleteById(Long id) {

        CheckUnit checkUnit = this.repository.findById(id).orElseThrow();
        this.repository.deleteById(id);
        return checkUnit;
    }

    /**
     * @param checkUnit
     * @return
     */
    @Override
    public CheckUnit update(CheckUnit checkUnit) {
        return this.repository.save(checkUnit);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<CheckUnit> getCheckUnits(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<CheckUnit> getAll() {
        return this.repository.findAll();
    }

    /**
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Page<CheckUnit> getByName(String name, Pageable pageable) {
        return this.repository.findAllByNameContains(name, pageable);
    }
}
