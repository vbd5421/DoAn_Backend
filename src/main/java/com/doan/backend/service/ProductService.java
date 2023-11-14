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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    FileService fileService;

    public List<Product> getAllProduct(String name) {
        return productRepository.getAllProduct(name);
    }
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy sản phẩm"));
        return new ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getContent(),
                product.getDescription(),
                product.getDate(),
                product.getImage().getPathUrl(),
                product.getUrl(),
                memberRepository.getListMemberByProduct(product.getId())
        );
    }
    public Product createOrUpdate(ProductDTO productDTO, MultipartFile file) throws IOException {
        Product newProduct = new Product();
        if(productDTO.getId() != null) {
            newProduct = productRepository.findById(productDTO.getId())
                    .orElseThrow(() -> new ResourceException("Không tìm thấy sản phẩm"));
        }
        newProduct.setTitle(productDTO.getTitle());
        newProduct.setDate(new Date());
        newProduct.setContent(productDTO.getContent());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setUrl(productDTO.getUrl());
        List<Member> members = new ArrayList<>();
        if(file != null) {
            newProduct.setImage(fileService.uploadImage(file));
        }
        newProduct.setMembers(new HashSet<>(productDTO.getMembers()));
        newProduct.setMembers(new HashSet<>(members));
        return productRepository.save(newProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceException("Không tìm thấy sản phẩm"));
        productRepository.delete(product);
    }
}
