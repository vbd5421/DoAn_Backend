package com.doan.backend.controller;

import com.doan.backend.model.Users;
import com.doan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<?> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
    }
}
