package com.vault.fundsloaderapplication.exceptions.handle;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private final int status;
    private final String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}
