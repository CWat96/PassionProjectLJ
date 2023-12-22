package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PlatingMaterial.
 */
@Entity
@Table(name = "plating_material")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlatingMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plating_name")
    private String platingName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "platingMaterial")
    @JsonIgnoreProperties(value = { "platingMaterial", "stoneGem" }, allowSetters = true)
    private Set<Effects> effects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlatingMaterial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatingName() {
        return this.platingName;
    }

    public PlatingMaterial platingName(String platingName) {
        this.setPlatingName(platingName);
        return this;
    }

    public void setPlatingName(String platingName) {
        this.platingName = platingName;
    }

    public Set<Effects> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<Effects> effects) {
        if (this.effects != null) {
            this.effects.forEach(i -> i.setPlatingMaterial(null));
        }
        if (effects != null) {
            effects.forEach(i -> i.setPlatingMaterial(this));
        }
        this.effects = effects;
    }

    public PlatingMaterial effects(Set<Effects> effects) {
        this.setEffects(effects);
        return this;
    }

    public PlatingMaterial addEffects(Effects effects) {
        this.effects.add(effects);
        effects.setPlatingMaterial(this);
        return this;
    }

    public PlatingMaterial removeEffects(Effects effects) {
        this.effects.remove(effects);
        effects.setPlatingMaterial(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlatingMaterial)) {
            return false;
        }
        return getId() != null && getId().equals(((PlatingMaterial) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlatingMaterial{" +
            "id=" + getId() +
            ", platingName='" + getPlatingName() + "'" +
            "}";
    }
}
