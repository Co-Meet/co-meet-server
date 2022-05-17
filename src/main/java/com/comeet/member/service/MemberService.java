package com.comeet.member.service;

import com.comeet.config.jwt.JwtService;
import com.comeet.member.entity.Member;
import com.comeet.member.exception.GithubUserNotFoundException;
import com.comeet.member.exception.MemberNotFoundException;
import com.comeet.member.model.request.JoinRequestDto;
import com.comeet.member.model.request.LoginRequestDto;
import com.comeet.member.model.response.GetOrganizationOfMemberDto;
import com.comeet.member.model.response.JoinResponseDto;
import com.comeet.member.model.response.LoginResponseDto;
import com.comeet.member.model.response.MemberInfoResponseDto;
import com.comeet.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public String checkNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new GithubUserNotFoundException();
        } else {
            return "사용가능한 닉네임입니다.";
        }
    }

    public String checkGithubId(String githubId) {
        /**
         * TODO 외부 API 호출은 추후 리팩토링
         */
        String githubUrl = "https://api.github.com/users";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("githubId", githubId);
        try {
            restTemplate.getForObject(githubUrl + "/{id}", Object.class,
                githubId);
            return "해당 깃허브 아이디를 사용하는 유저가 존재합니다.";
        } catch (HttpStatusCodeException e) {
            throw new GithubUserNotFoundException();
        }
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


    public MemberInfoResponseDto getMemberInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return new MemberInfoResponseDto(member.getId(), member.getNickname(),
            member.getGithubId());
    }

    /**
     * TODO 멤버 정보 수정
     */

    public GetOrganizationOfMemberDto getOrganizationOfMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return new GetOrganizationOfMemberDto(member.getOrganizations());
    }

    // 기타 validation 로직은 private 함수로 빼기
    private Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
    }
}
