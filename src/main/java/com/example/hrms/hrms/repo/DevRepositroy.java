package com.example.hrms.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hrms.hrms.model.Developers;

@Repository
public interface DevRepositroy extends JpaRepository<Developers, Long> {

    
    @Query(value = "SELECT d.emp_full_name FROM developers d WHERE d.id = (SELECT d1.reporting_to FROM developers d1 WHERE d1.emp_full_name = :name)", nativeQuery = true)
    String findReportingManagerName(@Param("name") String empFullName);


}
