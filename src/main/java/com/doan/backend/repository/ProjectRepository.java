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
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("select p from Project p " +
            "where (:name is null or ( LOWER(p.name)  LIKE LOWER(concat('%', :name , '%')))) " +
            "and ( :cateId is null or p.cateProject.id = :cateId) " +
            "order by p.id desc ")
    Page<Project> getAllProject(@Param("name") String name,@Param("cateId")Long cateId,Pageable pageable);

    @Query("SELECT p.image.name FROM Project p WHERE p.id=:id")
    String getImageByProjectId(Long id);
    @Query("SELECT p.image.pathFile FROM Project p WHERE p.id=:id")
    String getPathFileByProjectId(Long id);

    Optional<Project> findByUrl(String url);
}
