package com.cos.photogramstart.web.api;


import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDTO;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDTO;
import com.cos.photogramstart.web.dto.user.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId,
                                                   MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){

        User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);

        principalDetails.setUser(userEntity); //세션 변경

        return new ResponseEntity<>(new CMRespDTO<>(1, "프로필 사진 변경 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<CMRespDTO<?>> subscribeList(@PathVariable int pageUserId,
                                                      @AuthenticationPrincipal PrincipalDetails principalDetails){

        List<SubscribeDTO> subscribeDTO = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDTO<>(1, "구독자 정보 리스트 가져오기 성공", subscribeDTO), HttpStatus.OK);

    }

    @PutMapping("api/user/{id}")
    public CMRespDTO<?> update(@PathVariable("id") int id,
                               @Valid UserUpdateDTO userUpdateDTO,
                               BindingResult bindingResult, //꼭 @Valid 가 적혀있는 다음 파라미터에 적어야됨.
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

            User userEntity = userService.회원수정(id, userUpdateDTO.toEntity());

            principalDetails.setUser(userEntity); //세션 정보 변경

            return new CMRespDTO<>(1,"회원수정 완료", userEntity); //응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다.
        }

}
