package com.doan.backend.controller;


import com.doan.backend.model.Project;
import com.doan.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<?> createProject(@RequestBody Project project,
                                    @RequestParam(name="listMember")List<Long> member) {
        return ResponseEntity.ok(projectService.createOrUpdate(project,member));
    }
    @PostMapping("/update")
    ResponseEntity<?> updateProject(@RequestBody Project project,
                                    @RequestParam(name="listMember")List<Long> member) {
        return ResponseEntity.ok(projectService.createOrUpdate(project,member));
    }
}
