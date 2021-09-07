package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${file.path}")
    private String uploadFolder;

    private final ImageRepository imageRepository;

    public void 사진업로드(ImageUploadDTO imageUploadDTO, PrincipalDetails principalDetails){

        UUID uuid = UUID.randomUUID();

        String imageFilename = uuid + "_" + imageUploadDTO.getFile().getOriginalFilename(); //1.jpg

        System.out.println("이미지 파일이름: " + imageFilename);

        Path imageFilePath = Paths.get(uploadFolder + imageFilename);

        //통신, I/O -> 예외가 발생할 수 있다.
        try{

            Files.write(imageFilePath, imageUploadDTO.getFile().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //image 테이블에 저장
        Image image = imageUploadDTO.toEntity(imageFilename, principalDetails.getUser());

        Image save = imageRepository.save(image);

        System.out.println(save);

    }



}
