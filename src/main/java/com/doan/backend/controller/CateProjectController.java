package com.doan.backend.controller;

import com.doan.backend.model.CateProject;
import com.doan.backend.service.CateProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cate-project")
public class CateProjectController {
    @Autowired
    CateProjectService cateProjectService;
    @GetMapping("")
    public ResponseEntity<?> getAllCateProject(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                               @RequestParam(name="pageSize",defaultValue = "10")int size,
                                               @RequestParam(name = "search",required = false)String searchInput){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(cateProjectService.getListCateProject(pageable,searchInput));
    }
    @GetMapping("/all")
    public ResponseEntity<?> getListAllCateProject() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return ResponseEntity.ok(cateProjectService.getAllCateProject(sort));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCateProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(cateProjectService.getCateById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createCateProject(@RequestBody CateProject cateProject) {
        return ResponseEntity.ok(cateProjectService.addOrUpdateCate(cateProject));
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateCateProject(@RequestBody CateProject cateProject) {
        return ResponseEntity.ok(cateProjectService.addOrUpdateCate(cateProject));
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteCateProject(@PathVariable Long id) {
        cateProjectService.deleteCateProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
