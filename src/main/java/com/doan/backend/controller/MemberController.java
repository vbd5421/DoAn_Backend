package com.doan.backend.controller;

import com.doan.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @GetMapping("")
    ResponseEntity<?> getListMember(@RequestParam(name="pageNo",defaultValue = "1")int page,
                                    @RequestParam(name="pageSize",defaultValue = "10")int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(memberService.getListMember(pageable));

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
}
