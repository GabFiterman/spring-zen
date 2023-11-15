package com.gabfiterman.springzen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.dto.UpdateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
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

    @GetMapping
    public ResponseEntity<List<Professional>> listAllProfessionals(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<Professional> professionals = professionalService.getAllProfessionals(q, fields);

        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfessionalById(
            @PathVariable(required = false) Long id,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {

        if (id != null) {
            Professional professional = professionalService.getProfessionalById(id);

            if (professional != null) {
                return ResponseEntity.ok(professional);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Professional> professionals = professionalService.getAllProfessionals(q, fields);
            return ResponseEntity.ok(professionals);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateProfessional(
            @PathVariable Long id,
            @RequestBody UpdateProfessionalData data) {

        Professional professional = professionalService.getProfessionalById(id);

        if (professional != null) {
            professionalService.updateProfessional(professional, data);
            return ResponseEntity.ok("Sucesso cadastrado alterado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteProfessionalLogically(@PathVariable Long id) {
        professionalService.excludeProfessional(id);
        return ResponseEntity.ok("Sucesso contato excluído");
    }

}
