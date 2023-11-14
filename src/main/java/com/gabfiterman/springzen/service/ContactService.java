package com.gabfiterman.springzen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Contact> getAllContacts(String query, List<String> fields) {
        List<Contact> contacts = contactRepository.findAll();

        if (query != null && !query.isEmpty()) {
            String lowerQuery = query.replace("%", " ");

            contacts = contacts.stream()
                    .filter(contact -> containsQuery(contact, lowerQuery))
                    .collect(Collectors.toList());
        }

        if (fields != null && !fields.isEmpty()) {
            contacts = filterFields(contacts, fields);
        }

        return contacts;
    }

    private boolean containsQuery(Contact contact, String query) {
        return contact.getName().contains(query) ||
                contact.getContact().toString().contains(query) ||
                contact.getCreatedDate().toString().contains(query);
    }

    private List<Contact> filterFields(List<Contact> contacts, List<String> fields) {
        return contacts.stream().map(contact -> filterContactFields(contact, fields))
                .collect(Collectors.toList());
    }

    private Contact filterContactFields(Contact contact, List<String> fields) {
        Contact filteredContact = new Contact();
        for (String field : fields) {
            switch (field) {
                case "id":
                    filteredContact.setId(contact.getId());
                    break;
                case "name":
                    filteredContact.setName(contact.getName());
                    break;
                case "contact":
                    filteredContact.setContact(contact.getContact());
                    break;
                case "createdDate":
                    filteredContact.setCreatedDate(contact.getCreatedDate());
                    break;
            }
        }
        return filteredContact;
    }

    public Contact getContactById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return optionalContact.orElse(null);
    }
}
