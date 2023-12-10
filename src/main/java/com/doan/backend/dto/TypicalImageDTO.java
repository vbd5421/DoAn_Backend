package com.doan.backend.dto;


import com.doan.backend.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypicalImageDTO {

    private Long id;

    private String caption;

    private String description;

    private Image image;

    private Boolean active;

    private Boolean status;
}