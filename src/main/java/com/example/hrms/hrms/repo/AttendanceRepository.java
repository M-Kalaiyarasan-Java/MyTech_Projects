package com.example.hrms.hrms.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hrms.hrms.model.Attendance;
import com.example.hrms.hrms.model.Employee;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    /**
     * Find all attendance records between the given date range
     */
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find all attendance records for a specific employee between the given date range
     */
    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    
    /**
     * Check if an attendance record exists for the given employee and date
     */
    boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
}
