package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlatingMaterial.
 */
@Entity
@Table(name = "plating_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlatingMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plating_name")
    private String platingName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_plating_material__effects",
        joinColumns = @JoinColumn(name = "plating_material_id"),
        inverseJoinColumns = @JoinColumn(name = "effects_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "effects", "platingMaterials", "stoneGems" }, allowSetters = true)
    private Set<Effects> effects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_plating_material__materialsideeffects",
        joinColumns = @JoinColumn(name = "plating_material_id"),
        inverseJoinColumns = @JoinColumn(name = "materialsideeffects_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "platingMaterials", "effects" }, allowSetters = true)
    private Set<MaterialSideEffects> materialsideeffects = new HashSet<>();

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
        this.effects = effects;
    }

    public PlatingMaterial effects(Set<Effects> effects) {
        this.setEffects(effects);
        return this;
    }

    public PlatingMaterial addEffects(Effects effects) {
        this.effects.add(effects);
        return this;
    }

    public PlatingMaterial removeEffects(Effects effects) {
        this.effects.remove(effects);
        return this;
    }

    public Set<MaterialSideEffects> getMaterialsideeffects() {
        return this.materialsideeffects;
    }

    public void setMaterialsideeffects(Set<MaterialSideEffects> materialSideEffects) {
        this.materialsideeffects = materialSideEffects;
    }

    public PlatingMaterial materialsideeffects(Set<MaterialSideEffects> materialSideEffects) {
        this.setMaterialsideeffects(materialSideEffects);
        return this;
    }

    public PlatingMaterial addMaterialsideeffects(MaterialSideEffects materialSideEffects) {
        this.materialsideeffects.add(materialSideEffects);
        return this;
    }

    public PlatingMaterial removeMaterialsideeffects(MaterialSideEffects materialSideEffects) {
        this.materialsideeffects.remove(materialSideEffects);
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
