package com.gabfiterman.springzen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.repository.ProfessionalRepository;
import com.gabfiterman.springzen.service.ProfessionalService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/profissional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createProfessional(@RequestBody CreateProfessionalData data) {

        Long professionalId = professionalService.saveProfessional(data);

        return new ResponseEntity<>(
                "Sucesso! Profissional com id: " + professionalId + " cadastrado",
                HttpStatus.CREATED);
    }
}
