package org.mts.usersservice.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.Employee;
import org.mts.usersservice.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeService implements IEmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * @param employee
     * @return
     */
    @Override
    public Employee creatEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Employee getEmployeeById(String id) {
        return null;
    }

    /**
     * @param employee
     * @return
     */
    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Employee deleteEmployeeById(String id) {
        return null;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Employee> getEmployees(Pageable pageable) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<Employee> getListEmployee() {
        return this.employeeRepository.findAll();
    }

    /**
     * @param firstname
     * @param pageable
     * @return
     */
    @Override
    public Page<Employee> findEmployeeByFirstname(String firstname, Pageable pageable) {
        return null;
    }
}
