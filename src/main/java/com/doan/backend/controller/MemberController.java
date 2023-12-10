package com.doan.backend.controller;

import com.doan.backend.dto.MemberDTO;
import com.doan.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @GetMapping("")
    ResponseEntity<?> getListMember(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                    @RequestParam(name="pageSize",defaultValue = "10")int size,
                                    @RequestParam(name = "search",required = false)String searchInput){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(memberService.getListMember(pageable,searchInput));

    }

    @GetMapping("/all")
    ResponseEntity<?> getAllMember() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return ResponseEntity.ok(memberService.getAllMember(sort));
    }

    /**
     *
     * @param id
     * @return thành viên kèm cả list project và sản phẩm của thành viên
     */
    @GetMapping("/{id}")
    ResponseEntity<?> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberbyId(id));
    }
    @GetMapping("/home/{url}")
    public ResponseEntity<?> getByUrl(@PathVariable("url") String url) {
        return ResponseEntity.ok(memberService.getMemberByUrl(url));
    }
    @PostMapping("/create")
    ResponseEntity<?> addMember(@RequestPart MemberDTO memberDTO,
                                @RequestPart(required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(memberService.AddOrUpdate(memberDTO,file));
    }
    @PostMapping("/update/{id}")
    ResponseEntity<?> updateMember(@RequestPart MemberDTO memberDTO,
                                @RequestPart(required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(memberService.AddOrUpdate(memberDTO,file));
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable Long id) throws MalformedURLException {
        return ResponseEntity.ok(memberService.getImageByMemberId(id));
    }
    @PostMapping("/delete/{id}")
    ResponseEntity<?> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
