package com.blog.springrestdocsexample.common.presentation.model;


import com.blog.springrestdocsexample.Member.common.model.ResultCode;

public class ResultCodeResponse {

    private String code;
    private String message;


    public ResultCodeResponse() {}

    public ResultCodeResponse(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
