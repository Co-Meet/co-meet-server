package com.comeet.member.service;

import com.comeet.config.jwt.JwtService;
import com.comeet.config.security.SecurityUtil;
import com.comeet.github.GithubFeignService;
import com.comeet.github.model.response.GithubUserResponseDto;
import com.comeet.member.entity.Member;
import com.comeet.member.exception.GithubUserNotFoundException;
import com.comeet.member.exception.MemberNotFoundException;
import com.comeet.member.exception.NicknameAlreadyExistsException;
import com.comeet.member.model.request.JoinRequestDto;
import com.comeet.member.model.request.LoginRequestDto;
import com.comeet.member.model.response.GetOrganizationOfMemberDto;
import com.comeet.member.model.response.JoinResponseDto;
import com.comeet.member.model.response.LoginResponseDto;
import com.comeet.member.model.response.MemberInfoResponseDto;
import com.comeet.member.repository.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final GithubFeignService githubFeignService;

    public String checkNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistsException();
        } else {
            return "사용가능한 닉네임입니다.";
        }
    }

    public String checkGithubId(String githubId) {
        githubFeignService.getGithubUser(githubId);
        return githubId + "를(을) 사용하는 유저가 존재합니다.";
    }

    @Transactional
    public JoinResponseDto join(JoinRequestDto joinRequestDto) {
        Member member = Member.of(joinRequestDto.getNickname(), joinRequestDto.getGithubId());
        memberRepository.save(member);

        return new JoinResponseDto(jwtService.encode(member.getId()));
    }

    public LoginResponseDto login(LoginRequestDto logInRequestDto) {
        Member member = memberRepository.findByNickname(logInRequestDto.getNickname())
            .orElseThrow(MemberNotFoundException::new);
        return new LoginResponseDto(jwtService.encode(member.getId()));
    }

    public MemberInfoResponseDto getMemberInfo() {
        Member member = memberRepository.findById(SecurityUtil.resolveMemberId())
            .orElseThrow(MemberNotFoundException::new);
        return new MemberInfoResponseDto(member.getId(), member.getNickname(),
            member.getGithubId());
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    /**
     * TODO 멤버 정보 수정
     */

    public GetOrganizationOfMemberDto getOrganizationOfMember() {
        Member member = memberRepository.findById(SecurityUtil.resolveMemberId())
            .orElseThrow(MemberNotFoundException::new);
        return new GetOrganizationOfMemberDto(member.getOrganizations());
    }

    // 기타 validation 로직은 private 함수로 빼기
    private Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
    }
}
