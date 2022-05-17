package com.comeet.member.entity;

import com.comeet.common.entities.BaseEntity;
import com.comeet.organization.entity.Organization;
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
    private List<Organization> organizations = new ArrayList<>();

    public static Member of(String nickname, String githubId) {

        Member member = new Member();
        member.nickname = nickname;
        member.githubId = githubId;
        return member;
    }

    public void addOrganization(Organization organization) {
        if (this.organizations.contains(organization)) {
            /**
             * TODO 이미 있는 그룹이라고 에러 핸들링 할지 고민
             */
            return;
        }
        this.organizations.add(organization);
        organization.getMembers().add(this);
    }
}
