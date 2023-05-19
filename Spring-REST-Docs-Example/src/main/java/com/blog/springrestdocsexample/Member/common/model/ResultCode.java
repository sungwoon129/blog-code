package com.blog.springrestdocsexample.Member.common.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    OK("OK",""),
    ALREADY_EXISTED_MEMBER("ALREADY_EXISTED_MEMBER","이미 존재하는 회원입니다"),
    NO_EXISTS_MEMBER("NO_EXISTS_MEMBER","존재하지 않는 회원입니다.");


    private String code;
    private String message;


    private ResultCode(String code,String message) {
        this.code = code;
        this.message = message;
    }


}
