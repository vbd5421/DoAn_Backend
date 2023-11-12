package com.doan.backend.service;

import com.doan.backend.dto.AboutUsDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.AboutUs;
import com.doan.backend.model.Address;
import com.doan.backend.repository.AboutUsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class AboutUsService {
    @Autowired
    private AboutUsRepository aboutUsRepository;



    /***
     * Create new information for "About Us"
     * @param aboutUs payload
     * @return about us's information
     */
    public AboutUs createInformation(AboutUs aboutUs) {
        // get description
        String description = aboutUs.getDescription().trim();
        if (description.length() == 0 || description.length() > 2000) { // check if empty or too long
            throw new ResourceException("Vui lòng nhập miêu tả dưới 2000 ký tự");
        }
        // get content
        String content = aboutUs.getContent().trim();
        if (content.length() == 0) { // check if content is empty
            throw new ResourceException("Vui lòng nhập nội dung");
        }
        // get video link
        try {
            aboutUs.getVideoLINK().trim();
        } catch (Exception e) {
            throw new ResourceException("Vui lòng nhập đường dẫn");
        }

        String phone = aboutUs.getPhone().trim();
        if (phone.length() == 0) { // check phone length
            throw new ResourceException("Vui lòng nhập số điện thoại ");
        }
        // get fax
        String fax = aboutUs.getFax().trim();
        if (fax.length() == 0 || fax.length() > 15) { // check fax length
            throw new ResourceException("Vui lòng nhập số fax dưới 15 ký tự");
        }
        //get email
        String email = aboutUs.getEmail().trim();


        aboutUs.setDescription(description);
        aboutUs.setContent(content);
        aboutUs.setVideoLINK(aboutUs.getVideoLINK().trim());
        aboutUs.setPhone(phone);
        aboutUs.setFax(fax);
        aboutUs.setEmail(email);

        aboutUsRepository.save(aboutUs); // save information
        return aboutUs;
    }

    /***
     * Get information by id
     * @param id about us's id
     * @return about us's information
     */
    public AboutUs getAboutUsInformation(Long id) {
        Optional<AboutUs> aboutUsOpt = aboutUsRepository.findById(id);
        if(!aboutUsOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy thông tin");
        } else {
            AboutUs aboutUs = aboutUsOpt.get();
            return aboutUs;
        }
    }


    public AboutUs updateAboutUs (AboutUsDTO aboutUsDTO) {
        // find information by id
        AboutUs aboutUs = getAboutUsInformation(aboutUsDTO.getId());
        // get description
        String description = aboutUsDTO.getDescription().trim();
        if(description.length() == 0 || description.length() > 500) { // check if empty or too long
            throw new ResourceException("Vui lòng nhập miêu tả dưới 500 ký tự");
        }
        // get content
        String content = aboutUsDTO.getContent().trim();
        if(content.length() == 0) { // check if content is empty
            throw new ResourceException("Vui lòng nhập nội dung");
        }
        // get video link
        String link = aboutUsDTO.getVideoLINK().trim();
        if(link.length() == 0) { // chick if link is empty  q
            throw new ResourceException("Vui lòng nhập đường dẫn");
        }
        // get phone number
        String phone = aboutUsDTO.getPhone().trim();
        if(phone.length() != 10) { // check phone length
            throw new ResourceException("Vui lòng nhập số điện thoại gồm 10 số");
        }
        // get fax
        String fax = aboutUsDTO.getFax().trim();
        if(fax.length() == 0 || fax.length() >15) { // check fax length
            throw new ResourceException("Vui lòng nhập số fax dưới 15 ký tự");
        }
        //get email
        String email = aboutUsDTO.getEmail().trim();

        Collection<Address> address = aboutUsDTO.getAddressCollection();
//         update information
        aboutUs.setDescription(description);
        aboutUs.setContent(content);
        aboutUs.setVideoLINK(link);
        aboutUs.setPhone(phone);
        aboutUs.setFax(fax);
        aboutUs.setEmail(email);
//        aboutUs.setAddress(address);
        // save model
//        addressRepository.saveAll(address); // save addresses
        aboutUsRepository.save(aboutUs); // save information
        return aboutUsRepository.getReferenceById(aboutUsDTO.getId());
    }


    public AboutUs listAllInfor(){
        return aboutUsRepository.getAll();
    }
}
