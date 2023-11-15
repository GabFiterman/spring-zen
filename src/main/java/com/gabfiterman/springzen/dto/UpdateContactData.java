package com.gabfiterman.springzen.dto;

public record UpdateContactData(
                String name,
                String contact,
                Long professionalId) {

        public Long getProfessionalId() {
                return professionalId;
        }

}
