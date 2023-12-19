package com.blog.decorator;

public class SuffixDecorator extends Decorator{
    public SuffixDecorator(Component detailComponent) {
        super(detailComponent);
    }

    @Override
    public String add() {
        return super.add() + " suffix";
    }
}
