package com.doan.backend.repository;

import com.doan.backend.model.CateProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CateprojectRepository extends JpaRepository<CateProject,Long> {
    @Query("select c from CateProject c " +
            "where (:si is null or lower(c.typeName) like ('%'||lower(:si)||'%' ) )")
    Page<CateProject> getListCateProject(Pageable pageable, @Param("si")String searchInput);
}
