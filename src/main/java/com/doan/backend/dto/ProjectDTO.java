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
    //0, đang triển khai;1 đã hoàn thành
    private Long status;

    private List<Member> members;
}
