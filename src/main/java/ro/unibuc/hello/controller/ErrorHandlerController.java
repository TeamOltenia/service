package ro.unibuc.hello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.unibuc.hello.exception.CustomErrorHandler;
import ro.unibuc.hello.exception.ErrorDetails;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(CustomErrorHandler.class)
    public ResponseEntity handleException(CustomErrorHandler e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDetails.builder()
                                .errorKey(e.getErrorDispatcher().getErrorKey())
                                .detail(e.getErrorDispatcher().getErrorMessage())
                                .errorDate(ZonedDateTime.now())
                                .build());
    }
}
