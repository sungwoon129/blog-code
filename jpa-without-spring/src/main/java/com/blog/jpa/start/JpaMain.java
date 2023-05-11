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
            createMember(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
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



}
