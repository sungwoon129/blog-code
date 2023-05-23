package com.blog.jpashop.item.domain;

import java.io.Serializable;
import java.util.Objects;

public class CategoryItemId implements Serializable {

    private Long item;
    private Long category;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryItemId that = (CategoryItemId) o;
        return Objects.equals(item, that.item) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, category);
    }
}
