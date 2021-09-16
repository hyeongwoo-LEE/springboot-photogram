package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${file.path}")
    private String uploadFolder;

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true) //영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush 반영 x
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //images에 좋아요 상태 담기
        images.forEach((image -> {

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((likes -> {
                if(likes.getUser().getId() == principalId){
                    image.setLikeState(true);
                }
            }));
        }));

        return images;
    }

    @Transactional
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

        imageRepository.save(image);



    }



}
