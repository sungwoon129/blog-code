package com.blog.springvalidationcommonmodule;

import com.blog.springvalidationcommonmodule.exception.ValidCustomException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@SpringBootApplication
public class SpringValidationCommonModuleApplication {


    /*@Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, options);
                Throwable error = getError(requestAttributes);
                if (error instanceof ValidCustomException) {
                    errorAttributes.put("errors", ((ValidCustomException)error).getErrors());
                }
                return errorAttributes;
            }

        };
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SpringValidationCommonModuleApplication.class, args);
    }


}
