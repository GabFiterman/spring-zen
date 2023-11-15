package com.gabfiterman.springzen.model;

import java.sql.Date;

import com.gabfiterman.springzen.dto.CreateContactData;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a contact entity, which can be associated with a
 * professional.
 */
@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Contact {

        /**
         * 
         * @param data
         */
        public Contact(CreateContactData data) {
                this.name = data.name();
                this.contact = data.contact();
                this.createdDate = data.createdDate();
                this.active = true;
        }

        // Constructor to create a Contact directly (tests)
        /**
         * 
         * @param id
         * @param name
         * @param contact
         * @param createdDate
         * @param active
         */
        public Contact(Long id, String name, String contact, Date createdDate, boolean active) {
                this.id = id;
                this.name = name;
                this.contact = contact;
                this.createdDate = createdDate;
                this.active = true;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String contact;

        @Column(name = "created_date", nullable = false)
        private Date createdDate;

        @ManyToOne
        @JoinColumn(name = "professional_id")
        private Professional professional;

        /**
         * @param professional
         */
        public void setProfessional(Professional professional) {
                this.professional = professional;
        }

        @Column(name = "active")
        private Boolean active;

        /**
         * @return boolean
         */
        public boolean isActive() {
                return active;
        }
}
