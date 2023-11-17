package com.doan.backend.service;

import com.doan.backend.dto.User.UserResponseDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Users;
import com.doan.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    public List<UserResponseDTO> searchUsernameAndEmail( String searchInput) {
        //TO DO config searchInput
        return userRepository.searchUsernameAndEmail( searchInput)
                .stream().map(user -> new UserResponseDTO(
                                user.getId(),
                                user.getUserName(),
                                user.getEmail(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getRoles()
                        )
                ).collect(Collectors.toList());
    }

    public Users findUserById(Long id){
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceException("không tìm thấy user"));
        return user;
    }
}
