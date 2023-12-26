package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EffectsTestSamples.*;
import static com.mycompany.myapp.domain.MaterialSideEffectsTestSamples.*;
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

        platingMaterial.removeEffects(effectsBack);
        assertThat(platingMaterial.getEffects()).doesNotContain(effectsBack);

        platingMaterial.effects(new HashSet<>(Set.of(effectsBack)));
        assertThat(platingMaterial.getEffects()).containsOnly(effectsBack);

        platingMaterial.setEffects(new HashSet<>());
        assertThat(platingMaterial.getEffects()).doesNotContain(effectsBack);
    }

    @Test
    void materialsideeffectsTest() throws Exception {
        PlatingMaterial platingMaterial = getPlatingMaterialRandomSampleGenerator();
        MaterialSideEffects materialSideEffectsBack = getMaterialSideEffectsRandomSampleGenerator();

        platingMaterial.addMaterialsideeffects(materialSideEffectsBack);
        assertThat(platingMaterial.getMaterialsideeffects()).containsOnly(materialSideEffectsBack);

        platingMaterial.removeMaterialsideeffects(materialSideEffectsBack);
        assertThat(platingMaterial.getMaterialsideeffects()).doesNotContain(materialSideEffectsBack);

        platingMaterial.materialsideeffects(new HashSet<>(Set.of(materialSideEffectsBack)));
        assertThat(platingMaterial.getMaterialsideeffects()).containsOnly(materialSideEffectsBack);

        platingMaterial.setMaterialsideeffects(new HashSet<>());
        assertThat(platingMaterial.getMaterialsideeffects()).doesNotContain(materialSideEffectsBack);
    }
}
