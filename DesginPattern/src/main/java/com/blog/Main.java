package com.blog;

import com.blog.adapter.Adapter;
import com.blog.adapter.HDMIConverter;
import com.blog.adapter.RGB;
import com.blog.adapter.HDMIPort;
import com.blog.builder.Director;
import com.blog.builder.HTMLBuilder;
import com.blog.builder.TextBuilder;
import com.blog.factory_method.AbstractFactory;
import com.blog.factory_method.ConcreteFactory;
import com.blog.factory_method.ProductInterface;
import com.blog.facade.ComputerBody;
import com.blog.prototype.annoymous.MessageBox;
import com.blog.prototype.annoymous.UnderlinePen;
import com.blog.prototype.framework.Manager;
import com.blog.prototype.framework.Product;
import com.blog.singleton.Singleton;
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

        // 템플릿 메소드 패턴(Template Method Pattern)
        Payment cash = new Cash();
        Payment point = new Cash();
        cash.pay();
        point.pay();

        // 팩토리 메소드 패턴(Factory Method Pattern)
        AbstractFactory[] factories = { new ConcreteFactory()};

        ProductInterface productA = factories[0].makeSomeOperation();

        // 프로토 타입 패턴(Prototype Pattern)
        Manager manager = new Manager();
        UnderlinePen upen = new UnderlinePen('~');
        MessageBox mbox = new MessageBox('*');
        MessageBox sbox = new MessageBox('/');
        manager.register("strong message", upen);
        manager.register("warning box", mbox);
        manager.register("slash box", sbox);


        Product p1 = manager.create("strong message");
        p1.use("Hello, world.");
        Product p2 = manager.create("warning box");
        p2.use("Hello, world");
        Product p3 = manager.create("slash box");
        p3.use("slash box");

        // 싱글톤 패턴(Singleton pattern)
        System.out.println("start");
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        // 빌더 패턴(builder pattern)
        if (args[0].equals("plain")) {
            TextBuilder textBuilder = new TextBuilder();
            Director director = new Director(textBuilder);
            director.constructor();
            String result = textBuilder.getResult();
            System.out.println(result);
        } else if (args[0].equals("html")) {
            HTMLBuilder htmlBuilder = new HTMLBuilder();
            Director director = new Director(htmlBuilder);
            director.constructor();
            String filename = htmlBuilder.getResult();
            System.out.println(filename + "가 작성되었습니다.");
        }

        // 퍼사드 패턴
        ComputerBody computer = new ComputerBody();
        computer.startButton();

        System.out.println("instance1과 instance2 동일성 비교::"+ (instance1==instance2));


    }
}