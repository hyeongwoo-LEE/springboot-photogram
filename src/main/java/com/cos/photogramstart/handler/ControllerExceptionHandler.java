package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //모든 예외를 낚아챔.
public class ControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> validationException(CustomValidationException e){
        return e.getErrorMap();
    }
}
