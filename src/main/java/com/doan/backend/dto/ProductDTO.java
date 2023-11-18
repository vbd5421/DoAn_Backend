package com.doan.backend.dto;

import com.doan.backend.model.Image;
import com.doan.backend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String content;
    private String description;

    private Date date;

    private Image image;

    private String url;

    private List<Member> members;
}
