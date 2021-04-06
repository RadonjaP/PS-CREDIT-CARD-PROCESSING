package com.ps.org.ccp.service;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.controller.model.CreditCardResponse;
import com.ps.org.ccp.exceptions.CreditCardDuplicateException;
import com.ps.org.ccp.exceptions.CreditCardValidationException;
import com.ps.org.ccp.repository.CreditCardRepository;
import com.ps.org.ccp.repository.entity.CreditCardEntity;
import com.ps.org.ccp.service.validation.CreditCardValidator;
import com.ps.org.ccp.service.validation.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardService {

    private CreditCardValidator creditCardValidator;

    private CreditCardRepository creditCardRepository;

    public void createNewCard(final CreditCardRequest request) {
        log.info("Creating new credit card with number: {}", request.getCardNumber());
        try {
            ValidationResult validationResult = creditCardValidator.validateRequest(request);
            if (!validationResult.isValid()) {
                throw new CreditCardValidationException(validationResult);
            }

            CreditCardEntity creditCard = buildCreditCard(request);
            creditCardRepository.saveAndFlush(creditCard);

        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            log.error("Unable to persist data. Credit card number is duplicate.", e);
            throw new CreditCardDuplicateException("Unable to persist data. Credit card number is duplicate.");
        }
    }

    public Optional<List<CreditCardResponse>> getAllCreditCards() {
        log.info("Getting all credit cards...");
        List<CreditCardEntity> creditCards = creditCardRepository.findAll();
        List<CreditCardResponse> creditCardsResponse = creditCards.stream()
                .map(CreditCardMapper.mapCreditCardEntityToResponse)
                .collect(Collectors.toList());

        return Optional.of(creditCardsResponse);
    }

    private CreditCardEntity buildCreditCard(final CreditCardRequest request) {
        return CreditCardEntity.builder()
                .cardNumber(request.getCardNumber())
                .name(request.getName())
                .cardLimit(request.getCardLimit())
                .balance(0.0)
                .createdOn(LocalDateTime.now())
                .lastUpdatedOn(LocalDateTime.now())
                .build();
    }
}
