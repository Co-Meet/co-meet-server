package com.comeet.member.model.response;

import com.comeet.organization.Organization;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetOrganizationOfMemberDto {

    List<Organization> organizationList;
}
