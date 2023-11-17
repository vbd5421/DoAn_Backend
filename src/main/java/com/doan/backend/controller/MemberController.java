package com.doan.backend.controller;

import com.doan.backend.dto.MemberDTO;
import com.doan.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @GetMapping("")
    ResponseEntity<?> getListMember(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                    @RequestParam(name="pageSize",defaultValue = "10")int size,
                                    @RequestParam(name = "name",required = false)String name,
                                    @RequestParam(name = "degree",required = false)String degree,
                                    @RequestParam(name = "position",required = false)String position){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(memberService.getListMember(pageable,name,degree,position));

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
    @PostMapping("/create")
    ResponseEntity<?> addMember(@RequestPart MemberDTO memberDTO,
                                @RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.ok(memberService.AddOrUpdate(memberDTO,file));
    }
    @PostMapping("/update")
    ResponseEntity<?> updateMember(@RequestPart MemberDTO memberDTO,
                                @RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.ok(memberService.AddOrUpdate(memberDTO,file));
    }
    @PostMapping("/delete/{id}")
    ResponseEntity<?> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
