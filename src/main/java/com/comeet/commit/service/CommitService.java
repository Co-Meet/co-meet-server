package com.comeet.commit.service;

import com.comeet.commit.model.response.GetCommitResponseDto;
import com.comeet.common.exceptions.InternalServerErrorException;
import com.comeet.github.GithubFeignService;
import com.comeet.github.model.response.GithubCommitsResponseDto;
import com.comeet.member.entity.Member;
import com.comeet.member.service.MemberService;
import java.io.IOException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommitService {

    private final MemberService memberService;

    public GetCommitResponseDto getCommit(Long memberId) {
        return new GetCommitResponseDto(this.parsingGithubContribution(memberId));
    }

    private Integer parsingGithubContribution(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        String githubId = member.getGithubId();

        String githubUrl = "https://github.com/" + githubId;
        String attributes = "data-date";
        String value = LocalDate.now().toString();
        String attributeKey = "data-count";
        try {
            Document document = Jsoup.connect(githubUrl).get();
            String commits = document.getElementsByAttributeValue(attributes, value)
                .get(0).attr(attributeKey);
            return Integer.parseInt(commits);
        } catch (IOException e) {
            throw new InternalServerErrorException();
        }
    }
}
