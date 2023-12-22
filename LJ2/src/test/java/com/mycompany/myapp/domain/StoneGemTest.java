package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EffectsTestSamples.*;
import static com.mycompany.myapp.domain.StoneGemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StoneGemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoneGem.class);
        StoneGem stoneGem1 = getStoneGemSample1();
        StoneGem stoneGem2 = new StoneGem();
        assertThat(stoneGem1).isNotEqualTo(stoneGem2);

        stoneGem2.setId(stoneGem1.getId());
        assertThat(stoneGem1).isEqualTo(stoneGem2);

        stoneGem2 = getStoneGemSample2();
        assertThat(stoneGem1).isNotEqualTo(stoneGem2);
    }

    @Test
    void effectsTest() throws Exception {
        StoneGem stoneGem = getStoneGemRandomSampleGenerator();
        Effects effectsBack = getEffectsRandomSampleGenerator();

        stoneGem.addEffects(effectsBack);
        assertThat(stoneGem.getEffects()).containsOnly(effectsBack);
        assertThat(effectsBack.getStoneGem()).isEqualTo(stoneGem);

        stoneGem.removeEffects(effectsBack);
        assertThat(stoneGem.getEffects()).doesNotContain(effectsBack);
        assertThat(effectsBack.getStoneGem()).isNull();

        stoneGem.effects(new HashSet<>(Set.of(effectsBack)));
        assertThat(stoneGem.getEffects()).containsOnly(effectsBack);
        assertThat(effectsBack.getStoneGem()).isEqualTo(stoneGem);

        stoneGem.setEffects(new HashSet<>());
        assertThat(stoneGem.getEffects()).doesNotContain(effectsBack);
        assertThat(effectsBack.getStoneGem()).isNull();
    }
}
