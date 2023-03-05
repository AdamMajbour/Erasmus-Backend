package com.example.erasmus_app.services;

import com.example.erasmus_app.Exceptions.AlreadyExistException;
import com.example.erasmus_app.models.City;
import com.example.erasmus_app.models.Country;
import com.example.erasmus_app.models.ImageUtil;
import com.example.erasmus_app.repositories.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public String addCity(String name, String country, String description, MultipartFile file) throws IOException {
        if (cityRepository.existsByNameAndCountry(name, country))
            throw new AlreadyExistException(HttpStatus.CONFLICT, "CITY ALREADY EXISTS!");

        cityRepository.save(City.builder().name(name).country(country)
                .description(description).imageName(file.getOriginalFilename())
                .imageType(file.getContentType()).image(ImageUtil.compressImage(file.getBytes()))
                .imageAddress("http://localhost:8080/city/image/" + name + "/" + country).build());

        return "Added!";
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Transactional
    public byte[] getImage(String name, String country) throws ChangeSetPersister.NotFoundException {
        Optional<City> city = cityRepository.findByNameAndCountry(name, country);

        if (city.isPresent()) return ImageUtil.decompressImage(city.get().getImage());
        else throw new ChangeSetPersister.NotFoundException();
    }
}
