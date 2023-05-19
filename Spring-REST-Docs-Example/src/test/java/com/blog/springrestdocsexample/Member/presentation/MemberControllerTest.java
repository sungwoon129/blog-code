package com.blog.springrestdocsexample.Member.presentation;

import com.blog.springrestdocsexample.Member.applicaation.MemberService;
import com.blog.springrestdocsexample.Member.common.model.Address;
import com.blog.springrestdocsexample.Member.presentation.model.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.blog.springrestdocsexample.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.blog.springrestdocsexample.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@AutoConfigureRestDocs
@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mvc;

    @MockBean
    MemberService service;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    @DisplayName("회원이 등록된다")
    @Test
    void saveMember() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        //given
        MemberRequest memberRequest = new MemberRequest(
                "qwe@naver.com",
                "1234",
                new Address("대한민국","어딘가","123-456"));



        //when
        ResultActions result = mvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRequest)));



        //then
        result.andExpect(status().isOk())
                .andDo(document("member",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email")
                                        .type(JsonFieldType.STRING)
                                        .attributes(key("constraints").value("이메일 형식이어야 합니다"))
                                        .description("이메일"),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .attributes(key("constraints").value("길이가 4이상이어야 합니다."))
                                        .description("비밀번호"),
                                fieldWithPath("address.address1").type(JsonFieldType.STRING).description("주소1").optional(),
                                fieldWithPath("address.address2").type(JsonFieldType.STRING).description("주소2").optional(),
                                fieldWithPath("address.zipcode").type(JsonFieldType.STRING).description("우편번호").optional()
                        ),
                        responseFields(
                                //beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("code")
                                        .type(JsonFieldType.STRING)
                                        .description("결과코드"),
                                fieldWithPath("message")
                                        .type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data")
                                        .type(JsonFieldType.NUMBER)
                                        .description("등록된 회원 ID")

                        )

                ));
    }


    @DisplayName("한명의 회원정보를 얻는다")
    @Test
    void findById() throws Exception {
        //given
        Long id = 1L;

        //when
        ResultActions result = mvc.perform(get("/member/" + id)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk());

    }

}

