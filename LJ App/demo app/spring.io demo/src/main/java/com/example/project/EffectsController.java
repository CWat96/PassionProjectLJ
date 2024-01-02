package com.example.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EffectsController {
    private EffectsService service;

    @Autowired
    public EffectsController(EffectsService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Effects> create(@RequestBody Effects effects) {
        return new ResponseEntity<>(service.create(effects), HttpStatus.CREATED);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<Effects> readById(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/readAll")
    public ResponseEntity<List<Effects>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Effects> updateById(
            @PathVariable Long id,
            @RequestBody Effects newData) {
        return new ResponseEntity<>(service.update(id, newData), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Effects> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
    }
}
