package com.example.hrms.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hrms.hrms.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    /**
     * Find employee by employee code
     */
    Employee findByEmployeeCode(String employeeCode);
    
    /**
     * Find employees by department
     */
    List<Employee> findByDepartment(String department);
    
    /**
     * Find employees by status
     */
    List<Employee> findByStatus(String status);
}
