package io.github.marbys.util.http;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class HttpErrorInfo {

    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus status;
    private final String message;

    public HttpErrorInfo() {
        this.timestamp = null;
        this.path = null;
        this.status = null;
        this.message = null;
    }

    public HttpErrorInfo(String path, HttpStatus status, String message) {
        this.timestamp = ZonedDateTime.now();
        this.path = path;
        this.status = status;
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() { return status.getReasonPhrase();}

    public String getMessage() {
        return message;
    }
}
