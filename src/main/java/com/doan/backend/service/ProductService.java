package com.doan.backend.service;

import com.doan.backend.dto.ProductDTO;
import com.doan.backend.model.Product;
import com.doan.backend.repository.MemberRepository;
import com.doan.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberRepository memberRepository;
    public List<ProductDTO> getAllProduct(Pageable pageable) {
        List<Product> products = productRepository.getAllProduct(pageable).toList();
        List<ProductDTO> productList = products.stream().map(
            product -> new ProductDTO(
                product.getTitle(),
                product.getContent(),
                product.getDescription(),
                product.getDate(),
                product.getImage().getPathUrl(),
                product.getUrl(),
                memberRepository.getListMemberByProduct(product.getId())
            )
        ).collect(Collectors.toList());

        return productList;
    }
}
