package com.doan.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Sliders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slider_id")
    private Long id;

    private String name;

    private String originalFileName;

    private String pathFile;

    @Column(name = "path_url")
    private String pathUrl;

    private String type;

    private Boolean active;

    private Boolean status;

    public Sliders(String name,String originalFileName, String pathFile, String pathUrl, String type, Boolean active,Boolean status) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.pathFile = pathFile;
        this.pathUrl = pathUrl;
        this.type = type;
        this.active = active;
        this.status = status;
    }
}