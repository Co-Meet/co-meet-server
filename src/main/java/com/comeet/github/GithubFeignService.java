package com.comeet.github;

import com.comeet.github.model.response.GithubCommitsResponseDto;
import com.comeet.github.model.response.GithubUserResponseDto;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
        String committerDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();
        return githubFeignClient.getGithubCommits(author, committerDate);
    }
}
