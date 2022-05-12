package com.comeet.member.repository;

import com.comeet.member.entity.Member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByGithubId(String githubId);

    boolean existsByNickname(String nickname);
}
