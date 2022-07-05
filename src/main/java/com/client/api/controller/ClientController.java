package com.client.api.controller;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.service.ClientService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClient(final @RequestBody ClientDto clientDto) {

        log.debug("Request: [{}]", clientDto);
        ResponseMessage responseMessage = clientService.createClient(clientDto);
        log.debug("Response: [{}]", responseMessage);

        return ResponseEntity
                .status(responseMessage.getHttpStatus())
                .body(responseMessage.getMessage());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(
            final @PathVariable(value = "id") long clientId,
            final @RequestBody ClientDto clientDto) {
        ResponseMessage responseMessage = clientService.updateClient(clientId, clientDto);

        return ResponseEntity
                .status(responseMessage.getHttpStatus())
                .body(responseMessage.getMessage());
    }

    @GetMapping("/search")
    public @ResponseBody
    List<ClientDto> searchClient(final @RequestParam String keyword) {
        return clientService.searchClient(keyword);
    }
}
