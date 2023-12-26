package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MaterialSideEffects.
 */
@Entity
@Table(name = "material_side_effects")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaterialSideEffects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "material_side_effects_name")
    private String materialSideEffectsName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "materialsideeffects")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "effects", "materialsideeffects" }, allowSetters = true)
    private Set<PlatingMaterial> platingMaterials = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "effects")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "effects", "platingMaterials", "stoneGems" }, allowSetters = true)
    private Set<Effects> effects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaterialSideEffects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialSideEffectsName() {
        return this.materialSideEffectsName;
    }

    public MaterialSideEffects materialSideEffectsName(String materialSideEffectsName) {
        this.setMaterialSideEffectsName(materialSideEffectsName);
        return this;
    }

    public void setMaterialSideEffectsName(String materialSideEffectsName) {
        this.materialSideEffectsName = materialSideEffectsName;
    }

    public Set<PlatingMaterial> getPlatingMaterials() {
        return this.platingMaterials;
    }

    public void setPlatingMaterials(Set<PlatingMaterial> platingMaterials) {
        if (this.platingMaterials != null) {
            this.platingMaterials.forEach(i -> i.removeMaterialsideeffects(this));
        }
        if (platingMaterials != null) {
            platingMaterials.forEach(i -> i.addMaterialsideeffects(this));
        }
        this.platingMaterials = platingMaterials;
    }

    public MaterialSideEffects platingMaterials(Set<PlatingMaterial> platingMaterials) {
        this.setPlatingMaterials(platingMaterials);
        return this;
    }

    public MaterialSideEffects addPlatingMaterial(PlatingMaterial platingMaterial) {
        this.platingMaterials.add(platingMaterial);
        platingMaterial.getMaterialsideeffects().add(this);
        return this;
    }

    public MaterialSideEffects removePlatingMaterial(PlatingMaterial platingMaterial) {
        this.platingMaterials.remove(platingMaterial);
        platingMaterial.getMaterialsideeffects().remove(this);
        return this;
    }

    public Set<Effects> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<Effects> effects) {
        if (this.effects != null) {
            this.effects.forEach(i -> i.removeEffects(this));
        }
        if (effects != null) {
            effects.forEach(i -> i.addEffects(this));
        }
        this.effects = effects;
    }

    public MaterialSideEffects effects(Set<Effects> effects) {
        this.setEffects(effects);
        return this;
    }

    public MaterialSideEffects addEffects(Effects effects) {
        this.effects.add(effects);
        effects.getEffects().add(this);
        return this;
    }

    public MaterialSideEffects removeEffects(Effects effects) {
        this.effects.remove(effects);
        effects.getEffects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialSideEffects)) {
            return false;
        }
        return getId() != null && getId().equals(((MaterialSideEffects) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialSideEffects{" +
            "id=" + getId() +
            ", materialSideEffectsName='" + getMaterialSideEffectsName() + "'" +
            "}";
    }
}
