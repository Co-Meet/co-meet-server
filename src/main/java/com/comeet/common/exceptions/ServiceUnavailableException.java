package com.comeet.common.exceptions;

import com.comeet.common.ResultCode;

public class ServiceUnavailableException extends CoMeetServerException {

    protected ServiceUnavailableException(ResultCode resultCode) {
        super(resultCode);
    }
}
