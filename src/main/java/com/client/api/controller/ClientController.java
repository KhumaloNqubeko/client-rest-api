package com.client.api.controller;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@RequestBody ClientDto clientDto) {

        ResponseMessage responseMessage = clientService.createClient(clientDto);

        return ResponseEntity
                .status(responseMessage.getHttpStatus())
                .body(responseMessage.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(
            @PathVariable(value = "id") long clientId,
            @RequestBody ClientDto clientDto) {
        ResponseMessage responseMessage = clientService.updateClient(clientId, clientDto);

        return ResponseEntity
                .status(responseMessage.getHttpStatus())
                .body(responseMessage.getMessage());
    }

    @GetMapping("/search")
    public @ResponseBody
    List<ClientDto> searchClient(@RequestParam String keyword) {
        return clientService.searchClient(keyword);
    }
}
