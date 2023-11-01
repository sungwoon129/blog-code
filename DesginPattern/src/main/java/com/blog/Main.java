package com.blog;

import com.blog.adapter.Adapter;
import com.blog.adapter.HDMIConverter;
import com.blog.adapter.RGB;
import com.blog.adapter.HDMIPort;
import com.blog.strategy.EldenRingCharacter;
import com.blog.strategy.Spear;

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

    }
}