package com.doan.backend.controller;


import com.doan.backend.dto.SlidersDTO;
import com.doan.backend.model.Sliders;
import com.doan.backend.service.SlidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/sliders")
public class SlidersController {
    @Autowired
    private SlidersService slidersService;

    @GetMapping("/home")
    public List<Sliders> listAllHomePage(){
        return slidersService.listAllHomePage();
    }

    @GetMapping("")
    public List<Sliders> listAll(){
        return  slidersService.listAll();
    }

    @GetMapping("/{id}")
    public SlidersDTO findSlidersById(@PathVariable Long id){
        return slidersService.findById(id);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addSlide(@RequestPart(value = "slide",required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(slidersService.add(file));
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<?> updateSlide(@PathVariable("id") Long id,
                                         @RequestPart("sliders") Sliders sliders,
                                         @RequestPart(value = "slide", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(slidersService.update(id, file));
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getImageBySliderName(@PathVariable String name) throws MalformedURLException {
        return ResponseEntity.ok(slidersService.getImageBySliderName(name));
    }

    @GetMapping("/hide/{id}")
    public Sliders hide(@PathVariable Long id){
        return slidersService.hide(id);
    }

    @GetMapping("/show/{id}")
    public Sliders show(@PathVariable Long id){
        return slidersService.show(id);
    }


    @PostMapping("/status/{id}")
    public void status(@PathVariable Long id){
        slidersService.delete(id);
    }

}
