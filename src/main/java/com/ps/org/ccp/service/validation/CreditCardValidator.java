package com.ps.org.ccp.service.validation;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class CreditCardValidator {

    private static final Integer CREDIT_CARD_MAX_DIGITS = 19;

    public ValidationResult validateRequest(final CreditCardRequest request) {
        List<String> errors = new ArrayList<>();
        if (nameIsEmpty.test(request)) errors.add("Name of the card holder should not be empty.");
        if (ccNumberIsNumeric.negate().test(request)) errors.add("Credit card number must be numeric.");
        if (ccNumberInRange.negate().test(request)) errors.add("Given credit card number is not in allowed range.");
        if (numberIsLuhn10.negate().test(request)) errors.add("Given credit card number is not valid Luhn10 number.");

        return ValidationResult.builder().isValid(errors.isEmpty()).errors(errors).build();
    }

    private Predicate<CreditCardRequest> nameIsEmpty = (request -> request.getName() == null || "".equals(request.getName()));

    private Predicate<CreditCardRequest> ccNumberIsNumeric = (request) -> {
        String cardNumber = request.getCardNumber();
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            try {
                Integer.parseInt(cardNumber.substring(i, i + 1));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    };

    private Predicate<CreditCardRequest> ccNumberInRange = (request -> request.getCardNumber().length() > 0 && request.getCardNumber().length() <= CREDIT_CARD_MAX_DIGITS);

    private Predicate<CreditCardRequest> numberIsLuhn10 = (request) -> {
        String cardNumber = request.getCardNumber();
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n;
            try {
                n = Integer.parseInt(cardNumber.substring(i, i + 1));
            } catch (NumberFormatException e) {
                return false;
            }
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    };

}
