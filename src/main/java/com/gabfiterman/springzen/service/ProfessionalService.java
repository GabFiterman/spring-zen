package com.gabfiterman.springzen.service;

import java.util.List;

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

    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAll();
    }

}
