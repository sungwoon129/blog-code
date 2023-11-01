package com.blog.adapter;

public class HDMIConverter implements Adapter {
    @Override
    public HDMI adapt(RGB rgb) {
        rgb.setImages(new byte[] {});
        return new HDMI(rgb.getImages());
    }
}
