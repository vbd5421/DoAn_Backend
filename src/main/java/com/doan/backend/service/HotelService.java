package com.doan.backend.service;

import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Hotel;
import com.doan.backend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    public List<Hotel> getListHotel() {
        return hotelRepository.findAll();
    }

    public Hotel createOrUpdateHotel(Hotel hotel) {
        Hotel hotelNew = new Hotel();
        if(hotel.getId() != null) {
            hotelNew = hotelRepository.findById(hotel.getId())
                    .orElseThrow(() -> new ResourceException("id khong ton tai"));
        }
        hotelNew.setName(hotel.getName());
        hotelNew.setDescription(hotel.getDescription());
        hotelNew.setFax(hotel.getFax());
        hotelNew.setEmail(hotel.getEmail());
        hotelNew.setPhone(hotel.getPhone());
        hotelNew.setAddress(hotel.getAddress());
        return hotelRepository.save(hotelNew);
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceException("id không tồn tại"));
        hotelRepository.delete(hotel);
    }

}
