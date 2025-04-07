package com.example.hrms.hrms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrms.hrms.model.Department;
import com.example.hrms.hrms.model.Role;
import com.example.hrms.hrms.model.User;
import com.example.hrms.hrms.repo.DepartmentRepository;
import com.example.hrms.hrms.repo.RoleRepositary;
import com.example.hrms.hrms.repo.UserRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private DepartmentRepository dep;

    @Autowired
    private RoleRepositary role_rep;

    @PostMapping("/user")
    public User save(@RequestBody User user){
        return repo.save(user);
    }

    @PostMapping("/department")
    public Department savDepartment(@RequestBody Department department){
        return dep.save(department);
    }

    @PostMapping("/role")
    public Role saveRole(@RequestBody Role role){
        return role_rep.save(role);
    }

    @GetMapping("/get_users")
    public List<User> getalluser(){
        return repo.findAll();
    }

    @GetMapping("/get_managers")
    public List<Department> getalldepartment(){
        return dep.findAll();
    }
    
   @GetMapping("/get_reporting_to")
    public ResponseEntity<List<Map<String, Object>>> getReportingTo() {
        List<Map<String, Object>> list = repo.getReportingToList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/get_department/{id}")
    public Department getdDepartment(@PathVariable long id) {
        return dep.findById(id).orElse(null);
    }


} 
