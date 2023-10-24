package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Users;
import com.doan.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Users findUserById(Long id){
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy user"));
        return user;
    }
}
