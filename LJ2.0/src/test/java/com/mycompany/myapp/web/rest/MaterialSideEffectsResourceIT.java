package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaterialSideEffects;
import com.mycompany.myapp.repository.MaterialSideEffectsRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MaterialSideEffectsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialSideEffectsResourceIT {

    private static final String DEFAULT_MATERIAL_SIDE_EFFECTS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_SIDE_EFFECTS_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/material-side-effects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialSideEffectsRepository materialSideEffectsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialSideEffectsMockMvc;

    private MaterialSideEffects materialSideEffects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialSideEffects createEntity(EntityManager em) {
        MaterialSideEffects materialSideEffects = new MaterialSideEffects().materialSideEffectsName(DEFAULT_MATERIAL_SIDE_EFFECTS_NAME);
        return materialSideEffects;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialSideEffects createUpdatedEntity(EntityManager em) {
        MaterialSideEffects materialSideEffects = new MaterialSideEffects().materialSideEffectsName(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);
        return materialSideEffects;
    }

    @BeforeEach
    public void initTest() {
        materialSideEffects = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterialSideEffects() throws Exception {
        int databaseSizeBeforeCreate = materialSideEffectsRepository.findAll().size();
        // Create the MaterialSideEffects
        restMaterialSideEffectsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isCreated());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialSideEffects testMaterialSideEffects = materialSideEffectsList.get(materialSideEffectsList.size() - 1);
        assertThat(testMaterialSideEffects.getMaterialSideEffectsName()).isEqualTo(DEFAULT_MATERIAL_SIDE_EFFECTS_NAME);
    }

    @Test
    @Transactional
    void createMaterialSideEffectsWithExistingId() throws Exception {
        // Create the MaterialSideEffects with an existing ID
        materialSideEffects.setId(1L);

        int databaseSizeBeforeCreate = materialSideEffectsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialSideEffectsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterialSideEffects() throws Exception {
        // Initialize the database
        materialSideEffectsRepository.saveAndFlush(materialSideEffects);

        // Get all the materialSideEffectsList
        restMaterialSideEffectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialSideEffects.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialSideEffectsName").value(hasItem(DEFAULT_MATERIAL_SIDE_EFFECTS_NAME)));
    }

    @Test
    @Transactional
    void getMaterialSideEffects() throws Exception {
        // Initialize the database
        materialSideEffectsRepository.saveAndFlush(materialSideEffects);

        // Get the materialSideEffects
        restMaterialSideEffectsMockMvc
            .perform(get(ENTITY_API_URL_ID, materialSideEffects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialSideEffects.getId().intValue()))
            .andExpect(jsonPath("$.materialSideEffectsName").value(DEFAULT_MATERIAL_SIDE_EFFECTS_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMaterialSideEffects() throws Exception {
        // Get the materialSideEffects
        restMaterialSideEffectsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterialSideEffects() throws Exception {
        // Initialize the database
        materialSideEffectsRepository.saveAndFlush(materialSideEffects);

        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();

        // Update the materialSideEffects
        MaterialSideEffects updatedMaterialSideEffects = materialSideEffectsRepository.findById(materialSideEffects.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMaterialSideEffects are not directly saved in db
        em.detach(updatedMaterialSideEffects);
        updatedMaterialSideEffects.materialSideEffectsName(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);

        restMaterialSideEffectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterialSideEffects.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterialSideEffects))
            )
            .andExpect(status().isOk());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
        MaterialSideEffects testMaterialSideEffects = materialSideEffectsList.get(materialSideEffectsList.size() - 1);
        assertThat(testMaterialSideEffects.getMaterialSideEffectsName()).isEqualTo(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMaterialSideEffects() throws Exception {
        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();
        materialSideEffects.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialSideEffectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materialSideEffects.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterialSideEffects() throws Exception {
        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();
        materialSideEffects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialSideEffectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterialSideEffects() throws Exception {
        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();
        materialSideEffects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialSideEffectsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialSideEffectsWithPatch() throws Exception {
        // Initialize the database
        materialSideEffectsRepository.saveAndFlush(materialSideEffects);

        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();

        // Update the materialSideEffects using partial update
        MaterialSideEffects partialUpdatedMaterialSideEffects = new MaterialSideEffects();
        partialUpdatedMaterialSideEffects.setId(materialSideEffects.getId());

        partialUpdatedMaterialSideEffects.materialSideEffectsName(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);

        restMaterialSideEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialSideEffects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialSideEffects))
            )
            .andExpect(status().isOk());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
        MaterialSideEffects testMaterialSideEffects = materialSideEffectsList.get(materialSideEffectsList.size() - 1);
        assertThat(testMaterialSideEffects.getMaterialSideEffectsName()).isEqualTo(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMaterialSideEffectsWithPatch() throws Exception {
        // Initialize the database
        materialSideEffectsRepository.saveAndFlush(materialSideEffects);

        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();

        // Update the materialSideEffects using partial update
        MaterialSideEffects partialUpdatedMaterialSideEffects = new MaterialSideEffects();
        partialUpdatedMaterialSideEffects.setId(materialSideEffects.getId());

        partialUpdatedMaterialSideEffects.materialSideEffectsName(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);

        restMaterialSideEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialSideEffects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialSideEffects))
            )
            .andExpect(status().isOk());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
        MaterialSideEffects testMaterialSideEffects = materialSideEffectsList.get(materialSideEffectsList.size() - 1);
        assertThat(testMaterialSideEffects.getMaterialSideEffectsName()).isEqualTo(UPDATED_MATERIAL_SIDE_EFFECTS_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMaterialSideEffects() throws Exception {
        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();
        materialSideEffects.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialSideEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materialSideEffects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterialSideEffects() throws Exception {
        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();
        materialSideEffects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialSideEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterialSideEffects() throws Exception {
        int databaseSizeBeforeUpdate = materialSideEffectsRepository.findAll().size();
        materialSideEffects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialSideEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialSideEffects))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialSideEffects in the database
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterialSideEffects() throws Exception {
        // Initialize the database
        materialSideEffectsRepository.saveAndFlush(materialSideEffects);

        int databaseSizeBeforeDelete = materialSideEffectsRepository.findAll().size();

        // Delete the materialSideEffects
        restMaterialSideEffectsMockMvc
            .perform(delete(ENTITY_API_URL_ID, materialSideEffects.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialSideEffects> materialSideEffectsList = materialSideEffectsRepository.findAll();
        assertThat(materialSideEffectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
