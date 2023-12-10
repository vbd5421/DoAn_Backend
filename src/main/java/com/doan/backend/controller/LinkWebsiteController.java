package com.doan.backend.controller;

import com.doan.backend.model.LinkWebsite;
import com.doan.backend.service.LinkWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getAllLink(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                        @RequestParam(name="pageSize",defaultValue = "10")int size,
                                        @RequestParam(name = "name",required = false)String name) {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(linkWebsiteService.getAllLink(pageable, name));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLinkById(@PathVariable Long id) {
        return ResponseEntity.ok(linkWebsiteService.getLinkById(id));
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
