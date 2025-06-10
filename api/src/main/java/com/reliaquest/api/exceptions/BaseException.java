package com.reliaquest.api.exceptions;

import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -1635148452692559035L;
    public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final String DEFAULT_MESSAGE = "Something went wrong, Please try again!";

    private final HttpStatus httpStatus;
    private final String message;

    public BaseException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = null == httpStatus ? DEFAULT_HTTP_STATUS : httpStatus;
        this.message = StringUtils.isBlank(message) ? DEFAULT_MESSAGE : message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
