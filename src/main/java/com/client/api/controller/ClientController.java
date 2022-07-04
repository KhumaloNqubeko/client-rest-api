package com.client.api.controller;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    public ResponseEntity<String> createClient(@RequestBody ClientDto clientDto) {

        ResponseMessage responseMessage = clientService.createClient(clientDto);

        return ResponseEntity.status(responseMessage.getHttpStatus())
                .body(responseMessage.getMessage());
    }
}
