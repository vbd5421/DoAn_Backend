package com.doan.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Users user;

    @Nullable
    @Column(columnDefinition = "text")
    private String content; // set limit 8000 chars

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
    @Column(name = "parent_id")
    @Nullable
    private Long parentId;
    @Nullable
    private Boolean status;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private List<Comment> commentChild;

    //    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;
}
