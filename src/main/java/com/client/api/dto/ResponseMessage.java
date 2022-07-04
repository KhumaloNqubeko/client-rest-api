package com.client.api.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private HttpStatus httpStatus;
    private String message;
}
