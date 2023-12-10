package com.doan.backend.repository;


import com.doan.backend.model.TypicalImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface TypicalImageRepository extends JpaRepository<TypicalImage, Long> {

    @Query("SELECT ti FROM TypicalImage ti where ti.active = true AND ti.status = true ORDER BY ti.id ASC")
    List<TypicalImage> listAll();

    @Query("SELECT ti FROM TypicalImage ti where ti.status = true ORDER BY ti.id DESC")
    Page<TypicalImage> findAll(Pageable pageable);

    Optional<TypicalImage> getTypicalImageByImage_Id(Long id);


    @Query("SELECT i.name FROM Image i WHERE i.name=:name")
    String getImageByName(String name);


    @Query("SELECT i.pathFile FROM Image i WHERE i.name=:name")
    String getPathFileByName(String name);

}
