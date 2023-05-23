package com.blog.jpashop.member.presentation;

import com.blog.jpashop.member.application.MemberService;
import com.blog.jpashop.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/members")
    public String list(Model model) {
        model.addAttribute("members",memberService.findMembers());
        return "/members/memberList";
    }

    @GetMapping("/members/new")
    public String createMemberPage() {

        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(Member member) {
        memberService.join(member);
        return "redirect:/members";
    }
}
