package com.blog.items.item36;

import java.util.EnumSet;

public class Client {
    public void test() {

        BadCase badTest = new BadCase();
        badTest.applyStyles(3);

        GoodCase text = new GoodCase();
        text.applyStyles(EnumSet.of(GoodCase.Style.BOLD, GoodCase.Style.UNDERLINE));
    }
}
