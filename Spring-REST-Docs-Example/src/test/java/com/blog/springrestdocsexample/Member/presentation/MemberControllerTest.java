package com.blog.springrestdocsexample.Member.presentation;

import com.blog.springrestdocsexample.Member.applicaation.MemberService;
import com.blog.springrestdocsexample.Member.common.model.Address;
import com.blog.springrestdocsexample.Member.domain.EmailAccount;
import com.blog.springrestdocsexample.Member.presentation.model.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import static com.blog.springrestdocsexample.ApiDocumentUtils.getDocumentRequest;
import static com.blog.springrestdocsexample.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
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
    @Test
    void 회원이_등록된다() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        //given
        MemberRequest memberRequest = new MemberRequest(
                new EmailAccount("qwe@naver.com"),
                "1234",
                new Address("대한민국","어딘가","123-456"));

        ConstrainedFields fields = new ConstrainedFields(MemberRequest.class);

        given(service.addMember(memberRequest)).willReturn(1L);

        //when
        ResultActions result = mvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRequest)));



        //then
        result.andExpect(status().isOk())
                .andDo(document("member",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fields.withPath("emailAccount.email")
                                        .type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fields.withPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("비밀번호"),
                                fieldWithPath("address.address1").type(JsonFieldType.STRING).description("주소1"),
                                fieldWithPath("address.address2").type(JsonFieldType.STRING).description("주소2"),
                                fieldWithPath("address.zipcode").type(JsonFieldType.STRING).description("우편번호")
                        )
                ));
    }


    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}

