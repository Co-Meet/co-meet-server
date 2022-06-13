package com.comeet.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.comeet.config.ApiDocumentUtil;
import com.comeet.config.jwt.JwtService;
import com.comeet.member.controller.MemberController;
import com.comeet.member.model.request.JoinRequestDto;
import com.comeet.member.model.response.JoinResponseDto;
import com.comeet.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        JoinResponseDto joinResponseDto = new JoinResponseDto("asdfg1234");

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
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("githubId").type(JsonFieldType.STRING).description("깃허브 아이디")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                    fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                        .description("엑세스토큰")
                )
            ));
    }
}
