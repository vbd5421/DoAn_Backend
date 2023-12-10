package com.doan.backend.service;


import com.doan.backend.dto.SlidersDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Sliders;
import com.doan.backend.repository.SlidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class SlidersService {

    @Autowired
    private SlidersRepository slidersRepository;

    @Autowired
    private FileService fileService;

    public List<Sliders> listAllHomePage(){
        return slidersRepository.getAllByActiveAndStatusIsTrue();
    }

    public List<Sliders> listAll(){

        return slidersRepository.getAllByStatusIsTrue();
    }

    public SlidersDTO findById(Long id){
        Sliders sliders = slidersRepository.findById(id).orElseThrow(() -> new ResourceException("Không tìm thấy slider với id là: " + id));
        SlidersDTO slidersDTO = new SlidersDTO();
        slidersDTO.setId(sliders.getId());
        slidersDTO.setName(sliders.getName());
        slidersDTO.setOriginalFileName(sliders.getOriginalFileName());
        slidersDTO.setPathFile(sliders.getPathFile());
        slidersDTO.setPathUrl(sliders.getPathUrl());
        slidersDTO.setActive(sliders.getActive());
        slidersDTO.setType(sliders.getType());
        return slidersDTO;
    }

    public Sliders add(MultipartFile file) throws IOException {
        if(file != null){
            Sliders sliders = fileService.uploadSliders(file);
            return slidersRepository.save(sliders);
        }else{
            throw new ResourceException("Vui lòng chèn vào ảnh");
        }
    }

    public Sliders update(Long id, MultipartFile file) throws IOException {

        Sliders updateSliders= slidersRepository.findById(id).orElseThrow(()->new ResourceException("Không tìm thấy slider với id là: "+id));
        if(file != null){
            Sliders sliders = fileService.uploadSliders(file);
            updateSliders.setType(sliders.getType());
            updateSliders.setName(sliders.getName());
            updateSliders.setOriginalFileName(sliders.getOriginalFileName());
            updateSliders.setPathUrl(sliders.getPathUrl());
            updateSliders.setPathFile(sliders.getPathFile());
            updateSliders.setActive(true);
            return slidersRepository.save(updateSliders);
        }else{
            throw new ResourceException("Vui lòng chèn vào ảnh");
        }
    }

    public Sliders hide(Long id){
        Sliders sliders = slidersRepository.findById(id)
                .orElseThrow(()->new ResourceException("Không tìm thấy id của slide để hiện"));
        sliders.setActive(false);
        return slidersRepository.save(sliders);
    }


    public Sliders show(Long id){
        Sliders sliders = slidersRepository.findById(id)
                .orElseThrow(()->new ResourceException("Không tìm thấy id của slide để hiện"));
        sliders.setActive(true);
        return slidersRepository.save(sliders);
    }

    public Resource getImageBySliderName(String name) throws MalformedURLException {
        String imageName = slidersRepository.getImageByName(name);
        String pathFile  = slidersRepository.getPathFileByName(name);
        Path imagePath = Paths.get(pathFile + "/" +imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if(imageResource.exists() && imageResource.isReadable()){
            return imageResource;
        }else{
//            throw new NoPathFileException("Không tìm thấy đường dẫn ảnh");
            return null;
        }

    }

    public void delete(Long id){
        Sliders sliders = slidersRepository.findById(id).orElseThrow(() -> new ResourceException("khong tim thay id cua slide"));
        sliders.setStatus(false);
        slidersRepository.save(sliders);
    }
}