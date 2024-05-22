package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository <Employee, String> {
}
