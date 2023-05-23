package com.blog.jpashop.common.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;


    public Address() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }


    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }
}
