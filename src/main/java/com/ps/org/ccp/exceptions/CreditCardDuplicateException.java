package com.ps.org.ccp.exceptions;

public class CreditCardDuplicateException extends RuntimeException {

    public CreditCardDuplicateException(String message){
        super(message);
    }

}
