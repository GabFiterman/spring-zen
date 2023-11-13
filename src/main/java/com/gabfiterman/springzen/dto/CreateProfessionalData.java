package com.gabfiterman.springzen.dto;

import java.sql.Date;

import com.gabfiterman.springzen.model.ProfessionalRole;

public record CreateProfessionalData(
        String name,
        ProfessionalRole role,
        Date birthDate,
        Date createdDate) {
}
