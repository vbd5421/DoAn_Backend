package com.doan.backend.repository;

import com.doan.backend.model.TypicalNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypicalNumberRepository extends JpaRepository<TypicalNumber,Long> {
    @Query("SELECT tn FROM TypicalNumber tn WHERE tn.active = true ORDER BY tn.id DESC ")
    List<TypicalNumber> listAll();

    @Query("SELECT tn FROM TypicalNumber tn WHERE tn.active = true ORDER BY tn.id DESC")
    Page<TypicalNumber> findAll(Pageable pageable);

    @Query("SELECT tn FROM TypicalNumber tn WHERE "
            +"(:si is null or LOWER(tn.description) LIKE LOWER(concat('%',:si,'%'))) " +
            "AND tn.active=true")
    Page<TypicalNumber> searchTitleAndDescription(Pageable pageable,@Param("si")String searchInput);
}
