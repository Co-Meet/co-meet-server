package com.comeet.member.controller;

import com.comeet.common.ApiResponse;
import com.comeet.member.model.request.JoinRequestDto;
import com.comeet.member.model.request.LoginRequestDto;
import com.comeet.member.model.request.UpdateGithubIdRequestDto;
import com.comeet.member.model.response.GetMyOrganizationResponseDto;
import com.comeet.member.model.response.JoinResponseDto;
import com.comeet.member.model.response.LoginResponseDto;
import com.comeet.member.model.response.MemberResponseDto;
import com.comeet.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-nickname/{nickname}")
    public ApiResponse<String> checkNickname(@PathVariable String nickname) {
        return ApiResponse.success(memberService.checkNickname(nickname));
    }

    @GetMapping("/check-githubId/{githubId}")
    public ApiResponse<String> checkGithubId(@PathVariable String githubId) {
        return ApiResponse.success(memberService.checkGithubId(githubId));
    }

    @PostMapping("/join")
    public ApiResponse<JoinResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        return ApiResponse.success(memberService.join(joinRequestDto));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.success(memberService.login(loginRequestDto));
    }

    @GetMapping("/organizations")
    public ApiResponse<GetMyOrganizationResponseDto> getOrganizationOfMember() {
        return ApiResponse.success(memberService.getMyOrganization());
    }

    @PatchMapping("/githubId")
    public ApiResponse<MemberResponseDto> updateGithubId(
        @RequestBody UpdateGithubIdRequestDto updateGithubIdRequestDto) {
        return ApiResponse.success(memberService.updateGithubId(updateGithubIdRequestDto));
    }

    @GetMapping("/me")
    public ApiResponse<MemberResponseDto> getMyInfo() {
        return ApiResponse.success(memberService.getMyInfo());
    }
}
