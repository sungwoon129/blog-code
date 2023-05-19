package com.blog.springrestdocsexample.common.presentation;

import com.blog.springrestdocsexample.Member.common.model.ResultCode;
import com.blog.springrestdocsexample.document.utils.CustomResponseFieldsSnippet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.persistence.EnumType;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.blog.springrestdocsexample.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.blog.springrestdocsexample.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ResultCodeController.class)
@ExtendWith(RestDocumentationExtension.class)
public class ResultCodeControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("결과코드 Enum정보를 얻어온다")
    @Test
    void getResultCodeEnums() throws Exception {
        mvc.perform(get("/docs/code"))
                .andExpect(status().isOk())
                .andDo(document("code",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        customResponseFields("code-response",fieldDescriptors())));
    }

    private List<FieldDescriptor> fieldDescriptors() {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

        for (ResultCode resultCode : ResultCode.values()) {
            FieldDescriptor attributes =
                    fieldWithPath(resultCode.name()).type(JsonFieldType.OBJECT)
                            .attributes(
                                    key("code").value(resultCode.getCode()),
                                    key("message").value(resultCode.getMessage())
                            );
            fieldDescriptors.add(attributes);
        }

        return fieldDescriptors;
    }

    public static CustomResponseFieldsSnippet customResponseFields(
            String snippetFilePrefix,
            List<FieldDescriptor> fieldDescriptors) {
        return new CustomResponseFieldsSnippet(snippetFilePrefix, fieldDescriptors, true);
    }
}
