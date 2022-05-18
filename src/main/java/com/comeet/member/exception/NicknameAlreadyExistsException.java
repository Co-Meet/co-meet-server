package com.comeet.member.exception;

import com.comeet.common.ResultCode;
import com.comeet.common.exceptions.NotFoundException;

public class NicknameAlreadyExistsException extends NotFoundException {

    public NicknameAlreadyExistsException() {
        super(ResultCode.MEMBER_NICKNAME_ALREADY_EXIST);
    }
}
