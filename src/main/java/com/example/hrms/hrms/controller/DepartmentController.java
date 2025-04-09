package com.example.hrms.hrms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrms.hrms.model.Department;
import com.example.hrms.hrms.repo.DepartmentRepository;

@RestController
@RequestMapping("/api/")
public class DepartmentController {


        @Autowired
    private DepartmentRepository dep;
    
    @GetMapping("/get")
   public List<Department> getall(){
    return dep.findAll();
   }

    @GetMapping("/get_department/{id}")
    public Department getDepartments(@PathVariable Long id) {
        
        Optional<Department> department = dep.findById(id);

        if(department.isPresent()){
            return department.get();
        }

        return null;

    }
    
}
