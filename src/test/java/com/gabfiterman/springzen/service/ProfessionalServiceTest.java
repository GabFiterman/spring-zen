package com.gabfiterman.springzen.service;

import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.model.ProfessionalRole;
import com.gabfiterman.springzen.repository.ProfessionalRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfessionalServiceTest {

    @MockBean
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ProfessionalService professionalService;

    // Test case for saving a professional
    @Test
    public void testSaveProfessional() {
        // Setup test data
        CreateProfessionalData data = new CreateProfessionalData("Jhon Doe", ProfessionalRole.Desenvolvedor,
                Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01"), true);

        // Mock repository response
        Professional professional = new Professional(data);
        when(professionalRepository.save(any(Professional.class))).thenReturn(professional);

        // Call the method under test
        Long savedProfessionalId = professionalService.saveProfessional(data);

        // Assert the results
        assertEquals(professional.getId(), savedProfessionalId);
        verify(professionalRepository, times(1)).save(professional);
    }

    // Test case for getting all professionals
    @Test
    public void testGetAllProfessionals() {
        // Setup test data
        CreateProfessionalData data1 = new CreateProfessionalData("Jhon Doe", ProfessionalRole.Desenvolvedor,
                Date.valueOf("2000-01-05"), Date.valueOf("2023-11-14"), true);
        CreateProfessionalData data2 = new CreateProfessionalData("Ronny Jhonson", ProfessionalRole.Desenvolvedor,
                Date.valueOf("1978-04-22"), Date.valueOf("2021-11-14"), true);

        // Mock repository response
        Professional professional1 = new Professional(data1);
        Professional professional2 = new Professional(data2);
        List<Professional> professionals = Arrays.asList(professional1, professional2);
        when(professionalRepository.findAll()).thenReturn(professionals);

        // Call the method under test
        List<Professional> result = professionalService.getAllProfessionals(null, null);

        // Assert the results
        assertEquals(2, result.size());
        verify(professionalRepository, times(1)).findAll();
    }

    // Test case for getting all professionals with filters and fields
    @Test
    public void testGetAllProfessionalsWithFiltersAndFields() {
        // Setup test data
        CreateProfessionalData data1 = new CreateProfessionalData("Jhon Doe", ProfessionalRole.Desenvolvedor,
                Date.valueOf("2000-01-05"), Date.valueOf("2023-11-14"), true);
        CreateProfessionalData data2 = new CreateProfessionalData("Ronny Jhonson", ProfessionalRole.Desenvolvedor,
                Date.valueOf("1978-04-22"), Date.valueOf("2021-11-14"), true);
        CreateProfessionalData data3 = new CreateProfessionalData("Bob Doe", ProfessionalRole.Desenvolvedor,
                Date.valueOf("1978-04-22"), Date.valueOf("2021-11-14"), true);

        // Mock repository response
        Professional professional1 = new Professional(data1);
        Professional professional2 = new Professional(data2);
        Professional professional3 = new Professional(data3);
        List<Professional> professionals = Arrays.asList(professional1, professional2, professional3);

        when(professionalRepository.findAll()).thenReturn(professionals);

        // Call the method under test with filters and fields
        List<String> fields = Arrays.asList("id", "name");
        List<Professional> result = professionalService.getAllProfessionals("Jhon Doe", fields);

        // Assert the results
        assertEquals(1, result.size());
        assertEquals(professional1.getId(), result.get(0).getId());

        // Call the method under test with filters and fields to return multiple results
        List<Professional> result2 = professionalService.getAllProfessionals("Doe", fields);

        // Assert the results
        assertEquals(2, result2.size());
        assertEquals(professional1.getId(), result.get(0).getId());

        verify(professionalRepository, times(2)).findAll();
    }

    // Test case for getting a professional by Id
    @Test
    public void testGetProfessionalById() {
        // Setup test data
        Long professionalId = 1L;
        Professional professional = new Professional();
        professional.setId(professionalId);

        // Mock repository response when a professional with the provided ID exists
        when(professionalRepository.findById(professionalId)).thenReturn(Optional.of(professional));

        // Test when a professional with the provided ID exists
        Professional result = professionalService.getProfessionalById(professionalId);

        // Assert the results
        assertNotNull(result);
        assertEquals(professionalId, result.getId());
        verify(professionalRepository, times(1)).findById(professionalId);

        // Setup test data for a non-existent professional
        Long nonExistentId = 2L;

        // Mock repository response when a professional with the provided ID does not
        // exist
        when(professionalRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Test when a professional with the provided ID does not exist
        Professional nonExistentResult = professionalService.getProfessionalById(nonExistentId);

        // Assert the results
        assertNull(nonExistentResult);
        verify(professionalRepository, times(1)).findById(nonExistentId);
    }

    // Test case for excluding a professional
    @Test
    public void testExcludeProfessional() {
        // Setup test data
        Long professionalId = 1L;
        Professional professional = new Professional();
        professional.setId(professionalId);
        professional.setActive(true);

        // Mock repository response when a professional with the provided ID exists
        when(professionalRepository.findById(professionalId)).thenReturn(Optional.of(professional));

        // Call the method under test
        professionalService.excludeProfessional(professionalId);

        // Assert the results
        assertFalse(professional.isActive());
        verify(professionalRepository, times(1)).findById(professionalId);
        verify(professionalRepository, times(1)).save(professional);
    }
}