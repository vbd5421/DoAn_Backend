package com.doan.backend.repository;

import com.doan.backend.model.LinkWebsite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkWebsiteRepository extends JpaRepository<LinkWebsite,Long> {

    @Query("select link from LinkWebsite link where :name is null or link.name like concat('%',:name,'%') order by link.id desc ")
    Page<LinkWebsite> getAll(Pageable pageable, @Param("name") String name);
}
