package com.blog.springrestdocsexample.Member.presentation.model;

import com.blog.springrestdocsexample.Member.common.model.Address;
import com.blog.springrestdocsexample.Member.domain.EmailAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private String email;
    private Address address;

}
