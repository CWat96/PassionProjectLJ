package com.example.project;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Effects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String effectsName;

    @ManyToMany(mappedBy = "effects")
    private Set<PlatingMaterial> platingMaterials;
    // created relationship

    @ManyToMany(mappedBy = "effects")
    private Set<StoneGem> stoneGems;



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
