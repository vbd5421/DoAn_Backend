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
            "where (:name is null or p.title = :name) and " +
            "(:id is null or p.id = :id) " +
            "order by p.date desc ")
    Page<Product> getAllProduct(@Param("id") Long id,@Param("name") String name, Pageable pageable);

}
