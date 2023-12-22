package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EffectsTestSamples.*;
import static com.mycompany.myapp.domain.PlatingMaterialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlatingMaterialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlatingMaterial.class);
        PlatingMaterial platingMaterial1 = getPlatingMaterialSample1();
        PlatingMaterial platingMaterial2 = new PlatingMaterial();
        assertThat(platingMaterial1).isNotEqualTo(platingMaterial2);

        platingMaterial2.setId(platingMaterial1.getId());
        assertThat(platingMaterial1).isEqualTo(platingMaterial2);

        platingMaterial2 = getPlatingMaterialSample2();
        assertThat(platingMaterial1).isNotEqualTo(platingMaterial2);
    }

    @Test
    void effectsTest() throws Exception {
        PlatingMaterial platingMaterial = getPlatingMaterialRandomSampleGenerator();
        Effects effectsBack = getEffectsRandomSampleGenerator();

        platingMaterial.addEffects(effectsBack);
        assertThat(platingMaterial.getEffects()).containsOnly(effectsBack);
        assertThat(effectsBack.getPlatingMaterial()).isEqualTo(platingMaterial);

        platingMaterial.removeEffects(effectsBack);
        assertThat(platingMaterial.getEffects()).doesNotContain(effectsBack);
        assertThat(effectsBack.getPlatingMaterial()).isNull();

        platingMaterial.effects(new HashSet<>(Set.of(effectsBack)));
        assertThat(platingMaterial.getEffects()).containsOnly(effectsBack);
        assertThat(effectsBack.getPlatingMaterial()).isEqualTo(platingMaterial);

        platingMaterial.setEffects(new HashSet<>());
        assertThat(platingMaterial.getEffects()).doesNotContain(effectsBack);
        assertThat(effectsBack.getPlatingMaterial()).isNull();
    }
}
