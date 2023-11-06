package com.doan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypicalNumberDTO {
    private Long id;

    private String description;

    private String num;

    private String icon;
    private Boolean active;
}
