package com.doan.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "file_name")
    private String name;

    private String originalFileName;

    private String pathFile;

    @Column(name = "path_url")
    private String pathUrl;

    private String type;

    private Boolean active;

    public Image(String name,String originalFileName, String pathFile, String pathUrl, String type, Boolean active) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.pathFile = pathFile;
        this.pathUrl = pathUrl;
        this.type = type;
        this.active = active;
    }

}
