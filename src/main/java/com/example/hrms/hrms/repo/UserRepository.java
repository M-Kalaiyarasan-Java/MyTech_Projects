package com.example.hrms.hrms.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hrms.hrms.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.department")
    List<User> findAll();

    @Query(value = "SELECT id, name  FROM user", nativeQuery = true)
    List<Map<String, Object>> getReportingToList();
}

