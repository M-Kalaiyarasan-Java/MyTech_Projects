package com.example.hrms.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrms.hrms.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
}
