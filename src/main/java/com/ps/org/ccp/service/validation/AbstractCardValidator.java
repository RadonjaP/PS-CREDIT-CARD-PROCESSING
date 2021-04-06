package com.ps.org.ccp.service.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCardValidator<T> {

    public ValidationResult validateRequest(final T request) {
        List<String> errors = new ArrayList<>();
        getRules().forEach(rule -> {
            if(!rule.getValidationExpression().test(request)) {
                errors.add(rule.getMessage());
            };
        });

        return ValidationResult.builder().isValid(errors.isEmpty()).errors(errors).build();
    }

    protected abstract List<ValidationRule<T>> getRules();

}
