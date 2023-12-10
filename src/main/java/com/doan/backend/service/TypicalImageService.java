package com.doan.backend.service;


import com.doan.backend.dto.TypicalImageDTO;
import com.doan.backend.exception.ResourceException;
import com.doan.backend.model.Image;
import com.doan.backend.model.TypicalImage;
import com.doan.backend.repository.ImageRepository;
import com.doan.backend.repository.TypicalImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class TypicalImageService {
    @Autowired
    private TypicalImageRepository typicalImageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileService fileService;

    public List<TypicalImage> listAll(){
        return typicalImageRepository.listAll();
    }

    public Page<TypicalImage> findAll(Pageable pageable){
        return typicalImageRepository.findAll(pageable);
    }

    public TypicalImage findById(Long id){
        return typicalImageRepository.findById(id).orElseThrow(()->new ResourceException("không tìm thấy ảnh với id là: "+id));
    }

    public TypicalImage addFromImage(Long id){
        if(typicalImageRepository.getTypicalImageByImage_Id(id).isPresent()){
            throw new ResourceException("Ảnh đã có trong thư viện hoạt động");
        }
        Image image = imageRepository.findById(id).get();
        TypicalImage typicalImage = new TypicalImage();
        typicalImage.setImage(image);
        typicalImage.setActive(true);
        typicalImage.setStatus(true);
        return typicalImageRepository.save(typicalImage);
    }

    public TypicalImage addTI(TypicalImageDTO typicalImageDTO, MultipartFile file) throws IOException{
        String caption = typicalImageDTO.getCaption();
        String description = typicalImageDTO.getDescription();

        TypicalImage typicalImage = new TypicalImage();
        typicalImage.setCaption(caption);
        typicalImage.setDescription(description);
        typicalImage.setActive(true);
        typicalImage.setStatus(true);
        if (file != null) {
            typicalImage.setImage(fileService.uploadImage(file));
        }else{
            throw new ResourceException("Vui lòng chèn vào ảnh");
        }
        return typicalImageRepository.save(typicalImage);
    }

    public Resource getImageByName(String name) throws MalformedURLException {
        String imageName = typicalImageRepository.getImageByName(name);
        String pathFile  = typicalImageRepository.getPathFileByName(name);
        Path imagePath = Paths.get(pathFile + "/" +imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if(imageResource.exists() && imageResource.isReadable()){
            return imageResource;
        }else{
//            throw new NoPathFileException("Không tìm thấy đường dẫn ảnh");
            return null;
        }

    }

    public TypicalImage updateTI(Long id,TypicalImageDTO typicalImageDTO,MultipartFile file) throws IOException{
        Optional<TypicalImage> op = typicalImageRepository.findById(id);
        if(op.isEmpty()){
            throw new ResourceException("Không tồn tại ảnh để sửa");
        }
        String caption = typicalImageDTO.getCaption();
        String description = typicalImageDTO.getDescription();

        TypicalImage updateTypicalImage = op.get();
        updateTypicalImage.setCaption(caption);
        updateTypicalImage.setDescription(description);
        updateTypicalImage.setActive(true);
        if(file!= null){
            Image image = imageRepository.save(fileService.uploadImage(file));
            updateTypicalImage.setImage(image);
        }
        updateTypicalImage.setStatus(true);
        return typicalImageRepository.save(updateTypicalImage);
    }

    public TypicalImage hideTypicalImage(Long id){
        TypicalImage typicalImage =typicalImageRepository.findById(id).orElseThrow(()->new ResourceException("Không tìm thấy ảnh để ẩn"));
        typicalImage.setActive(false);
        return typicalImageRepository.save(typicalImage);

    }
    public TypicalImage showTypicalImage(Long id){
        TypicalImage typicalImage =typicalImageRepository.findById(id).orElseThrow(()->new ResourceException("Không tìm thấy ảnh để hiện"));
        typicalImage.setActive(true);
        return typicalImageRepository.save(typicalImage);
    }

    //    public void deleteTypicalImage(Long id){
//        typicalImageRepository.deleteById(id);
//
//    }
    public TypicalImage deleteTypicalImage(Long id){
        TypicalImage typicalImage =typicalImageRepository.findById(id).orElseThrow(()->new ResourceException("Không tìm thấy ảnh để xoá"));
        typicalImage.setStatus(false);
        return typicalImageRepository.save(typicalImage);

    }
}
