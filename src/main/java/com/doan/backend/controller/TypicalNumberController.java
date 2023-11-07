package com.doan.backend.controller;

import com.doan.backend.dto.TypicalNumberDTO;
import com.doan.backend.model.TypicalNumber;
import com.doan.backend.service.TypicalNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/number")
public class TypicalNumberController {
    @Autowired
    private TypicalNumberService typicalNumberService;


    @GetMapping("")
    public Page<TypicalNumberDTO> searchTitleAndDescription(
            @RequestParam(name="pageNo",defaultValue = "1")int page,
            @RequestParam(name="pageSize",defaultValue = "5")int size,
            @RequestParam(name = "search",required = false)String searchInput)
    {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return typicalNumberService.searchTitleAndDescription(pageable,searchInput);
    }

    @GetMapping("/home")
    public ResponseEntity<List<TypicalNumber>> listAll(){
        return ResponseEntity.ok().body(typicalNumberService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypicalNumber> findTNById(@PathVariable("id")Long id){
        return ResponseEntity.ok().body(typicalNumberService.findTNById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<TypicalNumber> add( @RequestBody TypicalNumberDTO typicalNumberDTO){
        return ResponseEntity.ok().body(typicalNumberService.addTN(typicalNumberDTO));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<TypicalNumber> update(@PathVariable("id")Long id,
                                                @RequestBody TypicalNumberDTO typicalNumberDTO){

        return  ResponseEntity.ok().body(typicalNumberService.updateTN(id,typicalNumberDTO));
    }


    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        typicalNumberService.deleteTN(id);
    }
}
