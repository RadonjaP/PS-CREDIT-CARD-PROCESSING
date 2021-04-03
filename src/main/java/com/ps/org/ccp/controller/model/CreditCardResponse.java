package com.ps.org.ccp.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardResponse {

    private String name;

    private String cardNumber;

    private Double cardLimit;

    private Double  balance;

}
