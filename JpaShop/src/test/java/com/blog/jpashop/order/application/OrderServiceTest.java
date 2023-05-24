package com.blog.jpashop.order.application;

import com.blog.jpashop.common.model.Address;
import com.blog.jpashop.item.domain.Item;
import com.blog.jpashop.item.domain.NotEnoughStockException;
import com.blog.jpashop.member.domain.Member;
import com.blog.jpashop.order.domain.Order;
import com.blog.jpashop.order.domain.OrderStatus;
import com.blog.jpashop.order.infrastructure.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void 상품주문_성공() {

        //given
        Member member = createMember();
        Item item = createBook("JPA 도서",1000, 10);
        int orderCount = 2;


        //when
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertEquals( OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderLines().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(1000*2,getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        assertEquals(8,item.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }



    @Test
    void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        Item item = createBook("JPA 책",10000, 10);
        int orderCount = 11;


        //then
        NotEnoughStockException exception = assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
        assertEquals("재고가 부족합니다.",exception.getMessage());

    }

    @Test
    void 주문취소() {
        //given
        Member member = createMember();
        Item item = createBook("JPA 도서",1000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);


        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();
        assertEquals(getOrder.getStatus(),OrderStatus.CANCEL,"주문 취소시 주문의 상태는 CANCEL이다.");
        assertEquals(item.getStockQuantity(),10,"주문이 취소된 상품은 주문 수량만큼 재고가 증가해야 한다.");

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        return member;
    }

    private Item createBook(String itemName, int price, int stockQuantity) {
        Item item = new Item();
        item.setName(itemName);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }


}