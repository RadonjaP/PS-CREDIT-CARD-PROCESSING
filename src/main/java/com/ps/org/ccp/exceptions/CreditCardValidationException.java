package com.ps.org.ccp.exceptions;

import com.ps.org.ccp.service.validation.ValidationResult;

import java.util.List;

public class CreditCardValidationException extends RuntimeException {

    private ValidationResult validationResult;

    public CreditCardValidationException(final ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public List<String> getErrors() {
        return validationResult.getErrors();
    }
}
