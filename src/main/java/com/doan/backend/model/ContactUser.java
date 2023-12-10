package com.doan.backend.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUser  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private  String phone;

    private String email;
    @Column(columnDefinition = "text")
    private  String content;
    private LocalDate dates;

    private Boolean status;

}

