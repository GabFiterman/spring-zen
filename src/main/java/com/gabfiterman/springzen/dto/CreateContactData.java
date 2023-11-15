package com.gabfiterman.springzen.dto;

import java.sql.Date;

/**
 * /**
 * This class represents the data required to create a contact.
 *
 * @param name           contact name
 * @param contact        contact information
 * @param createdDate    date of creation of the contact record
 * @param professionalId ID of the professional associated with the contact
 * @param active         indicates whether the contact is active or not
 */

public record CreateContactData(
                String name,
                String contact,
                Date createdDate,
                Long professionalId,
                Boolean active) {

        /**
         * @return Long
         */
        public Long getProfessionalId() {
                return professionalId;
        }
}
