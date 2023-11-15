package com.gabfiterman.springzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabfiterman.springzen.model.Contact;

/**
 * Interface responsible for managing contact persistence operations.
  * Extends Spring Data JPA's JpaRepository interface.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
