package com.doan.backend.controller;

import com.doan.backend.model.Address;
import com.doan.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<Address> createAddress(@RequestBody Address address){
        return ResponseEntity.ok().body(addressService.createAddress(address));

    }

    @GetMapping("")
    public ResponseEntity<?> listAllAddress() {
        return ResponseEntity.ok(addressService.listAllAddress());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateAddress(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.updateAddress(address));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddress(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @PostMapping("/delete/{id}")

    public void delete(@PathVariable("id") Long id ){
        addressService.deleteAddress(id);
    }
}
