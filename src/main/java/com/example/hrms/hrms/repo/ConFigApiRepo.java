package com.example.hrms.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hrms.hrms.model.ConfigTable;

@Repository
public interface ConFigApiRepo extends JpaRepository<ConfigTable, Long> {

}
