package com.comeet.member.exception;


import com.comeet.common.ResultCode;
import com.comeet.common.exceptions.BadRequestException;

public class MemberNotExistsInOrganization extends BadRequestException {

    public MemberNotExistsInOrganization() {
        super(ResultCode.MEMBER_NOT_EXISTS_IN_ORGANIZATION);
    }
}
