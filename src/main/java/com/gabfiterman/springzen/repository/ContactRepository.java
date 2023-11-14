package com.gabfiterman.springzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabfiterman.springzen.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
