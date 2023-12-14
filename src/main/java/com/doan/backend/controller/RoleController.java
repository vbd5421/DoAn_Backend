package com.doan.backend.controller;

import com.doan.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.ok(roleService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRolesById(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }
}
