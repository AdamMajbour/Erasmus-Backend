package com.example.erasmus_app.controllers;

import com.example.erasmus_app.models.Country;
import com.example.erasmus_app.payload.response.CountryUploadResponse;
import com.example.erasmus_app.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/country")
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/{countryName}")
    public ResponseEntity<?> addCountry(@RequestParam("image") MultipartFile file, @PathVariable String countryName,
                                        @RequestParam("description") String description) {
        try {
            CountryUploadResponse response = countryService.addCountry(file, countryName, description);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (FileAlreadyExistsException e) {
            log.warn("Country " + file.getOriginalFilename() + " is already added!");
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAllCountries(){
        return ResponseEntity.status(HttpStatus.OK).body(countryService.getAllCountries());
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<?> getCountryImage(@PathVariable String name){
        try {
            byte[] image = countryService.getImage(name);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
        } catch (FileNotFoundException e) {
            log.warn("Not found image with name: " + name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
