package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
