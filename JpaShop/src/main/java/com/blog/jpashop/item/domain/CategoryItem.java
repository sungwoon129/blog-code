package com.blog.jpashop.item.domain;



import com.blog.jpashop.common.model.BaseEntity;

import jakarta.persistence.*;

@Entity
@IdClass(CategoryItemId.class)
public class CategoryItem extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Id
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
