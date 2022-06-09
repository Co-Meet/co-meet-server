package com.comeet.organization.exception;

import com.comeet.common.ResultCode;
import com.comeet.common.exceptions.NotFoundException;

public class OrganizationNotFoundException extends NotFoundException {

    public OrganizationNotFoundException() {
        super(ResultCode.ORGANIZATION_NOT_FOUND);
    }
}
