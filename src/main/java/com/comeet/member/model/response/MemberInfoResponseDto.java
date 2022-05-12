package com.comeet.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfoResponseDto {

    private Long id;

    private String nickname;

    private String githubId;
}
