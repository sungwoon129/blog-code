package com.blog.items.item36;

import java.util.Set;

public class GoodText {
    public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }

    public void applyStyles(Set<Style> styles) {
        styles.forEach(this::apply);
    }

    private void apply(Style style) {
        System.out.printf("Applying %s style",style);
    }
}
