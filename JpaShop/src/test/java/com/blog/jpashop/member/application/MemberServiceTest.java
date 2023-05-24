package com.blog.jpashop.member.application;

import com.blog.jpashop.member.domain.Member;
import com.blog.jpashop.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("woony");

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findById(savedId).orElseThrow());
    }

    @Test
    public void 중복회원_예외() {

        //given
        Member member1 = new Member();
        member1.setName("woony");

        Member member2 = new Member();
        member2.setName("woony");

        //when
        memberService.join(member1);

        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.",thrown.getMessage());

    }

}