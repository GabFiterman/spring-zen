package com.gabfiterman.springzen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/contatos")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createContact(@RequestBody CreateContactData data) {

        Long contactId = contactService.saveContact(data);

        return new ResponseEntity<>(
                "Sucesso! Contato com id: " + contactId + " cadastrado",
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> listAllContacts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<Contact> contacts = contactService.getAllContacts(q, fields);

        return ResponseEntity.ok(contacts);
    }

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
}
