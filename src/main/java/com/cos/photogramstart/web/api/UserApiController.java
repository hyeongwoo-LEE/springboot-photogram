package com.cos.photogramstart.web.api;


import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDTO;
import com.cos.photogramstart.web.dto.user.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("api/user/{id}")
    public CMRespDTO<?> update(@PathVariable("id") int id,
                               @Valid UserUpdateDTO userUpdateDTO,
                               BindingResult bindingResult, //꼭 @Valid 가 적혀있는 다음 파라미터에 적어야됨.
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        System.out.println("------------------");
        System.out.println(bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성검사 실패함", errorMap);
        }else{
            User userEntity = userService.회원수정(id, userUpdateDTO.toEntity());

            principalDetails.setUser(userEntity); //세션 정보 변경

            return new CMRespDTO<>(1,"회원수정 완료", userEntity);
        }
    }
}
