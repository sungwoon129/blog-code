package com.blog.items.item60;

import java.math.BigDecimal;

public class GoodCase {

    /**
     * 센트를 달러단위로 표현하면 소숫점이 있어 BigDecimal 타입을 이용한 금융 계산
     */
    public void solution1() {
        final BigDecimal TEN_CENTS = new BigDecimal(".10");

        int itemsBought = 0;
        BigDecimal funds = new BigDecimal("1.00");
        for(BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
            funds = funds.subtract(price);
            itemsBought++;
            System.out.println(itemsBought + "개 구입");
            System.out.println("잔돈(달러): " + funds);
        }
    }

    /**
     * BigDecimal 타입은 속도가 느리고, 사용하기 불편하다는 단점이 있어 int와 long 타입을 이용한 금융 계산
     * 하지만, int와 long은 값의 크기가 제한되고 소수점을 직접 관리해야함. 여기선 달러를 센트로 변환해 계산
     */
    public void solution2() {
      int itemsBought = 0;
      int funds = 100;
      for(int price = 10; funds >= price; price += 10) {
          funds -= price;
          itemsBought++;
          System.out.println(itemsBought + "개 구입");
          System.out.println("잔돈(달러): " + funds);
      }
    }
}
