package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAnnounceService {


    public Announce create(Announce announce);
    public Announce deleteById(String id);
    public Announce getByID(String id);
    public Announce update(Announce announce);
    public Page<Announce> getAnnounces(Pageable pageable, String search);
    public List<Announce> getAll();
    public Page<Announce> getByTitle(String title, Pageable pageable);
}
