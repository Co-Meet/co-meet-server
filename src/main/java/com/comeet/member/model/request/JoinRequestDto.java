package com.comeet.member.model.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinRequestDto {

    @NotNull
    private String nickname;

    @NotNull
    private String githubId;
}
