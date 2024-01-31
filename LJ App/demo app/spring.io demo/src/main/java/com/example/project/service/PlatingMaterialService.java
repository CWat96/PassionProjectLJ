package com.example.project.service;

import com.example.project.domain.PlatingMaterial;
import com.example.project.repository.PlatingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatingMaterialService {
    private PlatingMaterialRepository repository;

    @Autowired
    public PlatingMaterialService(PlatingMaterialRepository repository) {
        this.repository = repository;
    }

    public PlatingMaterial create(PlatingMaterial platingMaterial) {
        return repository.save(platingMaterial);
    }

    public PlatingMaterial readById(Long id) {
        return repository.findById(id).get();
    }

    public List<PlatingMaterial> readAll() {
        Iterable<PlatingMaterial> allPlating = repository.findAll();
        List<PlatingMaterial> platingList = new ArrayList<>();
        allPlating.forEach(platingList::add);
        return platingList;
    }

    public PlatingMaterial update(Long id, PlatingMaterial newPlatingData) {
        PlatingMaterial platingInDatabase = this.readById(id);
        platingInDatabase.setPlatingName(newPlatingData.getPlatingName());
        platingInDatabase = repository.save(platingInDatabase);
        return platingInDatabase;
    }

    public PlatingMaterial deleteById(Long id) {
        PlatingMaterial platingToBeDeleted = this.readById(id);
        repository.delete(platingToBeDeleted);
        return platingToBeDeleted;
    }
}