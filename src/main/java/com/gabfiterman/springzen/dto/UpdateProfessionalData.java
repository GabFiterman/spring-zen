package com.gabfiterman.springzen.dto;

import java.sql.Date;

import com.gabfiterman.springzen.model.ProfessionalRole;

/**
 * Class that represents a professional's update data.
 *
 * @param name      the name of the professional.
 * @param role      the role of the professional from the ProfessionalRole enum.
 * @param birthDate the professional's date of birth.
 */
public record UpdateProfessionalData(
                String name,
                ProfessionalRole role,
                Date birthDate) {
}
