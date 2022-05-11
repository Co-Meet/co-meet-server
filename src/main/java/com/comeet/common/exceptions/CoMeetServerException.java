package com.comeet.common.exceptions;


import com.comeet.common.ResultCode;

public abstract class CoMeetServerException extends RuntimeException {

    private final ResultCode resultCode;

    protected CoMeetServerException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    protected CoMeetServerException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    protected CoMeetServerException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    protected CoMeetServerException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
