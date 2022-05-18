package com.comeet.member.exception;


import com.comeet.common.ResultCode;
import com.comeet.common.exceptions.BadRequestException;

public class MemberAlreadyAddedOrganization extends BadRequestException {

    public MemberAlreadyAddedOrganization() {
        super(ResultCode.MEMBER_ALREADY_ADDED_ORGANIZATION);
    }
}
