package com.blog.adapter;

public class AdapterImpl implements Adapter {
    @Override
    public HDMICable adapt(DviCable dviCable) {
        dviCable.setMsg();
        return new HDMICable(dviCable.getMsg());
    }
}
