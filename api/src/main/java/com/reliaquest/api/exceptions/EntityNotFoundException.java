package com.reliaquest.api.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1635148452692559035L;
    public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.NOT_FOUND;

    private final HttpStatus httpStatus;
    private final String message;

    public EntityNotFoundException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = null == httpStatus ? DEFAULT_HTTP_STATUS : httpStatus;
        ;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
