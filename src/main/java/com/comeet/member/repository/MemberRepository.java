package com.comeet.member.repository;

import com.comeet.member.entity.Member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByGithubId(String githubId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.githubId = :githubId WHERE m.id = :id")
    int updateGithubId(String githubId, Long id);

    boolean existsByNickname(String nickname);
}
