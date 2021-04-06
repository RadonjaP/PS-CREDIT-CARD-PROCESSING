package com.ps.org.ccp.controller.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardRequest {

    @NotNull
    private String name;

    @NotNull
    private String cardNumber;

    @NotNull
    private Double cardLimit;

}
