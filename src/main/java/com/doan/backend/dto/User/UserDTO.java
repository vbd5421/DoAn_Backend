package com.doan.backend.dto.User;


import com.doan.backend.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean active;
    private Roles role;
//    private List<Role> role;

    public UserDTO(Long id, String userName, String email, String firstName, String lastName, Boolean active, Roles role) {
        this.id = id;
        this.username = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.role = role;
    }

}