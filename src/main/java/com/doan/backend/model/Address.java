package com.doan.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(columnDefinition = "text")
    private String address; // dia chi

    private String district;

    private String city;

    private String wards;
}