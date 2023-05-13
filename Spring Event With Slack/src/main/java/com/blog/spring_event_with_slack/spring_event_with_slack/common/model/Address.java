package com.blog.spring_event_with_slack.spring_event_with_slack.common.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    public Address() {
    }

    public Address(String zipCode, String address1, String address2) {
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
    }

    public String getFullAddress() {
        return address1 + address2;
    }

}
