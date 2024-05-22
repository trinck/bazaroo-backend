package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String > {


    public Page<User> findByFirstnameContainsIgnoreCase(String firstname, Pageable pageable);

}
