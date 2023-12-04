package com.doan.backend.controller;


import com.doan.backend.model.Image;
import com.doan.backend.repository.ImageRepository;
import com.doan.backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;
    //ServletUriComponentsBuilder.fromCurrentContextPath()
    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/downloadFile")
    public ResponseEntity<?> downloadFile(@RequestBody Image image) throws IOException {
        String getURL = ServletUriComponentsBuilder.fromCurrentContextPath().path(image.getPathUrl()).toUriString();
        InputStreamResource in = new InputStreamResource(new URL(getURL).openStream());
        String contentType = image.getType();
        String headerValue = "attachment;file=\"" + in.getFilename() +"\"";
        return ResponseEntity.ok().contentType( MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION,headerValue).body(in);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addFile(@RequestPart(name = "image",required = false) MultipartFile file) throws IOException {
        Image image = imageRepository.save(fileService.uploadImage(file));
        return ResponseEntity.ok().body(image);
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<?>deleteImage(@RequestBody Image image){
        try{
            this.fileService.deleteImage(image);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted the file successfully: "+image.getName());
        }catch (IOException e){
            return  ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/image")
    public Page<Image> search(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "16") int pageSize){
        String sortDirection = "desc";
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return imageRepository.getImageByActive(pageable);
    }
    //
    @GetMapping("/{id}")
    public ResponseEntity<Image> findImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not found the "+id));
        return ResponseEntity.ok().body(image);
    }


    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getImageByName(@PathVariable String name) throws MalformedURLException {
        return ResponseEntity.ok(fileService.getImageByName(name));
    }

    @GetMapping("/image/all")
    public ResponseEntity<List<Image>> getAllImage() {
        return ResponseEntity.ok(imageRepository.getImageByNameIsNotNull());
    }

    @PostMapping("image/update")
    public ResponseEntity<Image> showHome(@RequestBody Image image){
        return ResponseEntity.ok(imageRepository.save(image));
    }

    /***
     * Download an image found by its id
     * @param id
     */
    @GetMapping("image/download/{id}")
    public void downloadImage(@PathVariable Long id) {
        fileService.downloadImage(id);
    }
}
