package com.example.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoneGemService {
    private StoneGemRepository repository;

    @Autowired
    public StoneGemService(StoneGemRepository repository) {
        this.repository = repository;
    }

    public StoneGem create(StoneGem stoneGem) {
        return repository.save(stoneGem);
    }

    public StoneGem readById(Long id) {
        return repository.findById(id).get();
    }

    public List<StoneGem> readAll() {
        Iterable<StoneGem> allStoneGem = repository.findAll();
        List<StoneGem> stoneGemList = new ArrayList<>();
        allStoneGem.forEach(stoneGemList::add);
        return stoneGemList;
    }

    public StoneGem update(Long id, StoneGem newStoneGemData) {
        StoneGem stoneGemInDatabase = this.readById(id);
        stoneGemInDatabase.setStoneGemName(newStoneGemData.getStoneGemName());
        stoneGemInDatabase = repository.save(stoneGemInDatabase);
        return stoneGemInDatabase;
    }

    public StoneGem deleteById(Long id) {
        StoneGem stoneGemToBeDeleted = this.readById(id);
        repository.delete(stoneGemToBeDeleted);
        return stoneGemToBeDeleted;
    }
}
