package com.comeet.commit.entity;

import com.comeet.common.entities.BaseEntity;
import com.comeet.member.entity.Member;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Commit extends BaseEntity {

    private Long count;

    /* 연관관계 */

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public static Commit of(Long count, Member member) {
        Commit commit = new Commit();
        commit.count = count;
        commit.member = member;
        return commit;
    }

    public void updateCount(Long count) {
        this.count = count;
    }
}
