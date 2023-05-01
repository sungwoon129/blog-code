package com.blog.springvalidationcommonmodule.presentation.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberRequestDtoTest {

    @Test
    void MemberRequestDto_객체가_생성된다() {
        String email = "hi";
        Long id = 1L;
        String phone = "123";
        String name = "qwe";
        MemberRequestDto dto = new MemberRequestDto(id,name,phone,email);

        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getPhoneNumber()).isEqualTo(phone);
    }

}