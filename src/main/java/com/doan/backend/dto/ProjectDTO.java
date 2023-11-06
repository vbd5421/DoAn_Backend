package com.doan.backend.dto;

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
    private String name;
    private String description;
    private String content;
    private Date date;
    //0, đang triển khai;1 đã hoàn thành
    private Long status;

    private List<String> membersName;
}
