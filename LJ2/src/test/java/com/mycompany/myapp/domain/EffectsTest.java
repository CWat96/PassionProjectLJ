package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EffectsTestSamples.*;
import static com.mycompany.myapp.domain.PlatingMaterialTestSamples.*;
import static com.mycompany.myapp.domain.StoneGemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
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
    void platingMaterialTest() throws Exception {
        Effects effects = getEffectsRandomSampleGenerator();
        PlatingMaterial platingMaterialBack = getPlatingMaterialRandomSampleGenerator();

        effects.setPlatingMaterial(platingMaterialBack);
        assertThat(effects.getPlatingMaterial()).isEqualTo(platingMaterialBack);

        effects.platingMaterial(null);
        assertThat(effects.getPlatingMaterial()).isNull();
    }

    @Test
    void stoneGemTest() throws Exception {
        Effects effects = getEffectsRandomSampleGenerator();
        StoneGem stoneGemBack = getStoneGemRandomSampleGenerator();

        effects.setStoneGem(stoneGemBack);
        assertThat(effects.getStoneGem()).isEqualTo(stoneGemBack);

        effects.stoneGem(null);
        assertThat(effects.getStoneGem()).isNull();
    }
}
