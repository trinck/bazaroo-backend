package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Role;
import org.mts.usersservice.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findRoleByRole(EnumRole role);
}
