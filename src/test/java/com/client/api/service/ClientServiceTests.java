package com.client.api.service;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.entitiy.Client;
import com.client.api.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void should_create_a_client() {
        ClientDto clientDto = ClientDto.builder()
                .firstName("Sabelo")
                .lastName("Shabalala")
                .mobileNumber("0825340956")
                .idNumber("9408125465084")
                .address("129 Khumalo street, Mofolo South, Soweto 1801")
                .build();

        when(clientRepository.findByIdNumber(clientDto.getIdNumber())).thenReturn(Optional.empty());

        doNothing().when(validationService).validateIdNumber(clientDto.getIdNumber(), false);

        when(modelMapper.map(clientDto, Client.class)).thenReturn(getClientEntity(clientDto));

        ResponseMessage responseMessage = clientService.createClient(clientDto);

        Assertions.assertEquals("Created", responseMessage.getMessage());
    }

    @Test
    public void should_update_a_client() {
        ClientDto clientDto = ClientDto.builder()
                .firstName("Nqubeko")
                .lastName("Khumalo")
                .mobileNumber("0825340956")
                .idNumber("9408125465084")
                .address("129 Khumalo street, Mofolo South, Soweto 1801")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(getClientEntity(clientDto)));
        when(clientRepository.findByIdNumber(clientDto.getIdNumber())).thenReturn(Optional.of(getClientEntity(clientDto)));

        doNothing().when(validationService).validateIdNumber(clientDto.getIdNumber(), true);

        ResponseMessage responseMessage = clientService.updateClient(1L, clientDto);

        Assertions.assertEquals("Created", responseMessage.getMessage());
    }

    @Test
    public void should_return_a_list_of_clients_when_searching() {
        when(clientRepository.searchClient("0825340956")).thenReturn(clients());

        List<ClientDto> clients = clientService.searchClient("0825340956");

        Assertions.assertEquals(2, clients.size());
    }

    private Client getClientEntity(final ClientDto clientDto) {
        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setMobileNumber(clientDto.getMobileNumber());
        client.setIdNumber(clientDto.getIdNumber());
        client.setAddress(clientDto.getAddress());
        return client;
    }

    private List<Client> clients() {
        Client client = new Client();
        client.setFirstName("Nqubeko");
        client.setLastName("Khumalo");
        client.setMobileNumber("0825340956");
        client.setIdNumber("9408125465084");
        client.setAddress("129 Khumalo street, Mofolo South, Soweto 1801");

        Client client2 = new Client();
        client.setFirstName("Nqubeko");
        client.setLastName("Khumalo");
        client.setMobileNumber("0825340956");
        client.setIdNumber("9408125465084");
        client.setAddress("129 Khumalo street, Mofolo South, Soweto 1801");

        return Arrays.asList(
                client, client2
        );
    }
}
