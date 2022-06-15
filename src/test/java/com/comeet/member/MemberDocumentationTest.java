package com.comeet.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.comeet.config.ApiDocumentUtil;
import com.comeet.config.jwt.JwtService;
import com.comeet.member.controller.MemberController;
import com.comeet.member.entity.Member;
import com.comeet.member.model.request.JoinRequestDto;
import com.comeet.member.model.request.LoginRequestDto;
import com.comeet.member.model.response.GetMyOrganizationResponseDto;
import com.comeet.member.model.response.JoinResponseDto;
import com.comeet.member.model.response.LoginResponseDto;
import com.comeet.member.model.response.MemberResponseDto;
import com.comeet.member.service.MemberService;
import com.comeet.organization.entity.Organization;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@MockBean(JpaMetamodelMappingContext.class)
public class MemberDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void join() throws Exception {

        //given
        JoinRequestDto joinRequestDto = new JoinRequestDto("testNickname", "testGithubId");
        JoinResponseDto joinResponseDto = new JoinResponseDto("testAccessToken");

        when(memberService.join(any())).thenReturn(joinResponseDto);

        //when
        ResultActions result = this.mockMvc.perform(
            post("/api/v1/members/join")
                .content(objectMapper.writeValueAsString(joinRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
            .andDo(document("member-join", // (4)
                ApiDocumentUtil.getDocumentRequest(),
                ApiDocumentUtil.getDocumentResponse(),
                requestFields(
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
                    fieldWithPath("githubId").type(JsonFieldType.STRING).description("멤버 깃허브 아이디")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                    fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                        .description("엑세스토큰")
                )
            ));
    }

    @Test
    public void login() throws Exception {

        //given
        LoginRequestDto loginRequestDto = new LoginRequestDto("testNickname");
        LoginResponseDto loginResponseDto = new LoginResponseDto("testAccessToken");

        when(memberService.login(any())).thenReturn(loginResponseDto);

        //when
        ResultActions result = this.mockMvc.perform(
            post("/api/v1/members/login")
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
            .andDo(document("member-login", // (4)
                ApiDocumentUtil.getDocumentRequest(),
                ApiDocumentUtil.getDocumentResponse(),
                requestFields(
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("멤버 닉네임")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                    fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                        .description("엑세스토큰")
                )
            ));
    }

    @Test
    public void getMyInfo() throws Exception {

        //given
        MemberResponseDto memberResponseDto = new MemberResponseDto(1L, "testNickname",
            "testGithubId");
        when(memberService.getMyInfo()).thenReturn(memberResponseDto);

        //when
        ResultActions result = this.mockMvc.perform(
            get("/api/v1/members/me").header("Authorization",
                    "Bearer TEST-TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
            .andDo(document("member-get-my-info",
                ApiDocumentUtil.getDocumentRequest(),
                ApiDocumentUtil.getDocumentResponse(),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("멤버 아이디"),
                    fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                        .description("멤버 닉네임"),
                    fieldWithPath("data.githubId").type(JsonFieldType.STRING)
                        .description("멤버 깃허브 아이디")
                )
            ));
    }

    @Test
    public void getMyOrganization() throws Exception {

        //given
        Organization organization1 = Organization.of("testOranization");
        ReflectionTestUtils.setField(organization1, "id", 1L);
        ReflectionTestUtils.setField(organization1, "createdAt",
            LocalDateTime.parse("2022-02-02T00:00"));
        ReflectionTestUtils.setField(organization1, "updatedAt",
            LocalDateTime.parse("2022-02-02T00:00"));

        Member member1 = Member.of("testNickname", "testGithubId");
        ReflectionTestUtils.setField(member1, "id", 1L);
        ReflectionTestUtils.setField(member1, "createdAt", LocalDateTime.parse("2022-02-02T00:00"));
        ReflectionTestUtils.setField(member1, "updatedAt", LocalDateTime.parse("2022-02-02T00:00"));

        member1.addOrganization(organization1);

        List<Organization> organizationList = Arrays.asList(
            organization1);
        GetMyOrganizationResponseDto getMyOrganizationResponseDto = new GetMyOrganizationResponseDto(
            organizationList);
        when(memberService.getMyOrganization()).thenReturn(getMyOrganizationResponseDto);

        //when
        ResultActions result = this.mockMvc.perform(
            get("/api/v1/members/organizations").header("Authorization",
                    "Bearer TEST-TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
            .andDo(document("member-get-my-organization",
                ApiDocumentUtil.getDocumentRequest(),
                ApiDocumentUtil.getDocumentResponse(),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                    fieldWithPath("data.organizationList.[].id").type(JsonFieldType.NUMBER)
                        .description("오거니제이션 아이디"),
                    fieldWithPath("data.organizationList.[].name").type(JsonFieldType.STRING)
                        .description("오거니제이션 이름"),
                    fieldWithPath("data.organizationList.[].createdAt").type(JsonFieldType.STRING)
                        .description("오거니제이션 생성날짜"),
                    fieldWithPath("data.organizationList.[].updatedAt").type(JsonFieldType.STRING)
                        .description("오거니제이션 수정날짜"),
                    fieldWithPath("data.organizationList.[].members.[].id").type(
                            JsonFieldType.NUMBER)
                        .description("멤버 아이디"),
                    fieldWithPath("data.organizationList.[].members.[].nickname").type(
                            JsonFieldType.STRING)
                        .description("멤버 닉네임"),
                    fieldWithPath("data.organizationList.[].members.[].githubId").type(
                            JsonFieldType.STRING)
                        .description("멤버 깃허브 아이디"),
                    fieldWithPath("data.organizationList.[].members.[].createdAt").type(
                            JsonFieldType.STRING)
                        .description("멤버 생성날짜"),
                    fieldWithPath("data.organizationList.[].members.[].updatedAt").type(
                            JsonFieldType.STRING)
                        .description("멤버 수정날짜")
                )
            ));
    }
}
