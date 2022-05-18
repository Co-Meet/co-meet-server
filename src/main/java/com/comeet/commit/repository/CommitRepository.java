package com.comeet.commit.repository;

import com.comeet.commit.entity.Commit;
import com.comeet.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, Long> {

    Boolean existsByMember(Member member);

    Commit findByMember(Member member);
}
