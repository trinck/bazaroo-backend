package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}
