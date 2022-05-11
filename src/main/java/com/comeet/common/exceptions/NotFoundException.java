package com.comeet.common.exceptions;

import com.comeet.common.ResultCode;

public class NotFoundException extends CoMeetServerException {

    public NotFoundException() {
        super(ResultCode.NOT_FOUND);
    }

    public NotFoundException(ResultCode resultCode) {
        super(resultCode);
    }

    public NotFoundException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

}
