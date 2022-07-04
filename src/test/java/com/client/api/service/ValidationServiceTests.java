package com.client.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTests {

    @InjectMocks
    private ValidationService validationService;

    @Test
    public void should_validate_sa_id_number() {
        boolean isValid = validationService.validateIdNumber("9401235341684");

        System.out.println("Valid: " + isValid);
    }
}
