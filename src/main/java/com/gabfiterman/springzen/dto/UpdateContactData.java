package com.gabfiterman.springzen.dto;

/**
 * Class that represents a professional's contact update data.
 *
 * @param name           the name of the contact to be updated
 * @param contact        the new contact to be updated
 * @param professionalId the ID of the professional whose contact details will
 *                       be updated
 * @return an instance of UpdateContactData
 */
public record UpdateContactData(
                String name,
                String contact,
                Long professionalId) {

        /**
         * @return Long
         */
        public Long getProfessionalId() {
                return professionalId;
        }

}
