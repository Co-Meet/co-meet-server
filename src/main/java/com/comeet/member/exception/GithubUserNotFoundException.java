package com.comeet.member.exception;

import com.comeet.common.ResultCode;
import com.comeet.common.exceptions.NotFoundException;

public class GithubUserNotFoundException extends NotFoundException {

    public GithubUserNotFoundException() {
        super(ResultCode.Github_USER_NOT_FOUND);
    }
}