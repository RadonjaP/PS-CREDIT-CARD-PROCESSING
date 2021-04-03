package com.ps.org.ccp.service;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.controller.model.CreditCardResponse;
import com.ps.org.ccp.repository.entity.CreditCardEntity;

import java.time.LocalDateTime;
import java.util.function.Function;

public class CreditCardMapper {

    public static Function<CreditCardRequest, CreditCardEntity> mapCreditCardRequestToEntity = (request) ->
            CreditCardEntity.builder()
                .cardNumber(request.getCardNumber())
                .name(request.getName())
                .cardLimit(request.getCardLimit())
                .createdOn(LocalDateTime.now())
                .lastUpdatedOn(LocalDateTime.now())
                .build();

    public static Function<CreditCardEntity, CreditCardResponse> mapCreditCardEntityToResponse = (entity) ->
            CreditCardResponse.builder()
                    .cardNumber(entity.getCardNumber())
                    .name(entity.getName())
                    .cardLimit(entity.getCardLimit())
                    .balance(entity.getBalance())
                    .build();

}
