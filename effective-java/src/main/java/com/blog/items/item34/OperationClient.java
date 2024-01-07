package com.blog.items.item34;

public class OperationClient {
    public void test(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        for (Operation op : Operation.values())
            System.out.printf("%f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
    }

    public void test2(Operation op) {
        Operation inverseOp = inverse(op);
        System.out.printf("%s 의 반대 연산은 %s 입니다.",op,inverseOp);

    }

    public static Operation inverse(Operation op) {
        switch (op) {
            case PLUS: return Operation.MINUS;
            case MINUS: return Operation.PLUS;
            case TIMES: return Operation.DIVIDE;
            case DIVIDE: return Operation.TIMES;
            default: throw new AssertionError("알 수 없는 연산: " + op);
        }
    }
}
