package com.blog.springrestdocsexample.Member.applicaation;

import com.blog.springrestdocsexample.Member.domain.Member;
import com.blog.springrestdocsexample.Member.infrastructure.MemberRepository;
import com.blog.springrestdocsexample.Member.presentation.model.MemberRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long addMember(MemberRequest memberRequest) {

        Member member = memberRequest.toEntity();

        memberRepository.save(member);

        return member.getId();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}
