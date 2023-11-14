package com.gabfiterman.springzen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ContactRepository;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/contatos")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @PostMapping
    @Transactional
    public void createContact(@RequestBody CreateContactData data) {
        Professional professional = professionalRepository.findById(data.getProfessionalId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Professional not found with id: " + data.getProfessionalId()));

        Contact contact = new Contact(data);
        contact.setProfessional(professional);

        contactRepository.save(contact);
    }
}
