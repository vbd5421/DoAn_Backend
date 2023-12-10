package com.doan.backend.dto;

import com.doan.backend.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String fullName;
    private String description;
    private Image image;
    private Date birthDate;
    private Date timeJoin;

    private String phone;
    private String email;
    private String url;
    private String position;
    private String degree;
    private List<String> productName;
    private List<String> projectName;
    private String externalProject;

    public MemberDTO(String fullName, String description, Image image, Date birthDate, Date timeJoin,
                     String phone, String email, String url, String position, String degree, List<String> productName,
                     List<String> projectName) {
        this.fullName = fullName;
        this.description = description;
        this.image = image;
        this.birthDate = birthDate;
        this.timeJoin = timeJoin;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.position = position;
        this.degree = degree;
        this.productName = productName;
        this.projectName = projectName;
    }


}
