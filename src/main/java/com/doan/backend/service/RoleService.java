package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Roles;
import com.doan.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Roles getRoleById(Long id){
        Roles roles = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Id khong ton tai"));
        return roles;
    }
    public List<Roles> getAll() {
        return  roleRepository.findAll();
    }
}
