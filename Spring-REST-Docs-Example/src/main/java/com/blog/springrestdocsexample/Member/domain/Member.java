package com.blog.springrestdocsexample.Member.domain;

import com.blog.springrestdocsexample.Member.common.model.Address;
import com.blog.springrestdocsexample.Member.presentation.model.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MBMBER_ID")
    private Long id;

    @Embedded
    private EmailAccount emailAccount;

    private String password;

    @Embedded
    private Address address;

    public Member(EmailAccount emailAccount, String password, Address address) {
        this.emailAccount =emailAccount;
        this.password = password;
        this.address = address;
    }

    public MemberResponse toResponseModel() {
        return new MemberResponse(this.getEmailAccount(),this.getAddress());
    }

}
