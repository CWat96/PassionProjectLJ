package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "effects")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Effects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "effect")
    private String effect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "effects" }, allowSetters = true)
    private PlatingMaterial platingMaterial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "effects" }, allowSetters = true)
    private StoneGem stoneGem;

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

    public PlatingMaterial getPlatingMaterial() {
        return this.platingMaterial;
    }

    public void setPlatingMaterial(PlatingMaterial platingMaterial) {
        this.platingMaterial = platingMaterial;
    }

    public Effects platingMaterial(PlatingMaterial platingMaterial) {
        this.setPlatingMaterial(platingMaterial);
        return this;
    }

    public StoneGem getStoneGem() {
        return this.stoneGem;
    }

    public void setStoneGem(StoneGem stoneGem) {
        this.stoneGem = stoneGem;
    }

    public Effects stoneGem(StoneGem stoneGem) {
        this.setStoneGem(stoneGem);
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
