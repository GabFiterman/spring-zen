package com.gabfiterman.springzen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.dto.UpdateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.service.ContactService;

import jakarta.transaction.Transactional;

/**
 * Controller responsible for managing requests related to contacts.
 * All requests are mapped to the path "/contacts".
 */
@RestController
@RequestMapping("/contatos")
public class ContactController {

    /**
     * Service responsible for managing contacts.
     */
    @Autowired
    private ContactService contactService;

    /**
     * Creates a new contact with the data provided.
     *
     * @param data the data of the new contact
     * @return a ResponseEntity object with a success message and the ID of the
     *         contact created
     */
    @PostMapping
    @Transactional
    public ResponseEntity<String> createContact(@RequestBody CreateContactData data) {

        Long contactId = contactService.saveContact(data);

        return new ResponseEntity<>(
                "Sucesso! Contato com id: " + contactId + " cadastrado",
                HttpStatus.CREATED);
    }

    /**
     * Returns a list of contacts based on query parameters.
     *
     * @param q      optional query to filter contacts
     * @param fields optional fields to include in the response
     * @return ResponseEntity with the corresponding contact list
     */
    @GetMapping
    public ResponseEntity<List<Contact>> listAllContacts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<Contact> contacts = contactService.getAllContacts(q, fields);

        return ResponseEntity.ok(contacts);
    }

    /**
     * Returns a contact by ID or all contacts if ID is not provided.
     *
     * @param id     The ID of the contact to be returned (optional).
     * @param q      The search term to filter contacts (optional).
     * @param fields The fields to be returned in the response (optional).
     * @return A ResponseEntity object containing the contact or a list of contacts
     *         and the status of the response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(
            @PathVariable(required = false) Long id,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {

        if (id != null) {
            Contact contact = contactService.getContactById(id);

            if (contact != null) {
                return ResponseEntity.ok(contact);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Contact> contacts = contactService.getAllContacts(q, fields);
            return ResponseEntity.ok(contacts);
        }
    }

    /**
     * Updates a contact by its ID.
     *
     * @param id   the ID of the contact to be updated
     * @param data the new contact details
     * @return a ResponseEntity object with the message "Registered success changed"
     *         if the contact was updated successfully, or a ResponseEntity object
     *         with status 404 if the contact was not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContactById(
            @PathVariable Long id,
            @RequestBody UpdateContactData data) {

        Contact contact = contactService.getContactById(id);

        if (contact != null) {
            contactService.updateContact(contact, data);
            return ResponseEntity.ok("Sucesso cadastrado alterado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Logically deletes a contact by its ID.
     *
     * @param id the ID of the contact to be deleted
     * @return ResponseEntity with success message if the contact is deleted
     *         successfully
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteProfessionalLogically(@PathVariable Long id) {
        contactService.excludeContact(id);
        return ResponseEntity.ok("Sucesso contato excluído");
    }
}
