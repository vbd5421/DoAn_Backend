package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Hotel;
import com.doan.backend.model.Room;
import com.doan.backend.repository.HotelRepository;
import com.doan.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;
    List<Room> getListRoomOfHotel(Long hotelId) {
        return roomRepository.getListRoomOfHotel(hotelId);
    }
    Room createOrUpdate(Long hotelId,Room room) {
        Room roomNew = new Room();
        Hotel hotel = hotelRepository.findById(hotelId).get();
        if(roomNew.getId() != null) {
            roomNew = roomRepository.findById(room.getId())
                    .orElseThrow(() -> new ResourceException("không tìm thấy phong"));
        }
        roomNew.setName(room.getName());
        roomNew.setType(room.getType());
        roomNew.setPrice(room.getPrice());
        roomNew.setStatus(0L);
        roomNew.setDescription(room.getDescription());
        roomNew.setHotel(hotel);
        return roomRepository.save(room);

    }
}
