package com.dmdev.bootcamptest.exceptions;

import javax.validation.constraints.NotNull;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(@NotNull String message) {
        super(message);
    }
}
