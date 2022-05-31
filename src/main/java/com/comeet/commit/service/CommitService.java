package com.comeet.commit.service;

import com.comeet.commit.entity.Commit;
import com.comeet.commit.repository.CommitRepository;
import com.comeet.github.GithubFeignService;
import com.comeet.github.model.response.GithubCommitsResponseDto;
import com.comeet.member.entity.Member;
import com.comeet.member.exception.GithubUserNotFoundException;
import com.comeet.member.service.MemberService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommitService {

    private final CommitRepository commitRepository;
    private final MemberService memberService;
    private final GithubFeignService githubFeignService;

    @Transactional
    public Long getMemberCommits(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        String githubId = member.getGithubId();
        GithubCommitsResponseDto githubCommitsResponseDto = githubFeignService.getGithubCommits(
            githubId);

        Long commitCount = githubCommitsResponseDto.getTotal_count();
        if (!commitRepository.existsByMember(member)) {
            Commit commit = Commit.of(commitCount, member);
            commitRepository.save(commit);
        }
        Commit commit = commitRepository.findByMember(member);
        commit.updateCount(commitCount);
        return commitCount;
    }
}
