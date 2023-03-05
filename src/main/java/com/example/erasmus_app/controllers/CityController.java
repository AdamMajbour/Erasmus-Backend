package com.example.erasmus_app.controllers;

import com.example.erasmus_app.models.City;
import com.example.erasmus_app.services.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/city")
@Slf4j
@CrossOrigin("http://localhost:3000/")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<String> addCity(@RequestParam("name") String name, @RequestParam("country") String country,
                                          @RequestParam("description") String description,
                                          @RequestParam("image") MultipartFile file) {
        try {
            return new ResponseEntity<>(cityService.addCity(name, country, description, file), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(){
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllCities());
    }

    @GetMapping("/image/{name}/{country}")
    public ResponseEntity<byte[]> getCityImage(@PathVariable String name, @PathVariable String country) {
        try {
            byte[] image = cityService.getImage(name, country);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn("Not found city with name: " + name);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
