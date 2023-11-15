package com.doan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "text")
    private String description;
    @Column(name="created_date")
    private Date date;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Image image;

    private String url;

    private Boolean active;

    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable(name = "product_member",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members;

    public Product(Long id, String title, String content, String description, Date date, Image image, String url, Boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.date = date;
        this.image = image;
        this.url = url;
        this.active = active;
    }
}