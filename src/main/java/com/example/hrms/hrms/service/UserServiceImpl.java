package com.example.hrms.hrms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hrms.hrms.dto.UserDTO;
import com.example.hrms.hrms.model.User;
import com.example.hrms.hrms.repo.UserRepository;

public interface UserServiceImpl {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
}


