package com.blog.template_method;

public abstract class Payment {

    public void pay() {
        validateOrder();
        calculateAmount();
        payByMethod();
        createReceipt();
    }

    private void createReceipt() {
    }


    private void calculateAmount() {
        
    }

    abstract void payByMethod();

    private void validateOrder() {
        
    }

}
