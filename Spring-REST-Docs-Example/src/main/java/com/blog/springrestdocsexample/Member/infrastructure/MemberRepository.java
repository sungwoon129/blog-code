package com.blog.springrestdocsexample.Member.infrastructure;

import com.blog.springrestdocsexample.Member.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

    void save(Member member);

    Optional<Member> findById(Long id);

}
