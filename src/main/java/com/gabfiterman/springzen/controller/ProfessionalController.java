package com.gabfiterman.springzen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/profissional")
public class ProfessionalController {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @PostMapping
    @Transactional
    public void createProfessional(@RequestBody CreateProfessionalData data) {
        professionalRepository.save(new Professional(data));
    }
}
