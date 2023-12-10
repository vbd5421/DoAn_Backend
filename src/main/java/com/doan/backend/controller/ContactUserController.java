package com.doan.backend.controller;


import com.doan.backend.dto.ContactUserDTO;
import com.doan.backend.model.ContactUser;
import com.doan.backend.service.ContactUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;


import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/contact")
public class ContactUserController {
    private final ContactUserService contactUserService ;


    @GetMapping("/users")
    public List<ContactUser> getContactUser(){
        return contactUserService.getContactUser();

    }
    @PostMapping("/add")
    public ResponseEntity<?> add( @RequestBody ContactUserDTO contactUser){
        return  ResponseEntity.ok().body(contactUserService.addContactUser(contactUser));
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id ){
        contactUserService.deleteContactUser(id);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<ContactUser> showCT(@PathVariable Long id){
        return ResponseEntity.ok().body(contactUserService.showContactUser(id));
    }
    @GetMapping("/hide/{id}")
    public ResponseEntity<ContactUser> hideCT(@PathVariable Long id){
        return ResponseEntity.ok().body(contactUserService.hideContactUser(id));
    }

    @GetMapping("/number")
    public Page<ContactUser> searchContactUser(
            @RequestParam(name="pageNo",defaultValue = "1")int page,
            @RequestParam(name="pageSize",defaultValue = "5")int size,
            @RequestParam(name = "search",required = false)String searchInput,
            @RequestParam(name = "active",required = false)Boolean active)
    {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return contactUserService.searchContactUserAndFilter(pageable,searchInput,active);
    }
    @GetMapping("/search")
    public Page<ContactUser> searchTime(
            @RequestParam(name="pageNo",defaultValue = "0")int page,
            @RequestParam(name="pageSize",defaultValue = "3")int size,
            @RequestParam(value = "startTime",required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startTime,
            @RequestParam(value = "endTime",required = false,defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endTime
    ){

        Pageable pageable = PageRequest.of(page, size);
        return  contactUserService.findByTimestampBetween( pageable,startTime ,endTime );

    }

}
