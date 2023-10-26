package com.doan.backend.controller;

import com.doan.backend.model.Hotel;
import com.doan.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hotel")
public class HotelController {
    @Autowired
    HotelService hotelService;

    /**
     *
     * @return danh sách khách sạn
     */
    @GetMapping
    public ResponseEntity<?> getAllHotel() {
        return new ResponseEntity<>(hotelService.getListHotel(), HttpStatus.OK);
    }

    /**
     *
     * @param hotel
     * @return thêm mới khách sạn
     */
    @PostMapping("/add")
    public ResponseEntity<?> addHotel(@RequestBody Hotel hotel) {
        return new ResponseEntity<>(hotelService.createOrUpdateHotel(hotel),HttpStatus.OK);
    }

    /**
     *
     * @param hotel
     * @return chỉnh sửa thông tin
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel) {
        return new ResponseEntity<>(hotelService.createOrUpdateHotel(hotel),HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return xóa khách sạn
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
