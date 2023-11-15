package com.doan.backend.controller;


import com.doan.backend.dto.ProjectDTO;
import com.doan.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @GetMapping("")
    ResponseEntity<?> getAllProject(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                    @RequestParam(name="pageSize",defaultValue = "4")int size,
                                    @RequestParam(name="name",required = false)String name) {
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(projectService.getAllProject(name,pageable));

    }
    @GetMapping("/{id}")
    ResponseEntity<?> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
    @PostMapping("/create")
    ResponseEntity<?> createProject(@RequestPart ProjectDTO project,
                                    @RequestPart(required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(projectService.createOrUpdate(project,file));
    }
    @PostMapping("/update/{id}")
    ResponseEntity<?> updateProject(@RequestPart ProjectDTO project,
                                    @RequestPart(required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(projectService.createOrUpdate(project,file));
    }
    @PostMapping("/delete/{id}")
    ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
