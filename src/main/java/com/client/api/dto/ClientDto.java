package com.client.api.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ClientDto {
    // Required
    private String firstName;
    // Required
    private String lastName;
    private String mobileNumber;

    // Unique and Required
    private String idNumber;
    private String address;
}
