package com.blog.springrestdocsexample.Member.domain;

import com.blog.springrestdocsexample.Member.common.model.Address;
import com.blog.springrestdocsexample.Member.presentation.model.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MBMBER_ID")
    private Long id;


    private String email;

    private String password;

    @Embedded
    private Address address;



    public Member(String email, String password, Address address) {
        this.email =email;
        this.password = password;
        this.address = address;
    }

    public MemberResponse toResponseModel() {
        return new MemberResponse(this.getEmail(),this.getAddress());
    }

}
