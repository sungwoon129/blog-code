package com.blog.jpashop.item.domain;

import com.blog.jpashop.item.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("M")
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
