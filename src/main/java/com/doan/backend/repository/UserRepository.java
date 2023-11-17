package com.doan.backend.repository;

import com.doan.backend.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String userName);
    boolean existsByUserName(String userName);
    Optional<Users> findById(Long id);

    @Query("select u from Users u  ORDER BY u.id ASC")
    List<Users> listAll();

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_roles SET role_id =?1 WHERE user_id=?2", nativeQuery = true)
    void updateRoleByUserId(Long roleId, Long userId);

    @Query("SELECT u FROM Users u WHERE " +
            "(:si is null or ( LOWER(u.userName)  LIKE LOWER(concat('%', :si , '%') ) " +
            "OR LOWER(u.email)  LIKE LOWER(concat('%', :si , '%') ))) ")
    List<Users> searchUsernameAndEmail( @Param("si")String searchInput);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles ur WHERE ur.user_id=:id", nativeQuery = true)
    void deleteRoleByUserId(@Param("id") Long id);


    @Query(value = "SELECT CASE WHEN COUNT(ur) > 0 THEN true ELSE false END " +
            "FROM user_roles ur WHERE ur.user_id=:id", nativeQuery = true)

    boolean findRoleUserById(@Param("id")Long id);



}
