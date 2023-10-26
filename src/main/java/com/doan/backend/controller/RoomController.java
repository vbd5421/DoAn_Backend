package com.doan.backend.controller;

import com.doan.backend.model.Room;
import com.doan.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
public class RoomController {
    @Autowired
    RoomService roomService;

    /**
     *
     * @param hotelId
     * @return danh sách phòng của khách sạn
     */
    @GetMapping
    public ResponseEntity<?> getListRoomByHotel(@RequestParam(value = "hotelId")Long hotelId){
        return new ResponseEntity<>(roomService.getListRoomOfHotel(hotelId), HttpStatus.OK);
    }

    /**
     *
     * @param hotelId
     * @param room
     * @return thêm mới phòng của khách sạn
     */
    @PostMapping("/add")
    public ResponseEntity<?> addRoom(@RequestParam(value = "hotelId")Long hotelId,
                                     @RequestBody Room room){
        return new ResponseEntity<>(roomService.createOrUpdate(hotelId,room),HttpStatus.OK);
    }

    /**
     *
     * @param hotelId
     * @param room
     * @return chỉnh sửa thông tin phòng
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateRoom(@RequestParam(value = "hotelId")Long hotelId,
                                     @RequestBody Room room){
        return new ResponseEntity<>(roomService.createOrUpdate(hotelId,room),HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return đặt phòng chuyển status từ 0 -> 1
     */
    @PostMapping("booking/{id}")
    public ResponseEntity<?> bookingRoom(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.changeStatus(id),HttpStatus.OK);
    }

}
