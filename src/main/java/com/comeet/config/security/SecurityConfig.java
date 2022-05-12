package com.comeet.config.security;

import com.comeet.common.ApiResponse;
import com.comeet.common.ResultCode;
import com.comeet.config.jwt.JwtService;
import com.comeet.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String MEMBER_ROLE_NAME = "MEMBER";

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .mvcMatchers(
                "/error",
                "/favicon.ico",
                "/swagger-ui/**",
                "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**",
                "/v1/api-docs",
                "/h2-console/**",
                "/api/v1/hello",
                "/api/v1/check-github/**",
                "/api/v1/check-nickname/**"
            );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/**")
            .authorizeRequests()
            .antMatchers("/api/v1/members/join").permitAll()
            .antMatchers("/api/v1/members/login").permitAll()
            .anyRequest().hasAuthority(MEMBER_ROLE_NAME);
        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable();
        http.logout().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
        http.requestCache().disable();
        http.addFilterAt(tokenPreAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        http.sessionManagement().disable();
        http.exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    ApiResponse.failure(ResultCode.UNAUTHORIZED)
                );
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    ApiResponse.failure(ResultCode.FORBIDDEN)
                );
            });
    }

    @Bean
    TokenPreAuthFilter tokenPreAuthFilter() {
        TokenPreAuthFilter tokenPreAuthFilter = new TokenPreAuthFilter();
        tokenPreAuthFilter.setAuthenticationManager(new ProviderManager(preAuthTokenProvider()));
        return tokenPreAuthFilter;
    }

    @Bean
    PreAuthTokenProvider preAuthTokenProvider() {
        return new PreAuthTokenProvider(
            memberRepository,
            jwtService
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}