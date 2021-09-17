package com.cos.photogramstart.service;


import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${file.path}")
    private String uploadFolder;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;

    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile){

        UUID uuid = UUID.randomUUID();

        String imageFilename = uuid + "_" + profileImageFile.getOriginalFilename(); //1.jpg

        System.out.println("이미지 파일이름: " + imageFilename);

        Path imageFilePath = Paths.get(uploadFolder + imageFilename);

        //통신, I/O -> 예외가 발생할 수 있다.
        try{

            Files.write(imageFilePath, profileImageFile.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() ->
                new CustomApiException("유저를 찾을 수 없습니다."));

        userEntity.setProfileImageUrl(imageFilename);

        return userEntity;

    }


    @Transactional(readOnly = true)
    public UserProfileDTO 회원프로필(int pageUserId, int principalId){

        //select * from image where userId=:userId

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() ->
            new CustomException("해당 프로필 페이지는 없는 페이지입니다."));

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);

        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        //좋아요 카운트 추가하기
        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });

        return UserProfileDTO.builder()
                .user(userEntity)
                .imageCount(userEntity.getImages().size())
                .pageOwnerState(pageUserId == principalId)
                .subscribeState(subscribeState==1)
                .subscribeCount(subscribeCount)
                .build();

    }



    @Transactional
    public User 회원수정(int id, User user){

        System.out.println("id: " + id);

        //1. 영속화
        User userEntity = userRepository.findById(id).orElseThrow(() ->
                new CustomValidationApiException("찾을 수 없는 id입니다."));

        //2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    } //더티체킹이 일어나서 업데이트가 완료됨.
}
