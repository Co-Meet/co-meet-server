package com.comeet.member.exception;


import com.comeet.common.ResultCode;
import com.comeet.common.exceptions.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super(ResultCode.MEMBER_NOT_FOUND);
    }
}
