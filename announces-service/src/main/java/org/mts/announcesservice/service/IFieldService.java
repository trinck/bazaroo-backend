package org.mts.announcesservice.service;


import org.mts.announcesservice.entities.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFieldService {

    public Field create(Field field);
    public Field deleteById(String id);
    public Field update(Field field);
    public Field getByID(String id);
    public Page<Field> getFields(Pageable pageable);
    public List<Field> getAll();
    public Page<Field> getByName(String name, Pageable pageable);
}
