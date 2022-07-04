package com.client.api.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDto {
    // Required
    private String firstName;
    // Required
    private String lastName;
    private String mobileNumber;

    // Unique and Required
    private String idNumber;
    private long addressId;
}
