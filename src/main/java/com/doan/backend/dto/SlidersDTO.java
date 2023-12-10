package com.doan.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlidersDTO {
    private Long id;
    private String name;
    private String originalFileName;
    private String pathFile;
    private String pathUrl;
    private String type;
    private Boolean active;
    private Boolean status;

}
