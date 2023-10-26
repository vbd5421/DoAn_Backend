package com.doan.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String fax;
    private String email;
    private String description;
    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Room> rooms;

    public Hotel(String name, String address, String phone, String fax, String email, String description) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.description = description;
    }
}
