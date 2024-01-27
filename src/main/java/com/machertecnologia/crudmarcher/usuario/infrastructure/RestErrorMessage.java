package com.machertecnologia.crudmarcher.usuario.infrastructure;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {
    private HttpStatus status;
    private String message;

    public RestErrorMessage(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
