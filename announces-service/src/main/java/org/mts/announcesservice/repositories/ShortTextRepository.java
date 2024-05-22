package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.ShortText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortTextRepository extends JpaRepository<ShortText, String> {
}
