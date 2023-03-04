package com.example.erasmus_app.services;

import com.example.erasmus_app.models.Country;
import com.example.erasmus_app.models.ImageUtil;
import com.example.erasmus_app.payload.response.CountryUploadResponse;
import com.example.erasmus_app.repositories.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryUploadResponse addCountry(MultipartFile file, String countryName, String description)
            throws IOException {
        if (countryRepository.existsByName(file.getOriginalFilename()))
            throw new FileAlreadyExistsException(file.getOriginalFilename());

        countryRepository.save(Country.builder()
                .name(countryName)
                .description(description)
                .imageName(file.getOriginalFilename())
                .imageType(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());

        return new CountryUploadResponse("Country added successfully: " + file.getOriginalFilename());
    }

    @Transactional
    public List<Country> getAllCountries() {

        return countryRepository.findAll();
    }

    @Transactional
    public byte[] getImage(String name) throws FileNotFoundException {
        Optional<Country> country = countryRepository.findByName(name);

        if (country.isPresent()) return ImageUtil.decompressImage(country.get().getImage());
        else throw new FileNotFoundException();
    }
}
