package com.blog.decorator;

public class PrefixDecorator extends Decorator{
    public PrefixDecorator(Component detailComponent) {
        super(detailComponent);
    }

    @Override
    public String add() {
        return "prefix " + super.add();
    }
}
