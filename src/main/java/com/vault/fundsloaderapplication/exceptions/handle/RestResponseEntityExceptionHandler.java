package com.vault.fundsloaderapplication.exceptions.handle;

import com.vault.fundsloaderapplication.exceptions.LoadRequestException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {LoadRequestException.class})
    protected ResponseEntity<Object> handle(@NonNull final LoadRequestException exception) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
