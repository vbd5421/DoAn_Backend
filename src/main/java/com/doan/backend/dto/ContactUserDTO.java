package com.doan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUserDTO {

    private String name;

    private  String phone;
    private String email;
    private  String content;
}
