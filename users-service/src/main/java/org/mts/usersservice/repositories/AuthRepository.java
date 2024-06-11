package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    public Optional< Auth> findByUsername(String username);

}
