package com.blog.carta.exception;

import org.springframework.http.HttpStatus;

public class CommentPostException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public CommentPostException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommentPostException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
