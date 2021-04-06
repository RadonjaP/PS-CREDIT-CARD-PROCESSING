package com.ps.org.ccp.service.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public class ValidationRule<T> {

    private Predicate<T> validationExpression;

    private String message;

}
