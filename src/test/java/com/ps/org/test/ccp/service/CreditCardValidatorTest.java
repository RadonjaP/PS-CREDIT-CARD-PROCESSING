package com.ps.org.test.ccp.service;

import com.ps.org.ccp.repository.CreditCardRepository;
import com.ps.org.ccp.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
public class CreditCardServiceTest {

    @Autowired
    private CreditCardService creditCardService;

    @Mock
    private CreditCardRepository creditCardRepository;


}
