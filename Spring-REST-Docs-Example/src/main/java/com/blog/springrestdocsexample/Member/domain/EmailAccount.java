package com.blog.springrestdocsexample.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EmailAccount {

    @Email
    private String email;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAccount)) return false;
        EmailAccount that = (EmailAccount) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
