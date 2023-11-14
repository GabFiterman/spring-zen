package com.gabfiterman.springzen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ContactRepository;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Transactional
    public Long saveContact(CreateContactData data) {
        Professional professional = professionalRepository.findById(data.getProfessionalId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Professional not found with id: " + data.getProfessionalId()));

        Contact contact = new Contact(data);
        contact.setProfessional(professional);

        Contact savedContact = contactRepository.save(contact);

        return savedContact.getId();
    }
}
