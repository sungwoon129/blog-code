package com.blog.springrestdocsexample.common.presentation;

import com.blog.springrestdocsexample.Member.common.model.ResultCode;
import com.blog.springrestdocsexample.common.presentation.model.ResultCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ResultCodeController {

    @GetMapping("/docs/code")
    public Map<String, ResultCodeResponse> getResultCodeEnums() {

        Map<String,ResultCodeResponse> map = new HashMap<>();

        for (ResultCode code : ResultCode.values()) {
            map.put(code.name(),new ResultCodeResponse(code));
        }

        return map;
    }
}
