package com.doan.backend.controller;

import com.doan.backend.model.Product;

import com.doan.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("")
    ResponseEntity<?> getAllProduct(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                    @RequestParam(name="pageSize",defaultValue = "4")int size,
                                    @RequestParam(name="id",required = false)Long id,
                                    @RequestParam(name="name",required = false)String name) {
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(productService.getAllProduct(id,name,pageable));

    }
    @PostMapping("/create")
    ResponseEntity<?> createproduct(@RequestBody Product product,
                                    @RequestParam(name="listMember") List<Long> member) {
        return ResponseEntity.ok(productService.createOrUpdate(product,member));
    }
    @PostMapping("/update")
    ResponseEntity<?> updateproduct(@RequestBody Product product,
                                    @RequestParam(name="listMember")List<Long> member) {
        return ResponseEntity.ok(productService.createOrUpdate(product,member));
    }
}