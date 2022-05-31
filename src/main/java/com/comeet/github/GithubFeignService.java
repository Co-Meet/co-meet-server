package com.comeet.github;

import com.comeet.github.model.response.GithubCommitsResponseDto;
import com.comeet.github.model.response.GithubUserResponseDto;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GithubFeignService {

    private final GithubFeignClient githubFeignClient;

    public GithubUserResponseDto getGithubUser(String githubId) {
        return githubFeignClient.getGithubUser(githubId);
    }

    public GithubCommitsResponseDto getGithubCommits(String author) {
        LocalDate committerDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return githubFeignClient.getGithubCommits(author, committerDate);
    }
}
