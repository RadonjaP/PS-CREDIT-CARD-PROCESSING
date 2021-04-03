package com.ps.org.test.ccp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.org.ccp.CreditCardProcessingApplication;
import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.repository.CreditCardRepository;
import com.ps.org.ccp.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CreditCardProcessingApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:test-application.properties"})
public class CreditCardProcessingIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    public void createCreditCard_whenDataValid_thenExpectSuccess() throws Exception {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("12345678903555")
                .name("Test Card")
                .cardLimit(100.0)
                .build();

        mockMvc.perform(post("/credit-cards/add")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\": \"Test Card\",\"cardNumber\": \"12345678903555\",\"cardLimit\": 100.0}]"));

    }

    @Test
    public void createCreditCard_whenCardNumberInvalid_thenGiveDetailsAndCardNotSaved() throws Exception {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("3321")
                .name("Invalid Card")
                .cardLimit(100.0)
                .build();

        mockMvc.perform(post("/credit-cards/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    public void createCreditCard_whenExceptionOccurs_thenGiveDetails() throws Exception {
        Mockito.when(creditCardService.getAllCreditCards())
                .thenThrow(new NullPointerException());

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void createCreditCard_whenDuplicate_thenExpectBadRequest() throws Exception {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("79927398718")
                .name("Test Card")
                .cardLimit(100.0)
                .build();

        mockMvc.perform(post("/credit-cards/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/credit-cards/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
