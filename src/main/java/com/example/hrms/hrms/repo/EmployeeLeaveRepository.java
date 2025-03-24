package com.example.hrms.hrms.repo;

import com.example.hrms.hrms.model.EmployeeLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Long> {

    List<EmployeeLeave> findByMonth(String month);
    
    List<EmployeeLeave> findByYear(String year);
    
    List<EmployeeLeave> findByMonthAndYear(String month, String year);
    
    List<EmployeeLeave> findByEmployeeId(Long employeeId);
    
    List<EmployeeLeave> findByEmployeeIdAndYear(Long employeeId, String year);
    
    @Query("SELECT DISTINCT e.departmentName FROM EmployeeLeave e WHERE e.departmentName IS NOT NULL")
    List<String> findAllDepartments();
    
    @Query("SELECT DISTINCT e.designationName FROM EmployeeLeave e WHERE e.designationName IS NOT NULL")
    List<String> findAllDesignations();
    
    @Query("SELECT e.employeeId as id, e.apEmpName as name FROM EmployeeLeave e GROUP BY e.employeeId, e.apEmpName")
    List<Map<String, Object>> findAllEmployees();
}