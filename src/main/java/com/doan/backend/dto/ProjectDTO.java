package com.doan.backend.dto;

import com.doan.backend.model.Image;
import com.doan.backend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String content;
    private Image image;
    private Date date;
    private Long cateId;
    //0, đang triển khai;1 đã hoàn thành
    private Long status;

    private List<Member> members;

    public ProjectDTO(Long id, String name, String description,
                      String content, Image image, Date date, Long status, List<Member> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
        this.image = image;
        this.date = date;
        this.status = status;
        this.members = members;
    }
}
