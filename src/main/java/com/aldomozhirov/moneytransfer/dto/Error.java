package com.aldomozhirov.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

    @JsonProperty(required = true)
    private int status;

    @JsonProperty(required = true)
    private String message;

    public Error() {
    }

    public Error(int httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
