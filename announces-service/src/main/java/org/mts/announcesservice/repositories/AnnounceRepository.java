package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository("jpa")
public interface AnnounceRepository extends JpaRepository<Announce, String>, JpaSpecificationExecutor<Announce> {

    public Page<Announce> findAllByTitleContains(String title, Pageable pageable);

    public List<Announce> findByIdIn(Collection<String> ids);

    List<Announce> findAnnouncesByUserIdAndStatus(String userId, AnnounceStatus status);
}
