package com.comeet.commit;

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

    private int count;

    /* 연관관계 */

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
