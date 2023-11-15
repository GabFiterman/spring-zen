package com.gabfiterman.springzen.model;

import java.sql.Date;

import com.gabfiterman.springzen.dto.CreateContactData;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Contact {

        public Contact(CreateContactData data) {
                this.name = data.name();
                this.contact = data.contact();
                this.createdDate = data.createdDate();
                this.active = true;
        }

        // Construtor para criar um Contact diretamente (tests)
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

        // TODO: replace with @CreationTimestamp
        @Column(name = "created_date", nullable = false)
        private Date createdDate;

        @ManyToOne
        @JoinColumn(name = "professional_id")
        private Professional professional;

        public void setProfessional(Professional professional) {
                this.professional = professional;
        }

        @Column(name = "active")
        private Boolean active;

        public boolean isActive() {
                return active;
        }
}
