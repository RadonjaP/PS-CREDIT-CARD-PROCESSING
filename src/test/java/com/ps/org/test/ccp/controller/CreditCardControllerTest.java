package com.ps.org.test.ccp.controller;

import com.ps.org.ccp.controller.CreditCardController;
import com.ps.org.ccp.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class CreditCardControllerTest {

    @Autowired
    private CreditCardController creditCardController;

    @Mock
    private CreditCardService creditCardService;

    @Test
    public void getAllCreditCards_whenDataExists_thenReturnNonEmptyList() {

    }

}
