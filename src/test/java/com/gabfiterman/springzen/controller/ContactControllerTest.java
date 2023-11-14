package com.gabfiterman.springzen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    public void testCreateContact() throws Exception {
        // Setup test data
        Long contactId = 1L;
        CreateContactData data = new CreateContactData("John Doe", "john@example.com", Date.valueOf("2021-01-01"),
                contactId);

        // Mock service response
        when(contactService.saveContact(any(CreateContactData.class))).thenReturn(contactId);

        // Perform the HTTP request
        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(data)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Sucesso! Contato com id: " + contactId + " cadastrado"));

        // Verify that the service method was called with the correct data
        verify(contactService, times(1)).saveContact(data);
    }

    // Helper method to convert objects to JSON string
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
