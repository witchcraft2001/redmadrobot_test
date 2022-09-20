package com.dmdev.bootcamptest.exceptions;

import javax.validation.constraints.NotNull;

public class UnknownUserException extends RuntimeException {
    public UnknownUserException(@NotNull String message) {
        super(message);
    }
}
