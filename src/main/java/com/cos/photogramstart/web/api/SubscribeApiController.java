package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<CMRespDTO<?>> subscribe(@PathVariable("toUserId") int toUserId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails){

        subscribeService.구독하기(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CMRespDTO<>(1, "구독하기 성공", null ), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<CMRespDTO<?>> unSubscribe(@PathVariable("toUserId") int toUserId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails){

        subscribeService.구독취소하기(principalDetails.getUser().getId(), toUserId);

        return new ResponseEntity<>(new CMRespDTO<>(1, "구독취소 성공", null), HttpStatus.OK);
    }
}
