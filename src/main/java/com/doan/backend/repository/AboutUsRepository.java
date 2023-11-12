package com.doan.backend.repository;

import com.doan.backend.model.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs,Long> {
    @Query("SELECT ab FROM AboutUs ab")
    AboutUs getAll();
}
