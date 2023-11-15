package com.gabfiterman.springzen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ContactRepository;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

@SpringBootTest
public class ContactServiceTest {

        @MockBean
        private ContactRepository contactRepository;

        @MockBean
        private ProfessionalRepository professionalRepository;

        @Autowired
        private ContactService contactService;

        // Test case for saving a contact
        @Test
        public void testSaveContact() {
                // Setup test data
                Long professionalId = 1L;
                CreateContactData data = new CreateContactData("John Contact", "john@example.com",
                                Date.valueOf("2021-01-01"),
                                professionalId, true);

                // Mock repository response
                Professional professional = new Professional();
                professional.setId(professionalId);
                when(professionalRepository.findById(professionalId)).thenReturn(Optional.of(professional));

                Contact contact = new Contact(data);
                when(contactRepository.save(any(Contact.class))).thenReturn(contact);

                // Call the method under test
                Long savedContactId = contactService.saveContact(data);

                // Assert the results
                assertEquals(contact.getId(), savedContactId);
                verify(professionalRepository, times(1)).findById(professionalId);
                verify(contactRepository, times(1)).save(contact);
        }

        // Test case for getting all professionals
        @Test
        public void testGetAllContacts() {
                // Setup test data
                CreateContactData data1 = new CreateContactData("John Contact", "john@example.com",
                                Date.valueOf("2021-10-24"), 1L, true);
                CreateContactData data2 = new CreateContactData("Jane Contact", "jane@example.com",
                                Date.valueOf("2021-06-31"), 2L, true);

                // Mock repository response
                Contact contact1 = new Contact(data1);
                Contact contact2 = new Contact(data2);
                List<Contact> contacts = Arrays.asList(contact1, contact2);
                when(contactRepository.findAll()).thenReturn(contacts);

                // Call the method under test
                List<Contact> result = contactService.getAllContacts(null, null);

                // Assert the results
                assertEquals(2, result.size());
                verify(contactRepository, times(1)).findAll();
        }

        // Test case for getting all professionals with filters and fields
        @Test
        public void testGetAllContactsWithFiltersAndFields() {
                // Setup test data
                CreateContactData data1 = new CreateContactData("John Contact", "john@example.com",
                                Date.valueOf("2021-01-01"), 1L, true);
                CreateContactData data2 = new CreateContactData("Jane Contact", "jane@example.com",
                                Date.valueOf("2021-01-01"), 2L, true);
                CreateContactData data3 = new CreateContactData("Bob Contact", "bob@example.com",
                                Date.valueOf("2021-01-01"), 1L, true);

                // Mock repository response
                Contact contact1 = new Contact(data1);
                Contact contact2 = new Contact(data2);
                Contact contact3 = new Contact(data3);
                List<Contact> contacts = Arrays.asList(contact1, contact2, contact3);

                when(contactRepository.findAll()).thenReturn(contacts);

                // Call the method under test with filters and fields
                List<String> fields = Arrays.asList("id", "name");
                List<Contact> result = contactService.getAllContacts("John Contact", fields);

                // Assert the results
                assertEquals(1, result.size());
                assertEquals(contact1.getId(), result.get(0).getId());

                // Call the method under test with filters and fields to return multiple results
                List<Contact> result2 = contactService.getAllContacts("Contact", fields);

                // Assert the results
                assertEquals(3, result2.size());

                verify(contactRepository, times(2)).findAll();
        }

        // Test case for getting a professional by Id
        @Test
        public void testGetContactById() {
                // Setup test data
                Long contactId = 1L;
                Contact contact = new Contact();
                contact.setId(contactId);

                // Mock repository response when a contact with the provided ID exists
                when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

                // Test when a contact with the provided ID exists
                Contact result = contactService.getContactById(contactId);

                // Assert the results
                assertNotNull(result);
                assertEquals(contactId, result.getId());
                verify(contactRepository, times(1)).findById(contactId);

                // Setup test data for a non-existent contact
                Long nonExistentId = 2L;

                // Mock repository response when a contact with the provided ID does not exist
                when(contactRepository.findById(nonExistentId)).thenReturn(Optional.empty());

                // Test when a contact with the provided ID does not exist
                Contact nonExistentResult = contactService.getContactById(nonExistentId);

                // Assert the results
                assertNull(nonExistentResult);
                verify(contactRepository, times(1)).findById(nonExistentId);
        }

        @Test
        public void testExcludeContact() {
                // Setup test data
                Long contactId = 1L;
                Contact contact = new Contact();
                contact.setId(contactId);
                contact.setActive(true);

                // Mock repository response when a contact with the provided ID exists
                when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

                // Call the method under test
                contactService.excludeContact(contactId);

                // Assert the results
                assertFalse(contact.isActive());
                verify(contactRepository, times(1)).findById(contactId);
                verify(contactRepository, times(1)).save(contact);
        }
}
