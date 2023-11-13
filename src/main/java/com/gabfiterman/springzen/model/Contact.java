package com.gabfiterman.springzen.model;

import java.sql.Date;

import com.gabfiterman.springzen.dto.CreateContactData;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Contact {

        public Contact(CreateContactData data) {
                this.name = data.name();
                this.contact = data.contact();
                this.createdDate = data.createdDate();
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

}
