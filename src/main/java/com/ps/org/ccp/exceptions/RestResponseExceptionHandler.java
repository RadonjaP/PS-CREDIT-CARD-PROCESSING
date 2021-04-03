package com.ps.org.ccp.exceptions;

import com.ps.org.ccp.controller.model.ExceptionHandlerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CreditCardValidationException.class})
    protected ResponseEntity<ExceptionHandlerResponse> handleValidationError(CreditCardValidationException exception, WebRequest request) {
        log.error("Validation errors are present.");
        ExceptionHandlerResponse error = ExceptionHandlerResponse.builder()
                .message("Validation errors are present.")
                .errors(exception.getErrors())
                .build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = {CreditCardDuplicateException.class})
    protected ResponseEntity<ExceptionHandlerResponse> handleDuplicateCardException(CreditCardDuplicateException exception, WebRequest request) {
        log.error("Duplicate of credit card.", exception);
        ExceptionHandlerResponse error = ExceptionHandlerResponse.builder()
                .message("Duplicate of credit card.")
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<ExceptionHandlerResponse> handleUnexpectedRuntimeException(RuntimeException exception, WebRequest request) {
        log.error("Unexpected exception occurred.", exception);
        ExceptionHandlerResponse error = ExceptionHandlerResponse.builder()
                .message("Unexpected exception occurred.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
