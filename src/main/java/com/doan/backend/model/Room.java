package com.doan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Double price;
    // 0. còn trống,1. đã đặt
    private Long status;
    private String description;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
