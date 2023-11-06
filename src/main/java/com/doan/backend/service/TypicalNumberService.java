package com.doan.backend.service;

import com.doan.backend.dto.TypicalNumberDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.TypicalNumber;
import com.doan.backend.repository.TypicalNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypicalNumberService {
    @Autowired
    private TypicalNumberRepository typicalNumberRepository;

    public Page<TypicalNumberDTO> searchTitleAndDescription(Pageable pageable, String searchInput){
        //TO DO config searchInput
        return typicalNumberRepository.searchTitleAndDescription(pageable,searchInput)
                .map(typicalNumber -> new TypicalNumberDTO(
                        typicalNumber.getId(),
                        typicalNumber.getDescription(),
                        String.valueOf(typicalNumber.getNum()),
                        typicalNumber.getIcon(),
                        typicalNumber.getActive()
                ));
    }

    public List<TypicalNumber> listAll(){
        return typicalNumberRepository.listAll();
    }

    public TypicalNumber findTNById(Long id){
        TypicalNumber typicalNumber = typicalNumberRepository.findById(id).
                orElseThrow(()-> new ResourceException("Không tìm thấy id: "+ id));
        return typicalNumberRepository.save(typicalNumber);
    }



    public TypicalNumber addTN(TypicalNumberDTO typicalNumberDTO){

        TypicalNumber typicalNumber = new TypicalNumber();

        String num = typicalNumberDTO.getNum();

        String icon = typicalNumberDTO.getIcon();


        String description = typicalNumberDTO.getDescription();
        typicalNumber.setNum(Integer.parseInt(num));
        typicalNumber.setActive(true);
        typicalNumber.setIcon(typicalNumberDTO.getIcon());
        typicalNumber.setDescription(typicalNumberDTO.getDescription());
        return typicalNumberRepository.save(typicalNumber);
    }


    public TypicalNumber updateTN(Long id,TypicalNumberDTO typicalNumberDTO){
        Optional<TypicalNumber> typicalNumberOpt = typicalNumberRepository.findById(id);
        if (!typicalNumberOpt.isPresent()){
            throw new ResourceException("Number với id " + id + " không tồn tại để update");
        }

        String num = typicalNumberDTO.getNum();

        String icon = typicalNumberDTO.getIcon();

        String description = typicalNumberDTO.getDescription();

        TypicalNumber typicalNumberFromDB = typicalNumberOpt.get();
        typicalNumberFromDB.setNum(Integer.parseInt(num));
        typicalNumberFromDB.setIcon(icon);
        typicalNumberFromDB.setDescription(description);
        typicalNumberFromDB.setActive(true);
        return  typicalNumberRepository.save(typicalNumberFromDB);
    }

    public void deleteTN(Long id){
        TypicalNumber typicalNumber = typicalNumberRepository.findById(id).orElseThrow(()
                -> new ResourceException("Không tìm thấy number với id là: " + id));
        typicalNumber.setActive(false);
        typicalNumberRepository.save(typicalNumber);
    }
}
