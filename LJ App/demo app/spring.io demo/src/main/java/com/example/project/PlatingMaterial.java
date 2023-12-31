package com.example.project;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class PlatingMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;
    private String platingName;

    @ManyToMany
    @JoinTable(
            name = "plating_material_effects",
            joinColumns = @JoinColumn(name = "plating_material_id"),
            inverseJoinColumns = @JoinColumn(name = "effects_id")
    )
    private Set<Effects> effects;
    // created relationship


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


