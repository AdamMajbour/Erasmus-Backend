package com.example.erasmus_app.controllers;

import com.example.erasmus_app.Exceptions.AlreadyExistException;
import com.example.erasmus_app.models.Country;
import com.example.erasmus_app.payload.response.CountryUploadResponse;
import com.example.erasmus_app.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<CountryUploadResponse> addCountry(@RequestParam("image") MultipartFile file,
                                                            @PathVariable String countryName,
                                                            @RequestParam("description") String description) {
        try {
            CountryUploadResponse response = countryService.addCountry(file, countryName, description);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AlreadyExistException e) {
            log.warn(e.getMessage());
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
    public ResponseEntity<byte[]> getCountryImage(@PathVariable String name){
        try {
            byte[] image = countryService.getImage(name);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn("Not found country with name: " + name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
