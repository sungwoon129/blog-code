package com.blog.jpa.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            //logic(em);
            //createMember(em);
            //manyToOneTest(em);
            manyToManySave(em);
            manyToManyFind(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        String id = "id1";
        LocalDateTime createdTime = LocalDateTime.now();
        LocalDateTime lastModifiedTime = createdTime.plusMinutes(10);

        Member member = new Member("id1","woony",25,RoleType.ROLE_ADMIN,createdTime,lastModifiedTime,"test");

        //등록
        em.persist(member);

        //수정
        member.setAge(6);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        //삭제
        em.remove(member);

    }

    private static void createMember(EntityManager em) {
        LocalDateTime createdTime = LocalDateTime.now();
        LocalDateTime lastModifiedTime = createdTime.plusMinutes(10);

        Member member = new Member("id1","woony",27,RoleType.ROLE_ADMIN,createdTime,lastModifiedTime,"test");

        em.persist(member);

        Member findMember = em.find(Member.class, "id1");


        //생성한 member 인스턴스와 조회한 findMember 인스턴스는 같은가? == 연산
        System.out.println(member == findMember);
        //생성한 member 인스턴스와 조회한 findMember 인스턴스는 같은가? equals 연산
        System.out.println(member.equals(findMember));

        System.out.println(findMember.getCreatedDate());
        System.out.println(findMember.getLastModifiedDate());
        System.out.println(findMember.getRoleType());


        em.remove(findMember);


    }

    private static void manyToOneTest(EntityManager em) {
        Team team1 = new Team();
        team1.setName("team1");

        em.persist(team1);
        /* team1 객체를 영속성 컨텍스트에 persist해야만 정상적으로 DB에 트랜잭션이 반영된다.
        member 객체만 영속성 컨텍스트에 관리를 위임한 경우, findMember 객체의 팀이름을 조회하는 출력문까지는 정상적으로 실행은 되지만
        이후 DB에 트랜잭션을 커밋하는 과정에서 "Transaction not active" 오류가 발생한다
        */
        Member member = new Member();
        member.setId("member1");
        member.setUsername("팀원 회원");
        member.setTeam(team1);

        em.persist(member);

        Member findMember = em.find(Member.class,"member1");
        System.out.println(findMember.getTeam().getName());
    }

    private static void manyToManySave(EntityManager em) {
        Member member = new Member();
        member.setId("member1");
        member.setUsername("user1");

        em.persist(member);

        Product product = new Product();
        product.setId("product1");
        product.setName("하잎보이");

        em.persist(product);

        Order order = new Order();
        order.setMember(member);
        order.setProduct(product);
        order.setOrderAmount(2);

        em.persist(order);
    }

    private static void manyToManyFind(EntityManager em) {
        OrderId orderId = new OrderId();
        orderId.setMember("member1");
        orderId.setProduct("product1");

        Order findOrder = em.find(Order.class,orderId);

        Member member = findOrder.getMember();
        Product product = findOrder.getProduct();

        System.out.println(member.getUsername());
        System.out.println(product.getName());
        System.out.println(findOrder.getOrderAmount());
    }


}
