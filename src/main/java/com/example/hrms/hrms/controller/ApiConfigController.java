package com.example.hrms.hrms.controller;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrms.hrms.exception.ResourceNotFoundException;
import com.example.hrms.hrms.model.ConfigTable;
import com.example.hrms.hrms.model.Developers;
import com.example.hrms.hrms.repo.ConFigApiRepo;

@RestController
@RequestMapping("/api/config")
public class ApiConfigController {

    @Autowired
    private ConFigApiRepo configApiRepo;

    @PostMapping
    public ConfigTable create(@RequestBody ConfigTable configTable) {
        return configApiRepo.save(configTable);
    }

    @GetMapping
    public List<ConfigTable> getAll() {
        return configApiRepo.findAll();
    }

    @GetMapping("/{id}")
    public ConfigTable getById(@PathVariable Long id) {

        Optional<ConfigTable> configTable = configApiRepo.findById(id);
        if (configTable.isPresent()) {
            return configTable.get();
        } else {
            throw new ResourceNotFoundException("ConfigTable not found with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ConfigTable deactive(@PathVariable long id, @RequestBody ConfigTable dev) {

        ConfigTable existingDev = configApiRepo.findById(id).orElse(null);
        if (existingDev != null) {
            existingDev.setActive(dev.getActive());

        }

        return configApiRepo.save(existingDev);
    }

    @PutMapping("/update/{id}")

    public ConfigTable update(@PathVariable Long id, @RequestBody ConfigTable configTable) {

        ConfigTable existingConfigTable = configApiRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ConfigTable not found with id: " + id));

        existingConfigTable.setErp(configTable.getErp());
        existingConfigTable.setName(configTable.getName());
        existingConfigTable.setTable_name(configTable.getTable_name());
        existingConfigTable.setClient_id(configTable.getClient_id());
        existingConfigTable.setEntity_id(configTable.getEntity_id());
        existingConfigTable.setCompany_name(configTable.getCompany_name());
        existingConfigTable.setCompany_id(configTable.getCompany_id());
        existingConfigTable.setAccess_token(configTable.getAccess_token());
        existingConfigTable.setAccess_token_expires_at(configTable.getAccess_token_expires_at());
        existingConfigTable.setCompleted_at(configTable.getCompleted_at());
        existingConfigTable.setError_logs(configTable.getError_logs());
        existingConfigTable.setFrequency(configTable.getFrequency());
        existingConfigTable.setRefresh_token(configTable.getRefresh_token());
        existingConfigTable.setRefresh_token_expires_at(configTable.getRefresh_token_expires_at());
        existingConfigTable.setTime(configTable.getTime());
        existingConfigTable.setAction(configTable.getAction());
        existingConfigTable.setStarted_at(configTable.getStarted_at());
        existingConfigTable.setUpdated_at(configTable.getUpdated_at());

        return configApiRepo.save(existingConfigTable);
    }

}
