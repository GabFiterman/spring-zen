package com.gabfiterman.springzen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabfiterman.springzen.model.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
