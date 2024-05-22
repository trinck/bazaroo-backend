package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository  extends JpaRepository<Text,String> {
}
