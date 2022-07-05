package com.client.api.service;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.entitiy.Client;
import com.client.api.exception.ClientNotFoundException;
import com.client.api.exception.ErrorStatus;
import com.client.api.exception.ValidationException;
import com.client.api.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final ValidationService validationService;

    @Autowired
    public ClientService(final ClientRepository clientRepository,
                         final ModelMapper modelMapper, ValidationService validationService) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
    }

    public ResponseMessage createClient(final ClientDto clientDto) {
        ResponseMessage responseMessage = validateRequest(clientDto, false);
        if (responseMessage.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
            return responseMessage;
        }

        clientRepository.save(convertToEntity(clientDto));

        return ResponseMessage.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    public ResponseMessage updateClient(final long id, final ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElse(null);

        if (client == null) {
            return ResponseMessage.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(ErrorStatus.CLIENT_NOT_FOUND.status)
                    .build();
        }

        ResponseMessage responseMessage = validateRequest(clientDto, true);
        if (responseMessage.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
            return responseMessage;
        }

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setMobileNumber(clientDto.getMobileNumber());
        client.setIdNumber(clientDto.getIdNumber());
        client.setAddress(clientDto.getAddress());

        clientRepository.save(client);

        return ResponseMessage.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    public ResponseMessage validateRequest(final ClientDto clientDto, boolean isUpdate) {
        try {
            Client clientById = clientRepository.findByIdNumber(clientDto.getIdNumber()).orElse(null);

            validationService.validateIdNumber(clientDto.getIdNumber(), clientById != null && !isUpdate);
            validationService.validateFirstName(clientDto.getFirstName());
            validationService.validateLastName(clientDto.getLastName());

            if (!isUpdate) {
                Client client = clientRepository.findByMobileNumber(clientDto.getMobileNumber()).orElse(null);

                validationService.checkMobileNumberDuplicate(client != null);
            }

            return ResponseMessage.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();
        } catch (ValidationException e) {
            return ResponseMessage.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    public List<ClientDto> searchClient(final String searchKeyword) {
        List<ClientDto> clients = new ArrayList<>();

        clientRepository.searchClient(searchKeyword).forEach(c -> {
            clients.add(convertToDto(c));
        });

        return clients;
    }

    private Client convertToEntity(final ClientDto clientDto) {
        return modelMapper.map(clientDto, Client.class);
    }

    private ClientDto convertToDto(final Client client) {
        return modelMapper.map(client, ClientDto.class);
    }
}
