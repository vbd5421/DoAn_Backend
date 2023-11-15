package com.doan.backend.repository;

import com.doan.backend.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p " +
            "where (:name is null or  ( LOWER(p.title)  LIKE LOWER(concat('%', :name , '%'))))")
    Page<Product> getAllProduct(@Param("name") String name,Pageable pageable);

}
