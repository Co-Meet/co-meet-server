package com.comeet.organization.model.response;

import com.comeet.member.entity.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationResponseDto {

    private Long id;

    private String name;

    private List<Member> members;
}
