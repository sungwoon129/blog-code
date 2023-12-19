package com.blog.decorator;

public abstract class Decorator implements Component{
    private Component detailComponent;

    public Decorator(Component detailComponent) {
        this.detailComponent = detailComponent;
    }

    public String add() {
        return detailComponent.add();
    }
}
