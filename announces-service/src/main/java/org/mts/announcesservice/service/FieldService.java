package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Field;
import org.mts.announcesservice.repositories.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService implements IFieldService{

    @Autowired
    private FieldRepository repository;
    /**
     * @param field
     * @return
     */
    @Override
    public Field create(Field field) {
        return this.repository.save(field);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Field deleteById(String id) {

       Field field = this.repository.findById(id).orElseThrow();
       this.repository.deleteById(id);
        return field;
    }

    /**
     * @param field
     * @return
     */
    @Override
    public Field update(Field field) {
        return this.repository.save(field);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Field getByID(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Field> getFields(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Field> getAll() {
        return this.repository.findAll();
    }

    /**
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Page<Field> getByName(String name, Pageable pageable) {
        return this.repository.findAllByNameContains(name, pageable);
    }
}
