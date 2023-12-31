package com.example.project;

import jakarta.persistence.*;

@Entity
public class Effects {
    @ManyToMany(cascade = CascadeType.ALL)
    private PlatingMaterial platingMaterial;
    @Id
    @GeneratedValue

    private Long id;
    private String effectsName;


    public Effects() {
    }

    public Effects(Long id, String effectsName) {
        this.id = id;
        this.effectsName = effectsName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffectsName() {
        return effectsName;
    }

    public void setEffectsName(String effectsName) {
        this.effectsName = effectsName;
    }
}
