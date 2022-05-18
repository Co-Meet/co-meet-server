package com.comeet.member.controller;

import com.comeet.common.ApiResponse;
import com.comeet.member.model.request.JoinRequestDto;
import com.comeet.member.model.request.LoginRequestDto;
import com.comeet.member.model.response.GetOrganizationOfMemberDto;
import com.comeet.member.model.response.JoinResponseDto;
import com.comeet.member.model.response.LoginResponseDto;
import com.comeet.member.model.response.MemberInfoResponseDto;
import com.comeet.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/check-github/{githubId}")
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
    public ApiResponse<GetOrganizationOfMemberDto> getOrganizationOfMember(
        @ModelAttribute("memberId") Long memberId) {
        return ApiResponse.success(memberService.getOrganizationOfMember(memberId));
    }

    @GetMapping("/me")
    public ApiResponse<MemberInfoResponseDto> getMyInfo(
        @ModelAttribute("memberId") Long memberId) {
        return ApiResponse.success(memberService.getMemberInfo(memberId));
    }
}
