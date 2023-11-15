package com.gabfiterman.springzen.dto;

import java.sql.Date;

public record CreateContactData(
                String name,
                String contact,
                Date createdDate,
                Long professionalId,
                Boolean active
) {
        public Long getProfessionalId() {
                return professionalId;
        }
}
