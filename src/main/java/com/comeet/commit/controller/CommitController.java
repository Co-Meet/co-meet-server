package com.comeet.commit.controller;

import com.comeet.commit.model.response.GetCommitResponseDto;
import com.comeet.commit.service.CommitService;
import com.comeet.common.ApiResponse;
import com.comeet.github.GithubFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/commits")
@RequiredArgsConstructor
public class CommitController {

    private final CommitService commitService;

    @GetMapping("members/{memberId}")
    public ApiResponse<GetCommitResponseDto> getCommit(
        @PathVariable Long memberId) {
        return ApiResponse.success(commitService.getCommit(memberId));
    }
}
