package com.comeet.commit.controller;

import com.comeet.commit.service.CommitService;
import com.comeet.common.ApiResponse;
import com.comeet.organization.model.response.OrganizationInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitSearchBuilder;
import org.kohsuke.github.PagedIterator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/commits")
@RequiredArgsConstructor
public class CommitController {

    private final CommitService commitService;

    @GetMapping("/{memberId}")
    public Long getMemberCommits(
        @PathVariable Long memberId) {
        return commitService.getMemberCommits(memberId);
    }
}
