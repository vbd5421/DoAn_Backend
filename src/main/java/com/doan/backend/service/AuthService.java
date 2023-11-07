package com.doan.backend.service;

import com.doan.backend.config.JwtService;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Roles;
import com.doan.backend.model.Users;
import com.doan.backend.payload.AuthenticationRequest;
import com.doan.backend.payload.AuthenticationResponse;
import com.doan.backend.payload.RegisterRequest;
import com.doan.backend.repository.RoleRepository;
import com.doan.backend.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;



    public Users register(RegisterRequest registerRequest){
        Users user = new Users(
                registerRequest.getUsername()
                ,registerRequest.getEmail()
                ,registerRequest.getFirstName()
                ,registerRequest.getLastName()
                ,passwordEncoder.encode(registerRequest.getPassword())
        );

        List<String> strRoles =registerRequest.getRole();
        List<Roles> roles = new ArrayList<>();
        if (strRoles == null) {
            Roles userRole = roleRepository.findByRoleName("ADMIN")
                    .orElseThrow(() -> new ResourceException("Không tìm thấy quyền cho người dùng"));
            roles.add(userRole);
        }
        user.setRoles(roles);
        return userRepository.save(user);

    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername()
                        ,authenticationRequest.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return AuthenticationResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .email(userDetails.getEmail())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse loginUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername()
                        ,authenticationRequest.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return AuthenticationResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .email(userDetails.getEmail())
                .token(jwtToken)
                .build();
    }
}
