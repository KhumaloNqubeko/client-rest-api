package com.client.api.controller;

import com.client.api.dto.ClientDto;
import com.client.api.dto.ResponseMessage;
import com.client.api.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTests {
    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_response_message_of_created() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .firstName("Sabelo")
                .lastName("Shabalala")
                .mobileNumber("0825340956")
                .idNumber("9408125465084")
                .address("129 Khumalo street, Mofolo South, Soweto 1801")
                .build();

        ResponseMessage responseMessage = ResponseMessage.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(HttpStatus.CREATED.getReasonPhrase())
                        .build();

        doReturn(responseMessage).when(clientService).createClient(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/create")
                .content(asJsonString(clientDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().string("Created"));
    }

    @Test
    public void should_return_response_message_of_created_on_update() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .firstName("Sabelo")
                .lastName("Khumalo")
                .mobileNumber("0825340956")
                .idNumber("9408125465084")
                .address("129 Khumalo street, Mofolo South, Soweto 1801")
                .build();

        ResponseMessage responseMessage = ResponseMessage.builder()
                .httpStatus(HttpStatus.CREATED)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();

        doReturn(responseMessage).when(clientService).updateClient(eq(2L), any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/update/2")
                .content(asJsonString(clientDto))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().string("Created"));
    }

    @Test
    public void should_return_a_list_of_clients() throws Exception {
        doReturn(getClients()).when(clientService).searchClient(any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/search?keyword=Sa")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private List<ClientDto> getClients() {
        return Arrays.asList(
                ClientDto.builder()
                        .firstName("Sabelo")
                        .lastName("Khumalo")
                        .mobileNumber("0825340956")
                        .idNumber("9408125465084")
                        .address("129 Khumalo street, Mofolo South, Soweto 1801")
                        .build()
        );
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
