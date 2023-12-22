package com.doan.backend.dto.User;


import com.doan.backend.model.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean active;
    //    private Set<Role> role;
    private List<Roles> roles;



    public UserResponseDTO(Long id, String username, String email, String firstName, String lastName, Boolean active, List<Roles> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.roles = roles;
    }


}
