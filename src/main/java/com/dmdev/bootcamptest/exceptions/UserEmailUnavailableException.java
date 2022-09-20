package com.dmdev.bootcamptest.exceptions;

import javax.validation.constraints.NotNull;

public class UserEmailUnavailableException extends RuntimeException {
    public UserEmailUnavailableException(@NotNull String message) {
        super(message);
    }
}
