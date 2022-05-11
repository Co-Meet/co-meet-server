package com.comeet.common.exceptions;

import com.comeet.common.ResultCode;

public class UnauthorizedException extends CoMeetServerException {

    public UnauthorizedException() {
        super(ResultCode.UNAUTHORIZED);
    }

    public UnauthorizedException(ResultCode resultCode) {
        super(resultCode);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(ResultCode.UNAUTHORIZED, message, cause);
    }
}
