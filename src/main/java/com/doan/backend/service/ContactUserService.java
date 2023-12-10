package com.doan.backend.service;


import com.doan.backend.dto.ContactUserDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.ContactUser;
import com.doan.backend.repository.ContactUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ContactUserService {
    private final ContactUserRepository contactUserRepository;


    public List<ContactUser> getContactUser() {
        return contactUserRepository.listAll();
    }

    public ContactUser addContactUser(ContactUserDTO contactUserDTO){
        ContactUser contactUser = new ContactUser();
        String name = contactUserDTO.getName();

        String phone = contactUserDTO.getPhone();

        String email = contactUserDTO.getEmail();


        String content = contactUserDTO.getContent();
        if(content == null || content.equals(" ")) {
            throw new ResourceException("Nhập email user");
        }
        contactUser.setName(name);
        contactUser.setPhone(phone);
        contactUser.setEmail(email);
        contactUser.setContent(content);
        contactUser.setDates(LocalDate.now());
        contactUser.setStatus(true);

        return contactUserRepository.save(contactUser);
    }

    public void deleteContactUser(Long id){
        contactUserRepository.deleteById(id);
    }



    public ContactUser hideContactUser(Long id){
        ContactUser contactUser =contactUserRepository.findById(id).orElseThrow(()->new ResourceException("khong tim thay id de an"));

        contactUser.setStatus(false);
        return contactUserRepository.save(contactUser);

    }
    public ContactUser showContactUser(Long id){

        ContactUser contactUser =contactUserRepository.findById(id).orElseThrow(()->new ResourceException("khong tim thay id de hien"));
        contactUser.setStatus(true);
        return contactUserRepository.save(contactUser);
    }


    public Page<ContactUser> searchContactUserAndFilter(Pageable pageable, String searchInput,Boolean active) {
        return  contactUserRepository.searchContactUserAndFilter(pageable,searchInput,active);

    }

    public Page<ContactUser> findByTimestampBetween(Pageable pageable, LocalDate startTime, LocalDate endTime) {
        return contactUserRepository.findByTimestampBetween(pageable,startTime,endTime);
    }


}
