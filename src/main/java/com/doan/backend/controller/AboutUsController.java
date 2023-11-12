package com.doan.backend.controller;

import com.doan.backend.dto.AboutUsDTO;
import com.doan.backend.model.AboutUs;
import com.doan.backend.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/about")
public class AboutUsController {
    @Autowired
    private AboutUsService aboutUsService;

    //* Worked
    /***
     * Create new information for "About Us"
     * @param aboutUs payload
     * @return about us's information
     */
    @PostMapping("/create")
    public ResponseEntity<?> createInformation(@RequestBody AboutUs aboutUs) {
        return ResponseEntity.ok(aboutUsService.createInformation(aboutUs));
    }


    @GetMapping("")
    public ResponseEntity<AboutUs> listAllInfor(){
        return ResponseEntity.ok(aboutUsService.listAllInfor());
    }

    //* Worked
    /***
     * Get information by id
     * @param id about us's id
     * @return about us's information
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAboutUsInformation(@PathVariable Long id) {
        return ResponseEntity.ok(aboutUsService.getAboutUsInformation(id));
    }

    //* Worked
    /***
     * Update information of "About us"
     * @param aboutUsDTO payload
     * @return updated data
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateInformation(@RequestBody AboutUsDTO aboutUsDTO) {
        return ResponseEntity.ok(aboutUsService.updateAboutUs(aboutUsDTO));
    }
}