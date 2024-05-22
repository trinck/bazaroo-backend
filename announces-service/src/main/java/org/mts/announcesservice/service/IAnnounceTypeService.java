package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.AnnounceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAnnounceTypeService {


    public AnnounceType create(AnnounceType announceType);
    public AnnounceType getByID(String id);
    public AnnounceType deleteById(String id);
    public AnnounceType update(AnnounceType announceType);
    public Page<AnnounceType> getAnnounceTypes(Pageable pageable);
    public List<AnnounceType> getAll();
    public Page<AnnounceType> getByName(String name, Pageable pageable);
}
