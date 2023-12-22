package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A StoneGem.
 */
@Entity
@Table(name = "stone_gem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StoneGem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stone_gem_name")
    private String stoneGemName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stoneGem")
    @JsonIgnoreProperties(value = { "platingMaterial", "stoneGem" }, allowSetters = true)
    private Set<Effects> effects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StoneGem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoneGemName() {
        return this.stoneGemName;
    }

    public StoneGem stoneGemName(String stoneGemName) {
        this.setStoneGemName(stoneGemName);
        return this;
    }

    public void setStoneGemName(String stoneGemName) {
        this.stoneGemName = stoneGemName;
    }

    public Set<Effects> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<Effects> effects) {
        if (this.effects != null) {
            this.effects.forEach(i -> i.setStoneGem(null));
        }
        if (effects != null) {
            effects.forEach(i -> i.setStoneGem(this));
        }
        this.effects = effects;
    }

    public StoneGem effects(Set<Effects> effects) {
        this.setEffects(effects);
        return this;
    }

    public StoneGem addEffects(Effects effects) {
        this.effects.add(effects);
        effects.setStoneGem(this);
        return this;
    }

    public StoneGem removeEffects(Effects effects) {
        this.effects.remove(effects);
        effects.setStoneGem(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoneGem)) {
            return false;
        }
        return getId() != null && getId().equals(((StoneGem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoneGem{" +
            "id=" + getId() +
            ", stoneGemName='" + getStoneGemName() + "'" +
            "}";
    }
}
