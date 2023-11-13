package com.gabfiterman.springzen.model;

import java.sql.Date;

public record Professional(
        String name,
        ProfessionalRole role,
        Date birthDate,
        Date createdDate) {
}
