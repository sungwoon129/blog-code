package com.blog.items.item36;

import java.util.EnumSet;

public class Client {
    public void test() {

        BadText badTest = new BadText();
        badTest.applyStyles(3);

        GoodText text = new GoodText();
        text.applyStyles(EnumSet.of(GoodText.Style.BOLD, GoodText.Style.UNDERLINE));
    }
}
