package com.blog.springvalidationcommonmodule.application;

import com.blog.springvalidationcommonmodule.domain.MemberRepository;
import com.blog.springvalidationcommonmodule.exception.ValidCustomException;
import com.blog.springvalidationcommonmodule.infrastructure.MemberRepositoryImpl;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberRequestDto;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public Long save(MemberRequestDto memberRequestDto) {
        if(memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent()) {
            throw new ValidCustomException("이미 사용중인 이메일 주소입니다.","email");
        }

        return memberRepository.save(memberRequestDto.toEntity()).getId();
    }

    @Transactional
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll();
    }


}
