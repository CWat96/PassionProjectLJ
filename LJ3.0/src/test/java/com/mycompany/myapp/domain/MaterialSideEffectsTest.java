package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EffectsTestSamples.*;
import static com.mycompany.myapp.domain.MaterialSideEffectsTestSamples.*;
import static com.mycompany.myapp.domain.PlatingMaterialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MaterialSideEffectsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialSideEffects.class);
        MaterialSideEffects materialSideEffects1 = getMaterialSideEffectsSample1();
        MaterialSideEffects materialSideEffects2 = new MaterialSideEffects();
        assertThat(materialSideEffects1).isNotEqualTo(materialSideEffects2);

        materialSideEffects2.setId(materialSideEffects1.getId());
        assertThat(materialSideEffects1).isEqualTo(materialSideEffects2);

        materialSideEffects2 = getMaterialSideEffectsSample2();
        assertThat(materialSideEffects1).isNotEqualTo(materialSideEffects2);
    }

    @Test
    void platingMaterialTest() throws Exception {
        MaterialSideEffects materialSideEffects = getMaterialSideEffectsRandomSampleGenerator();
        PlatingMaterial platingMaterialBack = getPlatingMaterialRandomSampleGenerator();

        materialSideEffects.addPlatingMaterial(platingMaterialBack);
        assertThat(materialSideEffects.getPlatingMaterials()).containsOnly(platingMaterialBack);
        assertThat(platingMaterialBack.getMaterialsideeffects()).containsOnly(materialSideEffects);

        materialSideEffects.removePlatingMaterial(platingMaterialBack);
        assertThat(materialSideEffects.getPlatingMaterials()).doesNotContain(platingMaterialBack);
        assertThat(platingMaterialBack.getMaterialsideeffects()).doesNotContain(materialSideEffects);

        materialSideEffects.platingMaterials(new HashSet<>(Set.of(platingMaterialBack)));
        assertThat(materialSideEffects.getPlatingMaterials()).containsOnly(platingMaterialBack);
        assertThat(platingMaterialBack.getMaterialsideeffects()).containsOnly(materialSideEffects);

        materialSideEffects.setPlatingMaterials(new HashSet<>());
        assertThat(materialSideEffects.getPlatingMaterials()).doesNotContain(platingMaterialBack);
        assertThat(platingMaterialBack.getMaterialsideeffects()).doesNotContain(materialSideEffects);
    }

    @Test
    void effectsTest() throws Exception {
        MaterialSideEffects materialSideEffects = getMaterialSideEffectsRandomSampleGenerator();
        Effects effectsBack = getEffectsRandomSampleGenerator();

        materialSideEffects.addEffects(effectsBack);
        assertThat(materialSideEffects.getEffects()).containsOnly(effectsBack);
        assertThat(effectsBack.getEffects()).containsOnly(materialSideEffects);

        materialSideEffects.removeEffects(effectsBack);
        assertThat(materialSideEffects.getEffects()).doesNotContain(effectsBack);
        assertThat(effectsBack.getEffects()).doesNotContain(materialSideEffects);

        materialSideEffects.effects(new HashSet<>(Set.of(effectsBack)));
        assertThat(materialSideEffects.getEffects()).containsOnly(effectsBack);
        assertThat(effectsBack.getEffects()).containsOnly(materialSideEffects);

        materialSideEffects.setEffects(new HashSet<>());
        assertThat(materialSideEffects.getEffects()).doesNotContain(effectsBack);
        assertThat(effectsBack.getEffects()).doesNotContain(materialSideEffects);
    }
}
