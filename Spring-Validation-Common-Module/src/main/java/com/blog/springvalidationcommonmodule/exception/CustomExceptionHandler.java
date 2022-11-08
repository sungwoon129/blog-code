package com.blog.springvalidationcommonmodule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidCustomException.class)
    public Map<String, Object> validCustomExceptionHandler(ValidCustomException e) {
        Map<String,Object> errorAttributes = new HashMap<>();
        errorAttributes.put("errors",e.getErrors());

        return errorAttributes;
    }

}
