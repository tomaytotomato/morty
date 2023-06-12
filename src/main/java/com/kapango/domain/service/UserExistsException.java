package com.kapango.domain.service;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String s) {
        super(s);
    }
}
