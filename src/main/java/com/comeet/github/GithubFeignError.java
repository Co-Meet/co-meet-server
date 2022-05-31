package com.comeet.github;

import com.comeet.member.exception.GithubUserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class GithubFeignError implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new GithubUserNotFoundException();
        }
        return null;
    }
}
