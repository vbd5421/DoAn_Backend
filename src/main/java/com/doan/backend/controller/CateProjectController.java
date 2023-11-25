package com.doan.backend.controller;

import com.doan.backend.model.CateProject;
import com.doan.backend.service.CatrProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cate-project")
public class CateProjectController {
    @Autowired
    CatrProjectService catrProjectService;
    @GetMapping("")
    public ResponseEntity<?> getAllCateProject(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                               @RequestParam(name="pageSize",defaultValue = "10")int size,
                                               @RequestParam(name = "search",required = false)String searchInput){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(catrProjectService.getListCateProject(pageable,searchInput));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCateProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(catrProjectService.getCateById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCateProject(@RequestPart CateProject cateProject) {
        return ResponseEntity.ok(catrProjectService.addOrUpdateCate(cateProject));
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateCateProject(@RequestPart CateProject cateProject) {
        return ResponseEntity.ok(catrProjectService.addOrUpdateCate(cateProject));
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteCateProject(@PathVariable Long id) {
        catrProjectService.deleteCateProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
