package com.gabfiterman.springzen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateContactData;
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
}
