package com.example.erasmus_app.services;

import com.example.erasmus_app.Exceptions.AlreadyExistException;
import com.example.erasmus_app.models.City;
import com.example.erasmus_app.models.ImageUtil;
import com.example.erasmus_app.models.Institution;
import com.example.erasmus_app.repositories.InstitutionRepository;
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
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public String addInstitution(String name, String country, String city, String description, MultipartFile file)
            throws IOException {
        if (institutionRepository.existsByNameAndCountryAndCity(name, country, city))
            throw new AlreadyExistException(HttpStatus.CONFLICT, "INSTITUTION ALREADY EXISTS!");

        institutionRepository.save(Institution.builder().name(name).country(country).city(city)
                .description(description).imageName(file.getOriginalFilename())
                .imageType(file.getContentType()).image(ImageUtil.compressImage(file.getBytes()))
                .imageAddress("http://localhost:8080/institution/image/" + name + "/" + country + "/" + city).build());

        return "Added!";
    }

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    @Transactional
    public byte[] getImage(String name, String country, String city) throws ChangeSetPersister.NotFoundException {
        Optional<Institution> institution = institutionRepository.findByNameAndCountryAndCity(name, country, city);

        if (institution.isPresent()) return ImageUtil.decompressImage(institution.get().getImage());
        else throw new ChangeSetPersister.NotFoundException();
    }
}
