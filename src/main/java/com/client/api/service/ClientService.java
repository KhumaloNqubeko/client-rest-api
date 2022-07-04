package com.client.api.service;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.entitiy.Client;
import com.client.api.exception.ValidationException;
import com.client.api.repository.ClientRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        try {
            validationService.validateIdNumber(clientDto.getIdNumber());
            validationService.validateFirstName(clientDto.getFirstName());
            validationService.validateLastName(clientDto.getLastName());

            Client client = clientRepository.findByMobileNumber(clientDto.getMobileNumber()).orElse(null);

            validationService.checkMobileNumberDuplicate(client != null);
        } catch (ValidationException e) {
            return ResponseMessage.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }

        clientRepository.save(convertToEntity(clientDto));

        return ResponseMessage.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

    public Client convertToEntity(final ClientDto clientDto) {
        return modelMapper.map(clientDto, Client.class);
    }

}
