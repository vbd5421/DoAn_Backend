package com.doan.backend.repository;

import com.doan.backend.model.LinkWebsite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkWebsiteRepository extends JpaRepository<LinkWebsite,Long> {
}
