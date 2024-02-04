package com.example.project.controller;

import com.example.project.domain.Effects;
import com.example.project.service.EffectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/effects")
public class EffectsController {
    private final EffectsService effectsService;

    @Autowired
    public EffectsController(EffectsService effectsService) {
        this.effectsService = effectsService;
    }

    // Endpoints for Effects CRUD operations
    @PostMapping(value = "/create")
    public ResponseEntity<Effects> createEffects(@RequestBody Effects effects) {
        Effects createEffects = effectsService.create(effects);
        return new ResponseEntity<>(createEffects, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Effects> getEffectsById(@PathVariable("id") Long id) {
        Effects effects = effectsService.readById(id);
        return new ResponseEntity<>(effects, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Effects>> getAllEffects() {
        List<Effects> effectsList = effectsService.readAll();
        return new ResponseEntity<>(effectsList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Effects> updateEffects(@PathVariable("id") Long id, @RequestBody Effects newEffectsData) {
        Effects updatedEffects = effectsService.update(id, newEffectsData);
        return new ResponseEntity<>(updatedEffects, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Effects> deleteEffects(@PathVariable("id") Long id) {
        Effects deletedEffects = effectsService.deleteById(id);
        return new ResponseEntity<>(deletedEffects, HttpStatus.OK);
    }

}
