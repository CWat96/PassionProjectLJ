package com.example.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PlatingMaterial {
    @Id
    @GeneratedValue

    private Long id;
    private String platingName;

    public PlatingMaterial() {
    }
    public PlatingMaterial(Long id, String platingName, String stoneGemName, String effects, String materialSideEffectsName) {
        this.id = id;
        this.platingName = platingName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatingName() {
        return platingName;
    }

    public void setPlatingName(String platingName) {
        this.platingName = platingName;
    }
}


