package com.blog.springrestdocsexample.Member.presentation.model;

import com.blog.springrestdocsexample.Member.common.model.Address;
import com.blog.springrestdocsexample.Member.domain.EmailAccount;
import com.blog.springrestdocsexample.Member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {

    @Email
    private String email;

    @Size(min = 4)
    private String password;

    private Address address;


    public Member toEntity() {
        return new Member(email,password,address);
    }
}
