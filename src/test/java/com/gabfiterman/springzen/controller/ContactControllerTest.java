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

import static org.hamcrest.Matchers.is;
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
                CreateContactData data = new CreateContactData("John Doe", "john@example.com",
                                Date.valueOf("2021-01-01"),
                                contactId, true);

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

        @Test
        public void testListAllContacts() throws Exception {
                // Setup mock data
                List<Contact> contacts = Arrays.asList(
                                new Contact(1L, "John Doe", "john@example.com", Date.valueOf("2021-01-01"), true),
                                new Contact(2L, "Jane Doe", "jane@example.com", Date.valueOf("2021-01-01"), true));

                // Mock service response
                when(contactService.getAllContacts(null, null)).thenReturn(contacts);

                // Perform GET request
                mockMvc.perform(get("/contatos"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.size()", is(contacts.size())))
                                .andExpect(jsonPath("$[0].id", is(contacts.get(0).getId().intValue())))
                                .andExpect(jsonPath("$[0].name", is(contacts.get(0).getName())))
                                .andExpect(jsonPath("$[0].contact", is(contacts.get(0).getContact())))
                                .andExpect(jsonPath("$[1].id", is(contacts.get(1).getId().intValue())))
                                .andExpect(jsonPath("$[1].name", is(contacts.get(1).getName())))
                                .andExpect(jsonPath("$[1].contact", is(contacts.get(1).getContact())));

                // Verify that the service method was called
                verify(contactService, times(1)).getAllContacts(null, null);
        }

        // Helper method to convert objects to JSON string
        private String asJsonString(Object obj) throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(obj);
        }

        @Test
        public void testGetContactById() throws Exception {
                // Setup mock data
                Long contactId = 1L;
                Contact contact = new Contact(contactId, "John Doe", "john@example.com", Date.valueOf("2021-01-01"),
                                true);

                // Mock service response
                when(contactService.getContactById(contactId)).thenReturn(contact);

                // Perform GET request with ID parameter
                mockMvc.perform(get("/contatos/{id}", contactId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id", is(contact.getId().intValue())))
                                .andExpect(jsonPath("$.name", is(contact.getName())))
                                .andExpect(jsonPath("$.contact", is(contact.getContact())));

                // Verify that the service method was called
                verify(contactService, times(1)).getContactById(contactId);
        }

        @Test
        public void testGetContactByIdNotFound() throws Exception {
                // Setup mock data
                Long nonExistentId = 99L;

                // Mock service response for a non-existent contact
                when(contactService.getContactById(nonExistentId)).thenReturn(null);

                // Perform GET request with non-existent ID parameter
                mockMvc.perform(get("/contatos/{id}", nonExistentId))
                                .andExpect(status().isNotFound());

                // Verify that the service method was called
                verify(contactService, times(1)).getContactById(nonExistentId);
        }

        @Test
        public void testGetContactWithoutId() throws Exception {
                // Setup mock data
                List<Contact> contacts = Arrays.asList(
                                new Contact(1L, "John Doe", "john@example.com", Date.valueOf("2021-01-01"), true),
                                new Contact(2L, "Jane Doe", "jane@example.com", Date.valueOf("2021-01-01"), true));

                // Mock service response
                when(contactService.getAllContacts(null, null)).thenReturn(contacts);

                // Perform GET request without ID parameter
                mockMvc.perform(get("/contatos"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.size()", is(contacts.size())))
                                .andExpect(jsonPath("$[0].id", is(contacts.get(0).getId().intValue())))
                                .andExpect(jsonPath("$[0].name", is(contacts.get(0).getName())))
                                .andExpect(jsonPath("$[0].contact", is(contacts.get(0).getContact())))
                                .andExpect(jsonPath("$[1].id", is(contacts.get(1).getId().intValue())))
                                .andExpect(jsonPath("$[1].name", is(contacts.get(1).getName())))
                                .andExpect(jsonPath("$[1].contact", is(contacts.get(1).getContact())));

                // Verify that the service method was called
                verify(contactService, times(1)).getAllContacts(null, null);
        }

}
