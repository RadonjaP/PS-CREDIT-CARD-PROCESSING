package com.ps.org.ccp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CreditCardProcessingApplication {

    public static void main(String[] args) {
        log.info("Starting CreditCardProcessingApplication...");
        SpringApplication.run(CreditCardProcessingApplication.class, args);
    }

}
