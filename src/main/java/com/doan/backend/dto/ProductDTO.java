package com.doan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String title;
    private String content;
    private String description;

    private Date date;

    private String image;

    private String url;

    private List<String> membersName;
}
