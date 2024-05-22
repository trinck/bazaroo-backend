package org.mts.usersservice.services;

import org.mts.usersservice.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService {

    public Employee creatEmployee(Employee employee);
    public Employee getEmployeeById(String id);
    public Employee updateEmployee(Employee employee);
    public Employee deleteEmployeeById(String id);
    public Page<Employee> getEmployees(Pageable pageable);
    public List<Employee> getListEmployee();
    public Page<Employee> findEmployeeByFirstname(String firstname, Pageable pageable);
}
