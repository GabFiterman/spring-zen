package com.gabfiterman.springzen.model;

import java.sql.Date;

import com.gabfiterman.springzen.dto.CreateProfessionalData;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a Professional entity with its attributes and methods.
 * It is mapped to the "professional" table in the database.
 */
@Entity
@Table(name = "professional")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Professional {

        /**
         * 
         * @param data
         */
        public Professional(CreateProfessionalData data) {
                this.name = data.name();
                this.role = data.role();
                this.birthDate = data.birthDate();
                this.createdDate = data.createdDate();
                this.active = true;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ProfessionalRole role;

        @Column(name = "birth_date", nullable = false)
        private Date birthDate;

        @Column(name = "created_date", nullable = false)
        private Date createdDate;

        @Column(name = "active")
        private Boolean active;

        /**
         * @return boolean
         */
        public boolean isActive() {
                return active;
        }
}
