package com.example.erasmus_app.repositories;

import com.example.erasmus_app.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByNameAndCountry(String name, String country);
    Optional<City> findByNameAndCountry(String name, String country);
}
