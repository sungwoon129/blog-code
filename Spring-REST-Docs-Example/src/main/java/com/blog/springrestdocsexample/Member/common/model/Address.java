package com.blog.springrestdocsexample.Member.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String address1;
    private String address2;
    private String zipcode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(address1, address.address1) && Objects.equals(address2, address.address2) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address1, address2, zipcode);
    }
}
