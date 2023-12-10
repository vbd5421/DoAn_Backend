package com.doan.backend.repository;



import com.doan.backend.model.ContactUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface ContactUserRepository extends JpaRepository<ContactUser, Long> {



    @Query("SELECT c FROM ContactUser c WHERE c.dates BETWEEN :startTime AND :endTime")
    Page<ContactUser> findByTimestampBetween(Pageable pageable, @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);

    @Query("SELECT n FROM ContactUser n WHERE (:si is null " +
            "or (LOWER(n.name) LIKE LOWER(concat('%',:si,'%')) OR " +
            "LOWER(n.content) LIKE LOWER(concat('%',:si,'%')) OR " +
            "LOWER(n.email) LIKE LOWER(concat('%',:si,'%')) OR " +
            "LOWER(n.phone) LIKE LOWER(concat('%',:si,'%'))))" +
            " AND (:active IS NULL OR n.status = :active) ")
    Page<ContactUser> searchContactUserAndFilter(Pageable pageable,@Param("si")String searchInput,@Param("active") Boolean active);

    ContactUser findByDates(LocalDate localDate);

    Page<ContactUser> findALlByStatusIsTrue(Pageable pageable);

    Page<ContactUser> findALlByStatusIsFalse(Pageable pageable);


    @Query("SELECT cu FROM ContactUser cu ORDER BY cu.id DESC")
    List<ContactUser> listAll();



}
