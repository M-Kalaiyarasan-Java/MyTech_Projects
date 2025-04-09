package com.example.hrms.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hrms.hrms.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
}
