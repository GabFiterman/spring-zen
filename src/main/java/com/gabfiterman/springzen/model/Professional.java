package com.gabfiterman.springzen.model;

import java.sql.Date;

import com.gabfiterman.springzen.dto.CreateProfessionalData;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "professional")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Professional {

        public Professional(CreateProfessionalData data) {
                this.name = data.name();
                this.role = data.role();
                this.birthDate = data.birthDate();
                this.createdDate = data.createdDate();
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
}
