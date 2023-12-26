package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EffectsTestSamples.*;
import static com.mycompany.myapp.domain.MaterialSideEffectsTestSamples.*;
import static com.mycompany.myapp.domain.PlatingMaterialTestSamples.*;
import static com.mycompany.myapp.domain.StoneGemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EffectsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Effects.class);
        Effects effects1 = getEffectsSample1();
        Effects effects2 = new Effects();
        assertThat(effects1).isNotEqualTo(effects2);

        effects2.setId(effects1.getId());
        assertThat(effects1).isEqualTo(effects2);

        effects2 = getEffectsSample2();
        assertThat(effects1).isNotEqualTo(effects2);
    }

    @Test
    void effectsTest() throws Exception {
        Effects effects = getEffectsRandomSampleGenerator();
        MaterialSideEffects materialSideEffectsBack = getMaterialSideEffectsRandomSampleGenerator();

        effects.addEffects(materialSideEffectsBack);
        assertThat(effects.getEffects()).containsOnly(materialSideEffectsBack);

        effects.removeEffects(materialSideEffectsBack);
        assertThat(effects.getEffects()).doesNotContain(materialSideEffectsBack);

        effects.effects(new HashSet<>(Set.of(materialSideEffectsBack)));
        assertThat(effects.getEffects()).containsOnly(materialSideEffectsBack);

        effects.setEffects(new HashSet<>());
        assertThat(effects.getEffects()).doesNotContain(materialSideEffectsBack);
    }

    @Test
    void platingMaterialTest() throws Exception {
        Effects effects = getEffectsRandomSampleGenerator();
        PlatingMaterial platingMaterialBack = getPlatingMaterialRandomSampleGenerator();

        effects.addPlatingMaterial(platingMaterialBack);
        assertThat(effects.getPlatingMaterials()).containsOnly(platingMaterialBack);
        assertThat(platingMaterialBack.getEffects()).containsOnly(effects);

        effects.removePlatingMaterial(platingMaterialBack);
        assertThat(effects.getPlatingMaterials()).doesNotContain(platingMaterialBack);
        assertThat(platingMaterialBack.getEffects()).doesNotContain(effects);

        effects.platingMaterials(new HashSet<>(Set.of(platingMaterialBack)));
        assertThat(effects.getPlatingMaterials()).containsOnly(platingMaterialBack);
        assertThat(platingMaterialBack.getEffects()).containsOnly(effects);

        effects.setPlatingMaterials(new HashSet<>());
        assertThat(effects.getPlatingMaterials()).doesNotContain(platingMaterialBack);
        assertThat(platingMaterialBack.getEffects()).doesNotContain(effects);
    }

    @Test
    void stoneGemTest() throws Exception {
        Effects effects = getEffectsRandomSampleGenerator();
        StoneGem stoneGemBack = getStoneGemRandomSampleGenerator();

        effects.addStoneGem(stoneGemBack);
        assertThat(effects.getStoneGems()).containsOnly(stoneGemBack);
        assertThat(stoneGemBack.getEffects()).containsOnly(effects);

        effects.removeStoneGem(stoneGemBack);
        assertThat(effects.getStoneGems()).doesNotContain(stoneGemBack);
        assertThat(stoneGemBack.getEffects()).doesNotContain(effects);

        effects.stoneGems(new HashSet<>(Set.of(stoneGemBack)));
        assertThat(effects.getStoneGems()).containsOnly(stoneGemBack);
        assertThat(stoneGemBack.getEffects()).containsOnly(effects);

        effects.setStoneGems(new HashSet<>());
        assertThat(effects.getStoneGems()).doesNotContain(stoneGemBack);
        assertThat(stoneGemBack.getEffects()).doesNotContain(effects);
    }
}
