package com.gabfiterman.springzen.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    public Long saveProfessional(CreateProfessionalData data) {
        Professional professional = new Professional(data);
        Professional savedProfessional = professionalRepository.save(professional);
        return savedProfessional.getId();
    }

    public List<Professional> getAllProfessionals(String query, List<String> fields) {
        List<Professional> professionals = professionalRepository.findAll();

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

    private boolean containsQuery(Professional professional, String query) {
        return professional.getName().contains(query) ||
                professional.getRole().toString().contains(query) ||
                professional.getBirthDate().toString().contains(query) ||
                professional.getCreatedDate().toString().contains(query);
    }

    private List<Professional> filterFields(List<Professional> professionals, List<String> fields) {
        return professionals.stream().map(professional -> filterProfessionalFields(professional, fields))
                .collect(Collectors.toList());
    }

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

}
