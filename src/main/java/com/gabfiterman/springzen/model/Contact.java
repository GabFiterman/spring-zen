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
        }

        // Construtor para criar um Contact diretamente (tests)
        public Contact(Long id, String name, String contact, Date createdDate) {
                this.id = id;
                this.name = name;
                this.contact = contact;
                this.createdDate = createdDate;
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

}
