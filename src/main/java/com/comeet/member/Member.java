package com.comeet.member;

import com.comeet.common.entities.BaseEntity;
import com.comeet.organization.Organization;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    private String nickname;

    private String githubId;

    /* 연관관계 */
    @ManyToMany()
    @JoinTable(name = "member_organization")
    private List<Organization> organizationList = new ArrayList<>();
}
