package com.example.erasmus_app.controllers;

import com.example.erasmus_app.models.City;
import com.example.erasmus_app.models.Institution;
import com.example.erasmus_app.services.InstitutionService;
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
@RequestMapping("/institution")
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class InstitutionController {
    private final InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @PostMapping
    public ResponseEntity<String> addInstitution(@RequestParam("name") String name,
                                                 @RequestParam("country") String country,
                                                 @RequestParam("city") String city,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("image") MultipartFile file) {
        try {
            return new ResponseEntity<>(institutionService.addInstitution(name, country, city, description, file),
                    HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Institution>> getAllInstitutions(){
        return ResponseEntity.status(HttpStatus.OK).body(institutionService.getAllInstitutions());
    }

    @GetMapping("/image/{name}/{country}/{city}")
    public ResponseEntity<byte[]> getInstitutionImage(@PathVariable String name, @PathVariable String country,
                                                      @PathVariable String city) {
        try {
            byte[] image = institutionService.getImage(name, country, city);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn("Not found institution with name: " + name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
