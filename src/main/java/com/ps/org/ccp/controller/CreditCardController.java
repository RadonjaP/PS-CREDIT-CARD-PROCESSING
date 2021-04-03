package com.ps.org.ccp.controller;

import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/credit-cards")
public class CreditCardController {

    private CreditCardService creditCardService;

    @PostMapping("/add")
    public ResponseEntity createNewCard(@RequestBody CreditCardRequest request) {
        creditCardService.createNewCard(request);
        return ResponseEntity.ok().body("Saved credit card with number " + request.getCardNumber());
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List> getAllCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllCreditCards().orElse(new ArrayList()));
    }

}