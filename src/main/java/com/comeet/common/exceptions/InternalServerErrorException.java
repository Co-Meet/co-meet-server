package com.comeet.common.exceptions;

import com.comeet.common.ResultCode;

public class InternalServerErrorException extends CoMeetServerException {

    protected InternalServerErrorException() {
        super(ResultCode.INTERNAL_SERVER_ERROR);
    }

    protected InternalServerErrorException(ResultCode resultCode) {
        super(resultCode);
    }
}
