package com.doan.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String content;
    private String url;
    private Date createDate; // time triển khai
    private LocalDate updateDate; //
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Image image;
    //0, đang triển khai;1 đã hoàn thành
    private Long status;
    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable(name = "project_member",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cate_project_id")
    private CateProject cateProject;
    public Project(Long id, String name, String description, String content,String url, Date createDate, Long status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
        this.url = url;
        this.createDate = createDate;
        this.status = status;
    }

}
