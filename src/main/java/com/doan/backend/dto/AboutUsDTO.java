package com.doan.backend.dto;


import com.doan.backend.model.Address;
import lombok.Data;

import java.util.Collection;

@Data
public class AboutUsDTO {
    private Long id;
    private String description;
    private String content;
    private String contentUav;
    private String videoLINK;
    private String phone;
    private String fax;
    private String email;
    private Collection<Address> addressCollection;
}