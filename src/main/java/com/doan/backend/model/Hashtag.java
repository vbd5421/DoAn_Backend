package com.doan.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    private String name;

}