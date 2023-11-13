package com.gabfiterman.springzen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.repository.ContactRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/contatos")
public class ContactController {
    
    @Autowired
    private ContactRepository contactRepository;

    @PostMapping
    @Transactional
    public void createContact(@RequestBody CreateContactData data) {
        contactRepository.save(new Contact(data));
    }
}
