package com.doan.backend.service;


import com.doan.backend.model.Image;
import com.doan.backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class FileService {
    @Autowired
    private ImageRepository imageRepository;



    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    private static final Path staticPath = Paths.get("static");
    private static final Path imagePath = Paths.get("images");
    private static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
    private static final Path folderPath = Paths.get(calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1));


    public void deleteImage(Image image) throws IOException {
        image.setActive(false);
        imageRepository.save(image);
//        fileRepository.deleteById(image.getId());

    }

    public Resource getImageByName(String name) throws MalformedURLException {
        String imageName = imageRepository.getImageByName(name);
        String pathFile  = imageRepository.getPathFileByName(name);
        Path imagePath = Paths.get(pathFile + "/" +imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if(imageResource.exists() && imageResource.isReadable()){
            return imageResource;
        }else{
//            throw new NoPathFileException("Không tìm thấy đường dẫn ảnh");
            return null;
        }
    }



    public Image uploadImage(MultipartFile file) throws IOException {
        //create folder following year/month
        if(file!=null){
            try{
                if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(folderPath))) {
                    Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(folderPath));
                }
            }catch (Exception ex) {
                throw new RuntimeException("Không thể tạo đường dẫn cho ảnh.", ex);
            }
            Instant instant = Instant.now();
            String filename = instant.toEpochMilli() + "_" + file.getOriginalFilename();

            //Tạo 1 đường dẫn mới để lưu file ở local

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath)
                        .resolve(folderPath).resolve(filename);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Không thể lưu ảnh có tên là: " + filename, ioe);
            }

            System.gc();
            String absoluteURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(folderPath.toString()).path("/").path(filename).toUriString();
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(absoluteURL);
            String path = uriComponentsBuilder.build().getPath();
            Image image = imageRepository.findAllByName(filename);
            if (image==null){
                return new Image(
                        filename,
                        file.getOriginalFilename(),
                        staticPath.resolve(imagePath).resolve(folderPath).toString(),
                        path,
                        file.getContentType(), true
                );
            }else{
                image.setName(filename);
                image.setOriginalFileName(file.getOriginalFilename());
                image.setPathFile(staticPath.resolve(imagePath).resolve(folderPath).toString());
                image.setPathUrl(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(folderPath.toString()).path("/").path(filename).toUriString());
                image.setType(file.getContentType());
                image.setActive(true);
                return imageRepository.save(image);
            }
        }else{
            return null;
        }

    }

    /***
     * Download an image from DB to local
     * @param id
     */
    public void downloadImage(Long id) {
        // find image by id
        Optional<Image> image = imageRepository.findById(id);
        // get image path Url to prepare for download
        String imageUrl = image.get().getPathUrl();
        // confict download path
        String saveDirectory = System.getProperty("user.home") + "/Downloads";
        String savePath = saveDirectory + "/downloaded-image.jpg";
        // other settings
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // download image to pre-set path
        ResponseEntity<byte[]> response = restTemplate.exchange(imageUrl, HttpMethod.GET, entity, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            try (OutputStream outputStream = new FileOutputStream(savePath)) {
                outputStream.write(response.getBody());
                System.out.println("Tải xuống thành công.");
            } catch (IOException e) {
                System.out.println("Tải xuống thất bại: " + e.getMessage());
            }
        } else {
            System.out.println("Thất bại khi tải ảnh xuống. Status code: " + response.getStatusCode());
        }
    }
}
