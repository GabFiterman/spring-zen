package com.gabfiterman.springzen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabfiterman.springzen.dto.CreateContactData;
import com.gabfiterman.springzen.dto.UpdateContactData;
import com.gabfiterman.springzen.model.Contact;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ContactRepository;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * This class represents the service layer for the Contact entity. It contains
 * methods for creating, reading, updating and deleting contacts.
 * The class uses the ContactRepository and ProfessionalRepository interfaces to
 * interact with the database.
 */
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    /**
     * @param data
     * @return Long
     */
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

    /**
     * @param query
     * @param fields
     * @return List<Contact>
     */
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

    /**
     * @param contact
     * @param query
     * @return boolean
     */
    private boolean containsQuery(Contact contact, String query) {
        return contact.getName().contains(query) ||
                contact.getContact().toString().contains(query) ||
                contact.getCreatedDate().toString().contains(query);
    }

    /**
     * @param contacts
     * @param fields
     * @return List<Contact>
     */
    private List<Contact> filterFields(List<Contact> contacts, List<String> fields) {
        return contacts.stream().map(contact -> filterContactFields(contact, fields))
                .collect(Collectors.toList());
    }

    /**
     * @param contact
     * @param fields
     * @return Contact
     */
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

    /**
     * @param id
     * @return Contact
     */
    public Contact getContactById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return optionalContact.orElse(null);
    }

    /**
     * @param contact
     * @param data
     */
    public void updateContact(Contact contact, UpdateContactData data) {
        if (data.name() != null) {
            contact.setName(data.name());
        }

        if (data.contact() != null) {
            contact.setContact(data.contact());
        }

        if (data.professionalId() != null) {
            Professional professional = professionalRepository.findById(data.professionalId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Professional not found with id: " + data.professionalId()));

            contact.setProfessional(professional);
        }
        contactRepository.save(contact);
    }

    /**
     * @param id
     */
    public void excludeContact(Long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            contact.setActive(false);
            contactRepository.save(contact);
        }
    }
}
