package com.blog.items.item36;

import java.util.EnumSet;

import static com.blog.items.item36.BadText.STYLE_BOLD;
import static com.blog.items.item36.BadText.STYLE_ITALIC;

public class Client {
    public void test() {

        BadText badTest = new BadText();
        badTest.applyStyles(STYLE_BOLD + STYLE_ITALIC);

        GoodText text = new GoodText();
        text.applyStyles(EnumSet.of(GoodText.Style.BOLD, GoodText.Style.UNDERLINE));
    }
}
