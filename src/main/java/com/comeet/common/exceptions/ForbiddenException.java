package com.comeet.common.exceptions;

import com.comeet.common.ResultCode;

public class ForbiddenException extends CoMeetServerException {

    public ForbiddenException() {
        super(ResultCode.FORBIDDEN);
    }

    public ForbiddenException(ResultCode resultCode) {
        super(resultCode);
    }
}
