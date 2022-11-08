package com.blog.springvalidationcommonmodule.infrastructure;

import com.blog.springvalidationcommonmodule.domain.Member;
import com.blog.springvalidationcommonmodule.domain.MemberRepository;
import com.blog.springvalidationcommonmodule.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public List<MemberResponseDto> findAll() {
        return memberJpaRepository
                .findAll()
                .stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());

    }


}
