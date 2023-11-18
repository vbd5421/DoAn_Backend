package com.doan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    private Date createDate;
    private Date updateDate;
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
    public Project(Long id, String name, String description, String content, Date createDate, Long status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
        this.createDate = createDate;
        this.status = status;
    }

}
