package com.gabfiterman.springzen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.dto.UpdateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

/**
 * This class represents the service layer for the Professional entity.
 * It provides methods for creating, retrieving, updating and deleting
 * professionals.
 */
@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    /**
     * @param data
     * @return Long
     */
    public Long saveProfessional(CreateProfessionalData data) {
        Professional professional = new Professional(data);
        Professional savedProfessional = professionalRepository.save(professional);
        return savedProfessional.getId();
    }

    /**
     * @param query
     * @param fields
     * @return List<Professional>
     */
    public List<Professional> getAllProfessionals(String query, List<String> fields) {
        List<Professional> professionals = professionalRepository.findAll();

        professionals = professionals.stream()
                .filter(Professional::isActive)
                .collect(Collectors.toList());

        if (query != null && !query.isEmpty()) {
            String lowerQuery = query.replace("%", " ");

            professionals = professionals.stream()
                    .filter(professional -> containsQuery(professional, lowerQuery))
                    .collect(Collectors.toList());
        }

        if (fields != null && !fields.isEmpty()) {
            professionals = filterFields(professionals, fields);
        }

        return professionals;
    }

    /**
     * @param professional
     * @param query
     * @return boolean
     */
    private boolean containsQuery(Professional professional, String query) {
        return professional.getName().contains(query) ||
                professional.getRole().toString().contains(query) ||
                professional.getBirthDate().toString().contains(query) ||
                professional.getCreatedDate().toString().contains(query);
    }

    /**
     * @param professionals
     * @param fields
     * @return List<Professional>
     */
    private List<Professional> filterFields(List<Professional> professionals, List<String> fields) {
        return professionals.stream().map(professional -> filterProfessionalFields(professional, fields))
                .collect(Collectors.toList());
    }

    /**
     * @param professional
     * @param fields
     * @return Professional
     */
    private Professional filterProfessionalFields(Professional professional, List<String> fields) {
        Professional filteredProfessional = new Professional();
        for (String field : fields) {
            switch (field) {
                case "id":
                    filteredProfessional.setId(professional.getId());
                    break;
                case "name":
                    filteredProfessional.setName(professional.getName());
                    break;
                case "role":
                    filteredProfessional.setRole(professional.getRole());
                    break;
                case "birthDate":
                    filteredProfessional.setBirthDate(professional.getBirthDate());
                    break;
                case "createdDate":
                    filteredProfessional.setCreatedDate(professional.getCreatedDate());
                    break;
            }
        }
        return filteredProfessional;
    }

    /**
     * @param id
     * @return Professional
     */
    public Professional getProfessionalById(Long id) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        return optionalProfessional.orElse(null);
    }

    /**
     * @param professional
     * @param data
     */
    public void updateProfessional(Professional professional, UpdateProfessionalData data) {
        if (data.name() != null) {
            professional.setName(data.name());
        }

        if (data.role() != null) {
            professional.setRole(data.role());
        }

        if (data.birthDate() != null) {
            professional.setBirthDate(data.birthDate());
        }

        professionalRepository.save(professional);
    }

    /**
     * @param id
     */
    public void excludeProfessional(Long id) {
        Professional professional = professionalRepository.findById(id).orElse(null);
        if (professional != null) {
            professional.setActive(false);
            professionalRepository.save(professional);
        }
    }

}
