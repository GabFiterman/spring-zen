package com.gabfiterman.springzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabfiterman.springzen.model.Professional;

/**
 * Interface responsible for managing Professional object persistence
 * operations.
 * Extends Spring Data JPA's JpaRepository interface.
 */
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
