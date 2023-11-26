package com.doan.backend.controller;

import com.doan.backend.model.LinkWebsite;
import com.doan.backend.service.LinkWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/link-website")
public class LinkWebsiteController {
    @Autowired
    LinkWebsiteService linkWebsiteService;

    @GetMapping("")
    public ResponseEntity<?> getAllLink() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return ResponseEntity.ok(linkWebsiteService.getAllLink(sort));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createLink(@RequestBody LinkWebsite linkWebsite) {
        return ResponseEntity.ok(linkWebsiteService.addOrUpdate(linkWebsite));
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateLink(@RequestBody LinkWebsite linkWebsite) {
        return ResponseEntity.ok(linkWebsiteService.addOrUpdate(linkWebsite));
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> createLink(@PathVariable Long id) {
        linkWebsiteService.deleteLink(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
