package com.gabfiterman.springzen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabfiterman.springzen.dto.CreateProfessionalData;
import com.gabfiterman.springzen.model.Professional;
import com.gabfiterman.springzen.model.ProfessionalRole;
import com.gabfiterman.springzen.service.ProfessionalService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessionalService professionalService;

    // Test case for creating a professional
    @Test
    public void testCreateContact() throws Exception {
        // Setup test data
        Long professionalId = 1L;
        CreateProfessionalData data = new CreateProfessionalData("Jhon Doe", ProfessionalRole.Desenvolvedor,
                Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01"));

        // Mock service response
        when(professionalService.saveProfessional(any(CreateProfessionalData.class))).thenReturn(professionalId);

        // Perform the HTTP request
        mockMvc.perform(post("/profissional")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(data)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Sucesso! Profissional com id: " + professionalId + " cadastrado"));

        // Verify that the service method was called with the correct data
        verify(professionalService, times(1)).saveProfessional(data);
    }

    // Helper method to convert objects to JSON string
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

        // Test case for search all professionals
    @Test
    public void testListAllProfessionals() throws Exception {
        // Setup mock data
        List<Professional> professionals = Arrays.asList(
                new Professional(1L, "John Doe", ProfessionalRole.Desenvolvedor, Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01")),
                new Professional(2L, "Jane Doe", ProfessionalRole.Designer, Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01")));

        // Mock service response
        when(professionalService.getAllProfessionals(null, null)).thenReturn(professionals);

        // Perform GET request
        mockMvc.perform(get("/profissional"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(professionals.size())))
                .andExpect(jsonPath("$[0].id", is(professionals.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(professionals.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(professionals.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(professionals.get(1).getName())));

        // Verify that the service method was called
        verify(professionalService, times(1)).getAllProfessionals(null, null);
    }

    @Test
    public void testGetProfessionalById() throws Exception {
        // Setup mock data
        Long professionalId = 1L;
        Professional professional = new Professional(professionalId, "John Doe", ProfessionalRole.Desenvolvedor,
                Date.valueOf("2021-01-01"), Date.valueOf("2021-01-01"));

        // Mock service response
        when(professionalService.getProfessionalById(professionalId)).thenReturn(professional);

        // Perform GET request with ID parameter
        mockMvc.perform(get("/profissional/{id}", professionalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(professional.getId().intValue())))
                .andExpect(jsonPath("$.name", is(professional.getName())));

        // Verify that the service method was called
        verify(professionalService, times(1)).getProfessionalById(professionalId);
    }

    @Test
    public void testGetProfessionalByIdNotFound() throws Exception {
        // Setup mock data
        Long nonExistentId = 99L;

        // Mock service response for a non-existent professional
        when(professionalService.getProfessionalById(nonExistentId)).thenReturn(null);

        // Perform GET request with non-existent ID parameter
        mockMvc.perform(get("/profissional/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        // Verify that the service method was called
        verify(professionalService, times(1)).getProfessionalById(nonExistentId);
    }

    @Test
    public void testGetProfessionalWithoutId() throws Exception {
        // Setup mock data
        List<Professional> professionals = Arrays.asList(
                new Professional(1L, "John Doe", ProfessionalRole.Desenvolvedor, Date.valueOf("2021-01-01"),
                        Date.valueOf("2021-01-01")),
                new Professional(2L, "Jane Doe", ProfessionalRole.Designer, Date.valueOf("2021-01-01"),
                        Date.valueOf("2021-01-01")));

        // Mock service response
        when(professionalService.getAllProfessionals(null, null)).thenReturn(professionals);

        // Perform GET request without ID parameter
        mockMvc.perform(get("/profissional"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(professionals.size())))
                .andExpect(jsonPath("$[0].id", is(professionals.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(professionals.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(professionals.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(professionals.get(1).getName())));

        // Verify that the service method was called
        verify(professionalService, times(1)).getAllProfessionals(null, null);
    }
}
