package com.doan.backend.service;


import com.doan.backend.model.Users;
import com.doan.backend.repository.UserRepository;
import lombok.AllArgsConstructor;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@AllArgsConstructor
@Transactional // khi xảy ra lỗi hoặc exception transaction sẽ giúp rollback lại các thao tác trước đó

public class UserDetailsServiceImpl implements  UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}

