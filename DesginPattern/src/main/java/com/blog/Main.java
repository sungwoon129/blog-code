package com.blog;

import com.blog.adapter.Adapter;
import com.blog.adapter.HDMIConverter;
import com.blog.adapter.RGB;
import com.blog.adapter.HDMIPort;
import com.blog.strategy.EldenRingCharacter;
import com.blog.strategy.Spear;
import com.blog.template_method.Cash;
import com.blog.template_method.Payment;

public class Main {
    public static void main(String[] args) {

        // 전략 패턴(Strategy Pattern)
        EldenRingCharacter eldenRingCharacter = new EldenRingCharacter();

        eldenRingCharacter.equipWeapon(new Spear());
        eldenRingCharacter.attack();

        // 어댑터 패턴(Adapter Pattern)
        Adapter adapter = new HDMIConverter();
        HDMIPort hdmiPort = new HDMIPort();

        hdmiPort.getSign(adapter.adapt(new RGB()));

        // 템플릿 메소드 패턴(Template Method Pattern
        Payment cash = new Cash();
        Payment point = new Cash();
        cash.pay();
        point.pay();

    }
}