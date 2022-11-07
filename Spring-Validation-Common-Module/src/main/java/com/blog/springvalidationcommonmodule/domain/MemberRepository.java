package com.blog.springvalidationcommonmodule.domain;

import com.blog.springvalidationcommonmodule.presentation.dto.MemberResponseDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);

    Member save(Member member);

    List<MemberResponseDto> findAll();


}

