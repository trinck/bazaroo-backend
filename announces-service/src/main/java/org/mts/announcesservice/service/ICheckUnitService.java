package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.CheckUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICheckUnitService {

    public CheckUnit create(CheckUnit checkUnit);
    public CheckUnit getByID(Long id);
    public CheckUnit deleteById(Long id);
    public CheckUnit update(CheckUnit checkUnit);
    public Page<CheckUnit> getCheckUnits(Pageable pageable);
    public List<CheckUnit> getAll();
    public Page<CheckUnit> getByName(String name, Pageable pageable);
}
