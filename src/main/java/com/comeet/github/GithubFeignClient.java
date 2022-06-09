package com.comeet.github;

import com.comeet.config.feign.GithubFeignClientConfig;
import com.comeet.github.model.response.GithubCommitsResponseDto;
import com.comeet.github.model.response.GithubUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "githubFeignClient", url = "https://api.github.com", configuration = GithubFeignClientConfig.class)
public interface GithubFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{githubId}")
    GithubUserResponseDto getGithubUser(@PathVariable("githubId") String githubId);

    @RequestMapping(method = RequestMethod.GET, value = "/search/commits?q=author:{author} committer-date:{committerDate}")
    GithubCommitsResponseDto getGithubCommits(@PathVariable("author") String author,
        @PathVariable("committerDate") String committerDate);
}
