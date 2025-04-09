package com.example.hrms.hrms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrms.hrms.model.Department;
import com.example.hrms.hrms.model.Developers;
import com.example.hrms.hrms.repo.DepartmentRepository;
import com.example.hrms.hrms.repo.DevRepositroy;

// @CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class DevController {

    @Autowired
    private DevRepositroy rep;

    @Autowired
    private DepartmentRepository dep;

    // Add your methods here to handle requests related to developersp
    @PostMapping("/add_user")
    public Developers save(@RequestBody Developers dev) {
        return rep.save(dev);
    }

    @GetMapping("/get_developers")
    public List<Developers> getAllDevelopers() {
        return rep.findAll();
    }

    @GetMapping("/managers")
    public List<Developers> getalldepartment() {
        return rep.findAll();
    }

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
     @GetMapping("/developer-name")
    public ResponseEntity<String> getManagerName(@RequestParam String employeeName) {
        String managerName = rep.findReportingManagerName(employeeName);
        return ResponseEntity.ok(managerName);
    }

    @GetMapping("/get_developer/{id}")
    public Developers getById(@PathVariable Long id) {
        return rep.findById(id).orElse(null);
    }

    @PutMapping("/update_developer/{id}")
    public Developers update(@PathVariable long id, @RequestBody Developers dev) {
        
        Developers existingDev = rep.findById(id).orElse(null);
        if (existingDev != null) {
            existingDev.setEmp_first_name(dev.getEmp_first_name());
            existingDev.setEmp_middle_name(dev.getEmp_middle_name());
            existingDev.setEmp_last_name(dev.getEmp_last_name());
            existingDev.setGender(dev.getGender());
            existingDev.setMarital_status(dev.getMarital_status());
            existingDev.setDob(dev.getDob());
            existingDev.setEmail(dev.getEmail());
            existingDev.setMobile_no(dev.getMobile_no());
            existingDev.setDesignation(dev.getDesignation());
            existingDev.setDepartment_name(dev.getDepartment_name());
            existingDev.setReporting_to(dev.getReporting_to());
            existingDev.setDoj_office(dev.getDoj_office());
            existingDev.setAadhaar_no(dev.getAadhaar_no());
            existingDev.setPan_no(dev.getPan_no());
            existingDev.setBank_name(dev.getBank_name());
            existingDev.setBank_branch_name(dev.getBank_branch_name());
            existingDev.setBank_ifsc_code(dev.getBank_ifsc_code());
            existingDev.setBank_account_no(dev.getBank_account_no());
            existingDev.setCommunication_address(dev.getCommunication_address());
            existingDev.setComm_add_city(dev.getComm_add_city());
            existingDev.setComm_add_pincode(dev.getComm_add_pincode());
            existingDev.setComm_add_state(dev.getComm_add_state());
            existingDev.setComm_add_country(dev.getComm_add_country());
            existingDev.setPermenant_address(dev.getPermenant_address());
            existingDev.setPermenant_add_city(dev.getPermenant_add_city());
            existingDev.setPermenant_add_pincode(dev.getPermenant_add_pincode());
            existingDev.setPermenant_add_state(dev.getPermenant_add_state());
            existingDev.setPermenant_add_country(dev.getPermenant_add_country());
            existingDev.setPassword(dev.getPassword());
            existingDev.setConfirm_password(dev.getConfirm_password());
            existingDev.setEmp_full_name(dev.getEmp_full_name());
        }
        return rep.save(existingDev);
    }


    
    @PutMapping("/update_user/{id}")
public Developers deactive(@PathVariable long id, @RequestBody Developers dev) {
    
    Developers existingDev = rep.findById(id).orElse(null);
    if (existingDev != null) {
        existingDev.setActive(dev.getActive());
        existingDev.setResign_date(dev.getResign_date());   
        existingDev.setResign_reson(dev.getResign_reson());
    }

    System.out.println("Resign Date: " + dev.getResign_date());
    System.out.println("Resign Reason: " + dev.getResign_reson());

    return rep.save(existingDev);
}
    
}
