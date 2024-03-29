package com.example.project.domain;

import com.example.project.domain.Effects;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class StoneGem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String stoneGemName;

    @ManyToMany
    @JoinTable(
            name = "stone_gem_effects",
            joinColumns = @JoinColumn(name = "stone_gem_id"),
            inverseJoinColumns = @JoinColumn(name = "effects_id")
    )
    private List<Effects> effects;


    // created relationship

    public StoneGem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoneGemName() {
        return stoneGemName;
    }

    public void setStoneGemName(String stoneGemName) {
        this.stoneGemName = stoneGemName;
    }
}
