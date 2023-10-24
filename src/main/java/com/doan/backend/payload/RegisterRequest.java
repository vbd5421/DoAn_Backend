package com.doan.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<String> role;
}