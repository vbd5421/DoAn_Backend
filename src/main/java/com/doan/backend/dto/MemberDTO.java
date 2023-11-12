package com.doan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String fullName;
    private String description;
    private String position;
    private String degree;
    private List<String> productName;
    private List<String> projectName;
}
