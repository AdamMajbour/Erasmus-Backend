package com.example.erasmus_app.repositories;

import com.example.erasmus_app.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
    boolean existsByName(String name);
    List<Country> findAll();
}
