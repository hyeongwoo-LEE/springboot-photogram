package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //모든 예외를 낚아챔.
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){

        //CMRespDTO, Script 비교
        //1. 클라이언트에 응답할 때는 Script 가 좋음 - 브라우저
        //2. Ajax 통신 - CMRespDTO - 프론트엔트 개발자
        //3. Android 통신 - CMRespDTO - 앱 개발
        return Script.back(e.getErrorMap().toString());
    }
}
