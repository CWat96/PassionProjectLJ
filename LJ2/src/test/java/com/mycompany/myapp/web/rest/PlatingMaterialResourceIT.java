package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PlatingMaterial;
import com.mycompany.myapp.repository.PlatingMaterialRepository;
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
 * Integration tests for the {@link PlatingMaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlatingMaterialResourceIT {

    private static final String DEFAULT_PLATING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLATING_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plating-materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlatingMaterialRepository platingMaterialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlatingMaterialMockMvc;

    private PlatingMaterial platingMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlatingMaterial createEntity(EntityManager em) {
        PlatingMaterial platingMaterial = new PlatingMaterial().platingName(DEFAULT_PLATING_NAME);
        return platingMaterial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlatingMaterial createUpdatedEntity(EntityManager em) {
        PlatingMaterial platingMaterial = new PlatingMaterial().platingName(UPDATED_PLATING_NAME);
        return platingMaterial;
    }

    @BeforeEach
    public void initTest() {
        platingMaterial = createEntity(em);
    }

    @Test
    @Transactional
    void createPlatingMaterial() throws Exception {
        int databaseSizeBeforeCreate = platingMaterialRepository.findAll().size();
        // Create the PlatingMaterial
        restPlatingMaterialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isCreated());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        PlatingMaterial testPlatingMaterial = platingMaterialList.get(platingMaterialList.size() - 1);
        assertThat(testPlatingMaterial.getPlatingName()).isEqualTo(DEFAULT_PLATING_NAME);
    }

    @Test
    @Transactional
    void createPlatingMaterialWithExistingId() throws Exception {
        // Create the PlatingMaterial with an existing ID
        platingMaterial.setId(1L);

        int databaseSizeBeforeCreate = platingMaterialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlatingMaterialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlatingMaterials() throws Exception {
        // Initialize the database
        platingMaterialRepository.saveAndFlush(platingMaterial);

        // Get all the platingMaterialList
        restPlatingMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(platingMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].platingName").value(hasItem(DEFAULT_PLATING_NAME)));
    }

    @Test
    @Transactional
    void getPlatingMaterial() throws Exception {
        // Initialize the database
        platingMaterialRepository.saveAndFlush(platingMaterial);

        // Get the platingMaterial
        restPlatingMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, platingMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(platingMaterial.getId().intValue()))
            .andExpect(jsonPath("$.platingName").value(DEFAULT_PLATING_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPlatingMaterial() throws Exception {
        // Get the platingMaterial
        restPlatingMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlatingMaterial() throws Exception {
        // Initialize the database
        platingMaterialRepository.saveAndFlush(platingMaterial);

        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();

        // Update the platingMaterial
        PlatingMaterial updatedPlatingMaterial = platingMaterialRepository.findById(platingMaterial.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlatingMaterial are not directly saved in db
        em.detach(updatedPlatingMaterial);
        updatedPlatingMaterial.platingName(UPDATED_PLATING_NAME);

        restPlatingMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlatingMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlatingMaterial))
            )
            .andExpect(status().isOk());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
        PlatingMaterial testPlatingMaterial = platingMaterialList.get(platingMaterialList.size() - 1);
        assertThat(testPlatingMaterial.getPlatingName()).isEqualTo(UPDATED_PLATING_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPlatingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();
        platingMaterial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatingMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, platingMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlatingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();
        platingMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatingMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlatingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();
        platingMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatingMaterialMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlatingMaterialWithPatch() throws Exception {
        // Initialize the database
        platingMaterialRepository.saveAndFlush(platingMaterial);

        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();

        // Update the platingMaterial using partial update
        PlatingMaterial partialUpdatedPlatingMaterial = new PlatingMaterial();
        partialUpdatedPlatingMaterial.setId(platingMaterial.getId());

        restPlatingMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlatingMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlatingMaterial))
            )
            .andExpect(status().isOk());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
        PlatingMaterial testPlatingMaterial = platingMaterialList.get(platingMaterialList.size() - 1);
        assertThat(testPlatingMaterial.getPlatingName()).isEqualTo(DEFAULT_PLATING_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePlatingMaterialWithPatch() throws Exception {
        // Initialize the database
        platingMaterialRepository.saveAndFlush(platingMaterial);

        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();

        // Update the platingMaterial using partial update
        PlatingMaterial partialUpdatedPlatingMaterial = new PlatingMaterial();
        partialUpdatedPlatingMaterial.setId(platingMaterial.getId());

        partialUpdatedPlatingMaterial.platingName(UPDATED_PLATING_NAME);

        restPlatingMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlatingMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlatingMaterial))
            )
            .andExpect(status().isOk());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
        PlatingMaterial testPlatingMaterial = platingMaterialList.get(platingMaterialList.size() - 1);
        assertThat(testPlatingMaterial.getPlatingName()).isEqualTo(UPDATED_PLATING_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPlatingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();
        platingMaterial.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatingMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, platingMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlatingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();
        platingMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatingMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlatingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = platingMaterialRepository.findAll().size();
        platingMaterial.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatingMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platingMaterial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlatingMaterial in the database
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlatingMaterial() throws Exception {
        // Initialize the database
        platingMaterialRepository.saveAndFlush(platingMaterial);

        int databaseSizeBeforeDelete = platingMaterialRepository.findAll().size();

        // Delete the platingMaterial
        restPlatingMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, platingMaterial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlatingMaterial> platingMaterialList = platingMaterialRepository.findAll();
        assertThat(platingMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
