package com.doan.backend.repository;

import com.doan.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String userName);
    boolean existsByUserName(String userName);
    Optional<Users> findById(Long id);
}
