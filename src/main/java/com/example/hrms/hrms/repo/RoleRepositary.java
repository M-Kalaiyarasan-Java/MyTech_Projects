package com.example.hrms.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrms.hrms.model.Role;

public interface RoleRepositary extends JpaRepository<Role, Long>{
    
}
