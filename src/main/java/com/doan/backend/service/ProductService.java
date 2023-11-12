package com.doan.backend.service;

import com.doan.backend.dto.ProductDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Member;
import com.doan.backend.model.Product;
import com.doan.backend.repository.MemberRepository;
import com.doan.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberRepository memberRepository;
    public List<ProductDTO> getAllProduct(String name,Pageable pageable) {
        List<Product> products = productRepository.getAllProduct(name,pageable).toList();
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
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy sản phẩm"));
        return new ProductDTO(
                product.getTitle(),
                product.getContent(),
                product.getDescription(),
                product.getDate(),
                product.getImage().getPathUrl(),
                product.getUrl(),
                memberRepository.getListMemberByProduct(product.getId())
        );
    }
    public Product createOrUpdate(Product product,List<Long> member) {
        Product newProduct = new Product();
        if(product.getId() != null) {
            newProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new ResourceException("Không tìm thấy sản phẩm"));
        }
        newProduct.setTitle(product.getTitle());
        newProduct.setDate(new Date());
        newProduct.setContent(product.getContent());
        newProduct.setDescription(product.getDescription());
        newProduct.setUrl(product.getUrl());
        List<Member> members = new ArrayList<>();
        for(Long id : member) {
            com.doan.backend.model.Member mem = memberRepository.findById(id)
                    .orElseThrow(() -> new ResourceException("không tìm thấy nhân viên"));
            members.add(mem);
        }
        newProduct.setMembers(new HashSet<>(members));
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy sản phẩm"));
        productRepository.delete(product);
    }
}
