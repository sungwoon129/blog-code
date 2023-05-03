package com.blog.springvalidationcommonmodule.presentation;

import com.blog.springvalidationcommonmodule.application.MemberService;
import com.blog.springvalidationcommonmodule.domain.MemberRepository;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberRequestDto;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void member가_등록된다() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .email("qwe@naver.com")
                .id(1L)
                .name("qwe")
                .phoneNumber("01012344123")
                .build();

        // MemberRequestDto 유효성검사를 통과하지 못한 경우 테스트를 실패하고 400 응답
        mvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        mvc.perform(get("/members"))
                .andExpect(status().isOk());

        List<MemberResponseDto> all = memberRepository.findAll();
        assertThat(all.get(0).getEmail()).isEqualTo(requestDto.getEmail());
        assertThat(all.get(0).getId()).isEqualTo(requestDto.getId());
        assertThat(all.get(0).getName()).isEqualTo(requestDto.getName());
    }
}