package com.blog.spring_event_with_slack.spring_event_with_slack.common.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column
    private String zipCode;

    @Column
    private String address1;

    @Column
    private String address2;

    protected Address() {
    }

    public Address(String zipCode, String address1, String address2) {
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
    }
}
