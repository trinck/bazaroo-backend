package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Announce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpa")
public interface AnnounceRepository extends JpaRepository<Announce, String> {

    public Page<Announce> findAllByTitleContains(String title, Pageable pageable);
}
