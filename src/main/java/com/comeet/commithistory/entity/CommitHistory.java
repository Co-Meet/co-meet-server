package com.comeet.commithistory.entity;

import com.comeet.common.entities.BaseEntity;
import com.comeet.member.entity.Member;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommitHistory extends BaseEntity {

    private Long count;

    /* 연관관계 */

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
