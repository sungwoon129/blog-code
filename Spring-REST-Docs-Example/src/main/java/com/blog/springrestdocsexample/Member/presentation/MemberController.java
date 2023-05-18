package com.blog.springrestdocsexample.Member.presentation;

import com.blog.springrestdocsexample.Member.applicaation.MemberService;
import com.blog.springrestdocsexample.Member.domain.Member;
import com.blog.springrestdocsexample.Member.presentation.model.MemberRequest;
import com.blog.springrestdocsexample.Member.presentation.model.MemberResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public Long add(@Valid MemberRequest memberRequest) {

        return memberService.addMember(memberRequest);

    }

    @GetMapping("/member/{id}")
    public MemberResponse findById(@PathVariable Long id) {
        Optional<Member> findMember = memberService.findById(id);

        return findMember.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다.")).toResponseModel();
    }
}
