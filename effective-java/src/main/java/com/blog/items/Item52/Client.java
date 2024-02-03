package com.blog.items.Item52;

import java.math.BigInteger;
import java.util.*;

import static com.blog.items.Item52.CollectionClassifier.classify;

public class Client {


    public void overLoading() {
        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String,String>().values()
        };

        // "집합" 만 3번 출력. 다중정의한 메서드는 컴파일 타임에 결정
        for(Collection<?> c : collections) {
            System.out.println(classify(c));
        }
    }

    public void overRiding() {
        List<Wine> wineList = List.of(
                new Wine(), new SparklingWine(), new Champagne()
        );

        // 포도주,발포성 포도주, 샴페인 출력. 재정의한 메서드는 런타임에 결정
        for(Wine wine : wineList) {
            System.out.println(wine.name());
        }
    }

    public void setList() {
        SetList setList = new SetList();
        setList.removeElement();
    }



}

