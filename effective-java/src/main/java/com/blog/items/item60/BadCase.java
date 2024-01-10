package com.blog.items.item60;

public class BadCase {

    /**
     * 1.03 달러에서 42센트를 사용한다고 가정한 경우
     */
    public void case1() {
        System.out.println(1.03 - 0.42); // 0.6100000001 출력
    }

    /**
     * 10센트짜리 사탕 9개를 구매한 경우
     */
    public void case2() {
        System.out.println(1.00 - 9 * 0.10); // 0.999999999998 출력
    }

    /**
     * 부동소수 타입을 사용해 반복문으로 소지금 1달러에서 10센트 가격의 사탕을 여러개 구매한 경우
     */
    public void case3() {
        double funds = 1.00;
        int itemsBought = 0;

        for (double price = 0.10; funds >= price; price += 0.10) {
            funds -= price;
            itemsBought++;
            // 3개를 구입한 후 잔돈이 0.3999999999달러가 남았다고 출력됨
            System.out.println(itemsBought + "개 구입");
            System.out.println("잔돈(달러): " + funds);
        }

    }
}
