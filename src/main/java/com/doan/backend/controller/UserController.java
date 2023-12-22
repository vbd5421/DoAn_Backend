package com.doan.backend.controller;


import com.doan.backend.dto.User.UserDTO;
import com.doan.backend.model.Users;
import com.doan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("")
    public ResponseEntity<?> searchUsernameAndEmail(
            @RequestParam(name = "search",required = false)String searchInput)
    {

        return ResponseEntity.ok(userService.searchUsernameAndEmail(searchInput));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id),HttpStatus.OK);
    }
    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id")Long id,
                                            @RequestBody UserDTO userDTO
    )
    {
        return ResponseEntity.ok().body(userService.updateUser(id,userDTO));
    }
    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<Users> changePassword(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.changePassword(id));
    }
}
