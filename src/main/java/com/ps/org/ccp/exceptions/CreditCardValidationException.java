package com.ps.org.ccp.exceptions;

import com.ps.org.ccp.service.ValidationResult;

public class CreditCardValidationException extends RuntimeException {

    public CreditCardValidationException(final ValidationResult validationResult) {

    }
}
