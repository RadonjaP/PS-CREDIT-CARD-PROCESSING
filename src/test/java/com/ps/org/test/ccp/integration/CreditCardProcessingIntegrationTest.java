package com.ps.org.test.ccp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.org.ccp.CreditCardProcessingApplication;
import com.ps.org.ccp.controller.model.CreditCardRequest;
import com.ps.org.ccp.repository.CreditCardRepository;
import com.ps.org.ccp.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CreditCardProcessingApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = {"classpath:test-application.properties"})
public class CreditCardProcessingIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Test
    public void createCreditCard_whenDataValid_thenExpectSuccess() throws Exception {
        creditCardRepository.deleteAll();
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("12345678903555")
                .name("Test Card User")
                .cardLimit(3000.0)
                .build();

        mockMvc.perform(post("/credit-cards/add")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\": \"Test Card User\",\"cardNumber\": \"12345678903555\",\"cardLimit\": 3000.0,\"balance\": 0.0}]"));

    }

    @Test
    public void createCreditCard_whenCardNumberInvalid_thenGiveDetailsAndCardNotSaved() throws Exception {
        creditCardRepository.deleteAll();

        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("")
                .name("")
                .cardLimit(2000.0)
                .build();

        mockMvc.perform(post("/credit-cards/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Validation errors are present.\",\"errors\":[" +
                        "\"Name of the card holder should not be empty.\"," +
                        "\"Given credit card number is not in allowed range.\"]}"));

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void createCreditCard_whenCardNumberNotNumeric_thenExpectBadRequest() throws Exception {
        creditCardRepository.deleteAll();

        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("I23A 567B 9O3 555")
                .name("Invalid Card Owner")
                .cardLimit(11000.0)
                .build();

        mockMvc.perform(post("/credit-cards/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Validation errors are present.\",\"errors\":[" +
                        "\"Credit card number must be numeric.\"," +
                        "\"Given credit card number is not valid Luhn10 number.\"]}"));

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void gatAllCreditCards_whenExceptionOccurs_thenGiveDetails() throws Exception {
        Mockito.when(creditCardService.getAllCreditCards())
                .thenThrow(new NullPointerException());

        mockMvc.perform(get("/credit-cards/getAll"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"message\":\"Unexpected exception occurred.\",\"errors\":null}"));

    }

    @Test
    public void createCreditCard_whenDuplicate_thenExpectBadRequest() throws Exception {
        CreditCardRequest request = CreditCardRequest.builder()
                .cardNumber("12345678903555")
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
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"message\":\"Duplicate of credit card.\",\"errors\":null}"));

    }

}
