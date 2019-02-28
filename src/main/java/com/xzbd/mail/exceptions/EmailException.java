package com.xzbd.mail.exceptions;

public class EmailException extends RuntimeException{

    private String msg ;

    public EmailException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
