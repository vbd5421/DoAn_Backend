package com.doan.backend.dto;

import com.doan.backend.model.Image;
import lombok.Data;


import java.time.LocalDate;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;

    private String title;

    private String content;

    private String description;
    private LocalDate date;
    private Image image;
    private String url;
    private Boolean active;

}