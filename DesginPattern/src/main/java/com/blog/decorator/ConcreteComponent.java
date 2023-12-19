package com.blog.decorator;

public class ConcreteComponent implements Component{
    @Override
    public String add() {
        return "구체적 요소";
    }
}
