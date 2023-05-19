package com.blog.springrestdocsexample.Member.presentation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    private String code;
    private String message;
    private Object data;
}
