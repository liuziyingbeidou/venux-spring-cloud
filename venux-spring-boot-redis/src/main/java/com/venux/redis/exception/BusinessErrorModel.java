package com.venux.redis.exception;

import org.springframework.http.HttpStatus;

public class BusinessErrorModel {
    private Long timestamp =System.currentTimeMillis();
    private int status = HttpStatus.BAD_REQUEST.value();
    private String error = "Business Exception";
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
