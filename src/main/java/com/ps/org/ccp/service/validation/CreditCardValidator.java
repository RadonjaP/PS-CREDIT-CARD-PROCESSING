package com.ps.org.ccp.service.validation;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class CreditCardValidator extends AbstractCardValidator<CreditCardRequest> {

    private static final Integer CREDIT_CARD_MAX_DIGITS = 19;

    private static Predicate<CreditCardRequest> nameIsNotEmpty = request -> request.getName() != null && !"".equals(request.getName());

    private static Predicate<CreditCardRequest> ccNumberIsNumeric = request -> {
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

    private static Predicate<CreditCardRequest> ccNumberInRange = request -> request.getCardNumber().length() > 0 && request.getCardNumber().length() <= CREDIT_CARD_MAX_DIGITS;

    private static Predicate<CreditCardRequest> numberIsLuhn10 = request -> {
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
        return sum % 10 == 0;
    };

    private static final List<ValidationRule<CreditCardRequest>> RULES = Arrays.asList(
            new ValidationRule<>(nameIsNotEmpty, "Name of the card holder should not be empty."),
            new ValidationRule<>(ccNumberIsNumeric, "Credit card number must be numeric."),
            new ValidationRule<>(ccNumberInRange, "Given credit card number is not in allowed range."),
            new ValidationRule<>(numberIsLuhn10, "Given credit card number is not valid Luhn10 number.")
    );

    @Override
    protected List<ValidationRule<CreditCardRequest>> getRules() {
        return RULES;
    }
}
