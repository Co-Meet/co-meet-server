package com.comeet.common.exceptions;

import com.comeet.common.ResultCode;

public class InternalServerErrorException extends CoMeetServerException {

    public InternalServerErrorException() {
        super(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(ResultCode resultCode) {
        super(resultCode);
    }
}
