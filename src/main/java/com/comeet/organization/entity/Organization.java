package com.comeet.organization.entity;

import com.comeet.common.entities.BaseEntity;
import com.comeet.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Organization extends BaseEntity {

    private String name;

    /* 연관관계 */
    @ManyToMany(mappedBy = "organizations")
    private List<Member> members = new ArrayList<>();

    public static Organization of(String name, List<Member> members) {

        Organization organization = new Organization();
        organization.name = name;
        organization.members = members;
        return organization;
    }
}
