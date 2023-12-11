package com.doan.backend.controller;


import com.doan.backend.dto.TypicalImageDTO;
import com.doan.backend.model.TypicalImage;
import com.doan.backend.service.TypicalImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/tImage")
public class TypicalImageController {
    @Autowired
    private TypicalImageService typicalImageService;

    @GetMapping("/show")
    public ResponseEntity<List<TypicalImage>> listAll(){
        return ResponseEntity.ok().body(typicalImageService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypicalImage> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(typicalImageService.findById(id));
    }

    @GetMapping("")
    public Page<TypicalImage> listAll(
            @RequestParam(name="pageNo",defaultValue = "1")int page,
            @RequestParam(name="pageSize",defaultValue = "16")int size)
    {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return typicalImageService.findAll(pageable);
    }

    @GetMapping("addimage/{id}")
    public ResponseEntity<TypicalImage> addFromImage(@PathVariable Long id){
        return ResponseEntity.ok().body(typicalImageService.addFromImage(id));
    }

    @PostMapping("/add")
    public ResponseEntity<TypicalImage> addTI(@RequestPart("typicalImage") TypicalImageDTO typicalImageDTO,
                                              @RequestPart(value = "imageFile", required = false) MultipartFile file) throws IOException{
        return ResponseEntity.ok().body(typicalImageService.addTI(typicalImageDTO,file));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<TypicalImage> updateTI(
            @PathVariable Long id,
            @RequestPart("typicalImage") TypicalImageDTO typicalImageDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile file) throws IOException{
        return ResponseEntity.ok().body(typicalImageService.updateTI(id,typicalImageDTO,file));
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getImageByName(@PathVariable String name) throws MalformedURLException {
        return ResponseEntity.ok(typicalImageService.getImageByName(name));
    }


    @GetMapping("/show/{id}")
    public ResponseEntity<TypicalImage> showTI(@PathVariable Long id){
        return ResponseEntity.ok().body(typicalImageService.showTypicalImage(id));
    }
    @GetMapping("/hide/{id}")
    public ResponseEntity<TypicalImage> hideTI(@PathVariable Long id){
        return ResponseEntity.ok().body(typicalImageService.hideTypicalImage(id));
    }

    @PostMapping("/delete/{id}")
    public void deleteTI(@PathVariable Long id){
        typicalImageService.deleteTypicalImage(id) ;
    }
}

