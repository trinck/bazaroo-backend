package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, String> {

  public Optional< Auth> findByUsername(String username);

   public Optional< Auth> findAuthByUsernameOrEmailOrTel(String username, String email, String tel);
}