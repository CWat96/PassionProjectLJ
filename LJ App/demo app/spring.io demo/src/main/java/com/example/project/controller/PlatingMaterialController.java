package com.example.project.controller;

import com.example.project.domain.PlatingMaterial;
import com.example.project.service.PlatingMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/platingMaterial")
public class PlatingMaterialController {
    private PlatingMaterialService service;

    @Autowired
    public PlatingMaterialController(PlatingMaterialService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PlatingMaterial> create(@Validated @RequestBody PlatingMaterial platingMaterial) {
        return new ResponseEntity<>(service.create(platingMaterial), HttpStatus.CREATED);
    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<PlatingMaterial> readById(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/readAll")
    public ResponseEntity<List<PlatingMaterial>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<PlatingMaterial> updateById(
            @PathVariable Long id,
            @RequestBody PlatingMaterial newData) {
        return new ResponseEntity<>(service.update(id, newData), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<PlatingMaterial> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
    }
}
