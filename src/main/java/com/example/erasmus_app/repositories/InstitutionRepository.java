package com.example.erasmus_app.repositories;

import com.example.erasmus_app.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    boolean existsByNameAndCountryAndCity(String name, String country, String city);
    Optional<Institution> findByNameAndCountryAndCity(String name, String country, String city);
}
