package com.doan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;
import java.time.LocalDate;
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Boolean active;
//    private String description;
//    private String title;
//    @Column(columnDefinition = "text")
//    private String content;
//    private String createDate;
//    private String updateDate;
//    private String url;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="image_id")
//    private Image image;
    private Long id;

    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "text")
    private String description;

    //    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name="created_date")
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Image image;
    private String url;
    private Boolean active;

    @Nullable
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "post_hashtag",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<Hashtag> hashtags;

}
