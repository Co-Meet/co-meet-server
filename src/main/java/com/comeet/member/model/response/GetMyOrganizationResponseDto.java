package com.comeet.member.model.response;

import com.comeet.organization.entity.Organization;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMyOrganizationResponseDto {

    List<Organization> organizationList;
}
