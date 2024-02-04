package com.example.project.service;

import com.example.project.domain.Effects;
import com.example.project.repository.EffectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EffectsService {
    private EffectsRepository repository;

    @Autowired
    public EffectsService(EffectsRepository repository) {
        this.repository = repository;
    }

    public Effects create(Effects effects) {
        return repository.save(effects);
    }

    public Effects readById(Long id) {
        return repository.findById(id).get();
    }

    public List<Effects> readAll() {
        Iterable<Effects> allEffects = repository.findAll();
        List<Effects> effectsList = new ArrayList<>();
        allEffects.forEach(effectsList::add);
        return effectsList;
    }

    public Effects update(Long id, Effects newEffectsData) {
        Effects effectsInDatabase = this.readById(id);
        effectsInDatabase.setEffectsName(newEffectsData.getEffectsName());
        effectsInDatabase = repository.save(newEffectsData);
        return effectsInDatabase;
    }

    public Effects deleteById(Long id) {
        Effects effectsToBeDeleted = this.readById(id);
        repository.delete(effectsToBeDeleted);
        return effectsToBeDeleted;
    }
}
