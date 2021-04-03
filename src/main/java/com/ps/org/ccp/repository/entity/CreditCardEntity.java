package com.ps.org.ccp.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CREDIT_CARDS")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CARD_NUMBER", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CARD_LIMIT")
    private Double cardLimit;

    @Column(name = "BALANCE")
    private Double balance;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;

}
