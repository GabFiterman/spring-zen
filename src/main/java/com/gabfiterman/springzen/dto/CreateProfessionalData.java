package com.gabfiterman.springzen.dto;

import java.sql.Date;

import com.gabfiterman.springzen.model.ProfessionalRole;

/**
 * Class that represents the data necessary to create a new professional.
 *
 * @param name        professional name
 * @param role        professional position
 * @param birthDate   professional's birth date
 * @param createdDate professional record creation date
 * @param active      indicates whether the professional is active or not
 */
public record CreateProfessionalData(
                String name,
                ProfessionalRole role,
                Date birthDate,
                Date createdDate,
                Boolean active) {

}
