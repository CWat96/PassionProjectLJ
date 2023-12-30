package com.example.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class HealthData {
    @Id
    @GeneratedValue

    private Long id;
    private String platingName;
    private String stoneGemName;
    private String effects;
    private String materialSideEffectsName;

    public HealthData() {
    }
    public HealthData(Long id, String platingName, String stoneGemName, String effects, String materialSideEffectsName) {
        this.id = id;
        this.platingName = platingName;
        this.stoneGemName = stoneGemName;
        this.effects = effects;
        this.materialSideEffectsName = materialSideEffectsName;
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

    public String getStoneGemName() {
        return stoneGemName;
    }

    public void setStoneGemName(String stoneGemName) {
        this.stoneGemName = stoneGemName;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getMaterialSideEffectsName() {
        return materialSideEffectsName;
    }

    public void setMaterialSideEffectsName(String materialSideEffectsName) {
        this.materialSideEffectsName = materialSideEffectsName;
    }
}


