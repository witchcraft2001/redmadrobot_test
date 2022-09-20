package com.dmdev.bootcamptest.exceptions;

public class BulletinNotFoundException extends RuntimeException {
    public BulletinNotFoundException(String message) {
        super(message);
    }
}
