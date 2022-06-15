package com.comeet.config.security;

import com.comeet.config.jwt.JwtService;
import com.comeet.config.security.exception.TokenMissingException;
import com.comeet.member.entity.Member;
import com.comeet.member.service.MemberService;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PreAuthTokenProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final JwtService jwtService;

    @Autowired
    Environment environment;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            String token = authentication.getPrincipal().toString();
            if (environment.acceptsProfiles(Profiles.of("local")) && token.equals("TEST-TOKEN")) {
                return new PreAuthenticatedAuthenticationToken(
                    1L,
                    "",
                    Collections.singletonList(
                        new SimpleGrantedAuthority(SecurityConfig.MEMBER_ROLE_NAME))
                );
            }
            Long memberId = jwtService.decode(token);
            Member member = memberService.findMemberById(memberId);
            return new PreAuthenticatedAuthenticationToken(
                member.getId(),
                "",
                Collections.singletonList(
                    new SimpleGrantedAuthority(SecurityConfig.MEMBER_ROLE_NAME))
            );
        }
        throw new TokenMissingException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
