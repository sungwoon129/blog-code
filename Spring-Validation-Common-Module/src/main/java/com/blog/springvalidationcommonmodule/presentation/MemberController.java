package com.blog.springvalidationcommonmodule.presentation;

import com.blog.springvalidationcommonmodule.application.MemberService;
import com.blog.springvalidationcommonmodule.exception.ValidCustomException;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberRequestDto;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public Long saveMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {

        return memberService.save(memberRequestDto);
    }

    @GetMapping("/members")
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }

}
