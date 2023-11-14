package com.doan.backend.repository;

import com.doan.backend.model.Product;
import com.doan.backend.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("select p from Project p " +
            "where (:name is null or p.name = :name) " +
            "order by p.createDate desc ")
    List<Project> getAllProject(@Param("name") String name);
}
