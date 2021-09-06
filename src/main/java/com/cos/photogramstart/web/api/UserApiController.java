package com.cos.photogramstart.web.api;


import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDTO;
import com.cos.photogramstart.web.dto.user.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("api/user/{id}")
    public CMRespDTO<?> update(@PathVariable("id") int id,
                               UserUpdateDTO userUpdateDTO,
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        User userEntity = userService.회원수정(id, userUpdateDTO.toEntity());

        principalDetails.setUser(userEntity); //세션 정보 변경

        return new CMRespDTO<>(1,"회원수정 완료", userEntity);

    }
}
