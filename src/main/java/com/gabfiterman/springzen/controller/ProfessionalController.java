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

/**
 * Controller responsible for managing requests related to
 * professionals.
 * All requests are mapped to the path "/professional".
 */
@RestController
@RequestMapping("/profissional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    /**
     * Creates a new professional with the data provided.
     *
     * @param data the professional data to be created
     * @return a ResponseEntity object with a success message and the ID of the
     *         created professional
     */
    @PostMapping
    @Transactional
    public ResponseEntity<String> createProfessional(@RequestBody CreateProfessionalData data) {

        Long professionalId = professionalService.saveProfessional(data);

        return new ResponseEntity<>(
                "Sucesso! Profissional com id: " + professionalId + " cadastrado",
                HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all professionals.
     * 
     * @param q      The query string to filter professionals by.
     * @param fields The list of fields to include in the response.
     * @return A ResponseEntity containing a list of Professional objects.
     */
    @GetMapping
    public ResponseEntity<List<Professional>> listAllProfessionals(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<Professional> professionals = professionalService.getAllProfessionals(q, fields);

        return ResponseEntity.ok(professionals);
    }

    /**
     * Retrieves a Professional by its ID or all Professionals if ID is not
     * provided.
     * 
     * @param id     The ID of the Professional to retrieve. Optional.
     * @param q      The search query to filter Professionals. Optional.
     * @param fields The fields to include in the response. Optional.
     * @return A ResponseEntity with either a single Professional or a List of
     *         Professionals.
     */
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

    /**
     * Update an existing professional.
     *
     * @param id   the ID of the professional to be updated
     * @param data the professional's updated data
     * @return a ResponseEntity object with a success message if the practitioner
     *         was updated successfully, or a ResponseEntity object with status 404
     *         if the practitioner was not found
     */
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

    /**
     * Logically excludes a professional by their ID.
     *
     * @param id the ID of the professional to be deleted
     * @return ResponseEntity with success message
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteProfessionalLogically(@PathVariable Long id) {
        professionalService.excludeProfessional(id);
        return ResponseEntity.ok("Sucesso profissional excluído");
    }

}
