package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "effects")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Effects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "effect")
    private String effect;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_effects__effects",
        joinColumns = @JoinColumn(name = "effects_id"),
        inverseJoinColumns = @JoinColumn(name = "effects_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "platingMaterials", "effects" }, allowSetters = true)
    private Set<MaterialSideEffects> effects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "effects")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "effects", "materialsideeffects" }, allowSetters = true)
    private Set<PlatingMaterial> platingMaterials = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "effects")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "effects" }, allowSetters = true)
    private Set<StoneGem> stoneGems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Effects id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffect() {
        return this.effect;
    }

    public Effects effect(String effect) {
        this.setEffect(effect);
        return this;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Set<MaterialSideEffects> getEffects() {
        return this.effects;
    }

    public void setEffects(Set<MaterialSideEffects> materialSideEffects) {
        this.effects = materialSideEffects;
    }

    public Effects effects(Set<MaterialSideEffects> materialSideEffects) {
        this.setEffects(materialSideEffects);
        return this;
    }

    public Effects addEffects(MaterialSideEffects materialSideEffects) {
        this.effects.add(materialSideEffects);
        return this;
    }

    public Effects removeEffects(MaterialSideEffects materialSideEffects) {
        this.effects.remove(materialSideEffects);
        return this;
    }

    public Set<PlatingMaterial> getPlatingMaterials() {
        return this.platingMaterials;
    }

    public void setPlatingMaterials(Set<PlatingMaterial> platingMaterials) {
        if (this.platingMaterials != null) {
            this.platingMaterials.forEach(i -> i.removeEffects(this));
        }
        if (platingMaterials != null) {
            platingMaterials.forEach(i -> i.addEffects(this));
        }
        this.platingMaterials = platingMaterials;
    }

    public Effects platingMaterials(Set<PlatingMaterial> platingMaterials) {
        this.setPlatingMaterials(platingMaterials);
        return this;
    }

    public Effects addPlatingMaterial(PlatingMaterial platingMaterial) {
        this.platingMaterials.add(platingMaterial);
        platingMaterial.getEffects().add(this);
        return this;
    }

    public Effects removePlatingMaterial(PlatingMaterial platingMaterial) {
        this.platingMaterials.remove(platingMaterial);
        platingMaterial.getEffects().remove(this);
        return this;
    }

    public Set<StoneGem> getStoneGems() {
        return this.stoneGems;
    }

    public void setStoneGems(Set<StoneGem> stoneGems) {
        if (this.stoneGems != null) {
            this.stoneGems.forEach(i -> i.removeEffects(this));
        }
        if (stoneGems != null) {
            stoneGems.forEach(i -> i.addEffects(this));
        }
        this.stoneGems = stoneGems;
    }

    public Effects stoneGems(Set<StoneGem> stoneGems) {
        this.setStoneGems(stoneGems);
        return this;
    }

    public Effects addStoneGem(StoneGem stoneGem) {
        this.stoneGems.add(stoneGem);
        stoneGem.getEffects().add(this);
        return this;
    }

    public Effects removeStoneGem(StoneGem stoneGem) {
        this.stoneGems.remove(stoneGem);
        stoneGem.getEffects().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Effects)) {
            return false;
        }
        return getId() != null && getId().equals(((Effects) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Effects{" +
            "id=" + getId() +
            ", effect='" + getEffect() + "'" +
            "}";
    }
}
