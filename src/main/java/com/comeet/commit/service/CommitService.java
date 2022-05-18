package com.comeet.commit.service;

import com.comeet.commit.entity.Commit;
import com.comeet.commit.repository.CommitRepository;
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

    @Transactional
    public Long getMemberCommits(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        String githubId = member.getGithubId();

        String githubUrl = "https://api.github.com/search/commits?q=author:{author} committer-date:{committer-date}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("author", githubId);
        params.put("committer-date", LocalDate.now(ZoneId.of("Asia/Seoul")));
        try {
            String result = restTemplate.getForObject(githubUrl, String.class, params);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result);
            JSONObject jsonObj = (JSONObject) obj;

            Long commitCount = (Long) jsonObj.get("total_count");

            if (!commitRepository.existsByMember(member)) {
                Commit commit = Commit.of(commitCount, member);
                commitRepository.save(commit);
            }
            Commit commit = commitRepository.findByMember(member);
            commit.updateCount(commitCount);

            return commitCount;
        } catch (ParseException e) {
            throw new GithubUserNotFoundException();
        }
    }
}
