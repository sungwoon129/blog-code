package com.blog.items.item36;


public class BadText {

    /**
     * 비트 필드
     */
    public static final int STYLE_BOLD = 1 << 0; // 1
    public static final int STYLE_ITALIC = 1 << 1; // 2
    public static final int STYLE_UNDERLINE = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8

    public void applyStyles(int styles) {
        if ((styles & STYLE_BOLD) != 0) {
            applyBoldStyle();
        }
        if ((styles & STYLE_ITALIC) != 0) {
            applyItalicStyle();
        }
        if ((styles & STYLE_UNDERLINE) != 0) {
            applyUnderlineStyle();
        }
        if ((styles & STYLE_STRIKETHROUGH) != 0) {
            applyStrikeThroughStyle();
        }
    }

    private void applyStrikeThroughStyle() {
        System.out.println("Applying STRIKETHROUGH style");
    }

    private void applyUnderlineStyle() {
        System.out.println("Applying UNDERLINE style");
    }

    private void applyItalicStyle() {
        System.out.println("Applying ITALIC style");
    }

    private void applyBoldStyle() {
        System.out.println("Applying BOLD style");
    }
}
