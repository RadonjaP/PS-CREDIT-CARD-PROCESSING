package com.ps.org.test.ccp.service;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.service.validation.CreditCardValidator;
import com.ps.org.ccp.service.validation.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class CreditCardValidatorTest {

    private CreditCardValidator creditCardValidator = new CreditCardValidator();

    @Test
    public void validateRequest_whenDataValid_thenNoErrors() {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("12345678903555")
                .name("Test Card")
                .cardLimit(100.0)
                .build();

        ValidationResult result = creditCardValidator.validateRequest(request);

        Assert.assertTrue(result.isValid());
    }

    @Test
    public void validateRequest_whenCreditCardNumberIsInValid_thenErrorsAreFound() {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("123")
                .name("Test Card")
                .cardLimit(100.0)
                .build();

        ValidationResult result = creditCardValidator.validateRequest(request);

        Assert.assertFalse(result.isValid());
        Assert.assertEquals(1, result.getErrors().size());
        Assert.assertEquals("Given credit card number is not valid Luhn10 number.", result.getErrors().get(0));
    }

    @Test
    public void validateRequest_whenRequestIsInValid_thenErrorsAreFound() {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("1111111111111111111111111111111111111111")
                .name("")
                .cardLimit(100.0)
                .build();

        ValidationResult result = creditCardValidator.validateRequest(request);

        Assert.assertFalse(result.isValid());
        Assert.assertEquals(2, result.getErrors().size());
        Assert.assertEquals("Name of the card holder should not be empty.", result.getErrors().get(0));
        Assert.assertEquals("Given credit card number is not in allowed range.", result.getErrors().get(1));
    }

    @Test
    public void validateRequest_whenRequestIsInValidWithChars_thenErrorsAreFound() {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("11111IIIA1111111iiiii+increased_range")
                .name("Character Numbers User")
                .cardLimit(100.0)
                .build();

        ValidationResult result = creditCardValidator.validateRequest(request);

        Assert.assertFalse(result.isValid());
        Assert.assertEquals(3, result.getErrors().size());
        Assert.assertEquals("Credit card number must be numeric.", result.getErrors().get(0));
        Assert.assertEquals("Given credit card number is not in allowed range.", result.getErrors().get(1));
        Assert.assertEquals("Given credit card number is not valid Luhn10 number.", result.getErrors().get(2));
    }

}
