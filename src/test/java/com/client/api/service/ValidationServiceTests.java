package com.client.api.service;

import com.client.api.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTests {

    @InjectMocks
    private ValidationService validationService;

    @Test
    public void should_validate_sa_id_number_is_not_null() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateIdNumber("");
        });

        Assertions.assertEquals("ID number is required", thrown.getMessage());
    }

    @Test
    public void should_validate_sa_id_number_length() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateIdNumber("94012353416844");
        });

        Assertions.assertEquals("ID number exceeds required length", thrown.getMessage());
    }

    @Test
    public void should_validate_sa_id_number_alpha_numeric() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateIdNumber("940123yy41684");
        });

        Assertions.assertEquals("ID number is not a number", thrown.getMessage());
    }

    @Test
    public void should_validate_sa_id_number_date_of_birth() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateIdNumber("9413235341684");
        });

        Assertions.assertEquals("Date in ID is invalid", thrown.getMessage());
    }

    @Test
    public void should_validate_sa_id_number() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateIdNumber("9401235341684");
        });

        Assertions.assertEquals("Not SA citizen", thrown.getMessage());
    }

    @Test
    public void should_validate_sa_first_name_not_null() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateFirstName("");
        });

        Assertions.assertEquals("First name is required", thrown.getMessage());
    }

    @Test
    public void should_validate_sa_last_name_not_null() {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            validationService.validateLastName("");
        });

        Assertions.assertEquals("Last name is required", thrown.getMessage());
    }

//    @Test
//    public void should_validate_sa_first_name_not_null() {
//        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
//            validationService.validateFirstName("");
//        });
//
//        Assertions.assertEquals("First name is required", thrown.getMessage());
//    }
}
