package com.blog.springrestdocsexample.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EmailAccount {

    @Email
    private String email;


}
