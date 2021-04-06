package com.ps.org.ccp.controller;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/credit-cards")
public class CreditCardController {

    private CreditCardService creditCardService;

    @PostMapping("/add")
    public ResponseEntity createNewCard(@Valid @RequestBody CreditCardRequest request) {
        creditCardService.createNewCard(request);
        return ResponseEntity.ok().body("Saved credit card with number " + request.getCardNumber());
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity getAllCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllCreditCards().orElse(Collections.EMPTY_LIST));
    }

}
