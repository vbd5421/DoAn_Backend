package com.doan.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
public class AboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "about_id")
    private Long id;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "text")
    private String contentUav;
    @Column(name = "video_link", columnDefinition = "text")
    private String videoLINK;

    private String phone;

    private String fax;

    private String email;
    @Column(columnDefinition = "text")
    private String project;
    @Column(columnDefinition = "text")
    private String member;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "about_address",
            joinColumns = @JoinColumn(name = "about_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Collection<Address> address;
}
