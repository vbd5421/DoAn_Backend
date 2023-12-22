package com.doan.backend.service;

import com.doan.backend.dto.User.UserDTO;
import com.doan.backend.dto.User.UserResponseDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Roles;
import com.doan.backend.model.Users;
import com.doan.backend.repository.RoleRepository;
import com.doan.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    public List<UserResponseDTO> searchUsernameAndEmail( String searchInput) {
        //TO DO config searchInput
        return userRepository.searchUsernameAndEmail( searchInput)
                .stream().map(user -> new UserResponseDTO(
                                user.getId(),
                                user.getUserName(),
                                user.getEmail(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getActive(),
                                user.getRoles()
                        )
                ).collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id){
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy user"));
        UserDTO useDTO = new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),

                user.getActive(),
                user.getRoles().get(0)
        );
        return useDTO;
    }


    public Users updateUser(Long id, UserDTO userDTO) {
        Optional<Users> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new ResourceException("Không tìm thấy người dùng với id là: " + id);
        }
        String username = userDTO.getUsername();
        String email = userDTO.getEmail();



        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        Users userFromBD = userOptional.get();
        userFromBD.setUserName(username);
        userFromBD.setEmail(email);
        userFromBD.setFirstName(firstName);
        userFromBD.setLastName(lastName);
        userFromBD.setActive(true);
        //userDto.setActive(user.getActive());
        List<Roles> roles = new ArrayList<>();
        roles.add(userDTO.getRole());
        if(roles != null){
            if (userRepository.findRoleUserById(id)) {
                userRepository.deleteRoleByUserId(id);
            }
            List<Roles> newRole = new ArrayList<>();
            for(Roles role:roles){
                newRole.add(role);
            }
            userFromBD.setRoles(newRole);
        }
        return userRepository.save(userFromBD);
    }
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public Users changePassword(Long userId) {
        Optional<Users> userOpt = userRepository.findById(userId);
        Users user = userOpt.get();
        System.out.println(user.toString());
        user.setPassword(passwordEncoder.encode("12345678"));
        return userRepository.save(user);
    }
}
