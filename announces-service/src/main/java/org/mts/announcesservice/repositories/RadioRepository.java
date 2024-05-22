package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Radio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadioRepository extends JpaRepository<Radio, String> {
}
