package com.blog.springvalidationcommonmodule.infrastructure;

import com.blog.springvalidationcommonmodule.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
