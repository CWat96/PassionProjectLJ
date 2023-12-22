package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.StoneGem;
import com.mycompany.myapp.repository.StoneGemRepository;
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
 * Integration tests for the {@link StoneGemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoneGemResourceIT {

    private static final String DEFAULT_STONE_GEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STONE_GEM_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stone-gems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoneGemRepository stoneGemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoneGemMockMvc;

    private StoneGem stoneGem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoneGem createEntity(EntityManager em) {
        StoneGem stoneGem = new StoneGem().stoneGemName(DEFAULT_STONE_GEM_NAME);
        return stoneGem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoneGem createUpdatedEntity(EntityManager em) {
        StoneGem stoneGem = new StoneGem().stoneGemName(UPDATED_STONE_GEM_NAME);
        return stoneGem;
    }

    @BeforeEach
    public void initTest() {
        stoneGem = createEntity(em);
    }

    @Test
    @Transactional
    void createStoneGem() throws Exception {
        int databaseSizeBeforeCreate = stoneGemRepository.findAll().size();
        // Create the StoneGem
        restStoneGemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoneGem)))
            .andExpect(status().isCreated());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeCreate + 1);
        StoneGem testStoneGem = stoneGemList.get(stoneGemList.size() - 1);
        assertThat(testStoneGem.getStoneGemName()).isEqualTo(DEFAULT_STONE_GEM_NAME);
    }

    @Test
    @Transactional
    void createStoneGemWithExistingId() throws Exception {
        // Create the StoneGem with an existing ID
        stoneGem.setId(1L);

        int databaseSizeBeforeCreate = stoneGemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoneGemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoneGem)))
            .andExpect(status().isBadRequest());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStoneGems() throws Exception {
        // Initialize the database
        stoneGemRepository.saveAndFlush(stoneGem);

        // Get all the stoneGemList
        restStoneGemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stoneGem.getId().intValue())))
            .andExpect(jsonPath("$.[*].stoneGemName").value(hasItem(DEFAULT_STONE_GEM_NAME)));
    }

    @Test
    @Transactional
    void getStoneGem() throws Exception {
        // Initialize the database
        stoneGemRepository.saveAndFlush(stoneGem);

        // Get the stoneGem
        restStoneGemMockMvc
            .perform(get(ENTITY_API_URL_ID, stoneGem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stoneGem.getId().intValue()))
            .andExpect(jsonPath("$.stoneGemName").value(DEFAULT_STONE_GEM_NAME));
    }

    @Test
    @Transactional
    void getNonExistingStoneGem() throws Exception {
        // Get the stoneGem
        restStoneGemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStoneGem() throws Exception {
        // Initialize the database
        stoneGemRepository.saveAndFlush(stoneGem);

        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();

        // Update the stoneGem
        StoneGem updatedStoneGem = stoneGemRepository.findById(stoneGem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStoneGem are not directly saved in db
        em.detach(updatedStoneGem);
        updatedStoneGem.stoneGemName(UPDATED_STONE_GEM_NAME);

        restStoneGemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStoneGem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStoneGem))
            )
            .andExpect(status().isOk());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
        StoneGem testStoneGem = stoneGemList.get(stoneGemList.size() - 1);
        assertThat(testStoneGem.getStoneGemName()).isEqualTo(UPDATED_STONE_GEM_NAME);
    }

    @Test
    @Transactional
    void putNonExistingStoneGem() throws Exception {
        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();
        stoneGem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoneGemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stoneGem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoneGem))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStoneGem() throws Exception {
        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();
        stoneGem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoneGemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoneGem))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStoneGem() throws Exception {
        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();
        stoneGem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoneGemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoneGem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoneGemWithPatch() throws Exception {
        // Initialize the database
        stoneGemRepository.saveAndFlush(stoneGem);

        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();

        // Update the stoneGem using partial update
        StoneGem partialUpdatedStoneGem = new StoneGem();
        partialUpdatedStoneGem.setId(stoneGem.getId());

        restStoneGemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoneGem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoneGem))
            )
            .andExpect(status().isOk());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
        StoneGem testStoneGem = stoneGemList.get(stoneGemList.size() - 1);
        assertThat(testStoneGem.getStoneGemName()).isEqualTo(DEFAULT_STONE_GEM_NAME);
    }

    @Test
    @Transactional
    void fullUpdateStoneGemWithPatch() throws Exception {
        // Initialize the database
        stoneGemRepository.saveAndFlush(stoneGem);

        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();

        // Update the stoneGem using partial update
        StoneGem partialUpdatedStoneGem = new StoneGem();
        partialUpdatedStoneGem.setId(stoneGem.getId());

        partialUpdatedStoneGem.stoneGemName(UPDATED_STONE_GEM_NAME);

        restStoneGemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoneGem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoneGem))
            )
            .andExpect(status().isOk());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
        StoneGem testStoneGem = stoneGemList.get(stoneGemList.size() - 1);
        assertThat(testStoneGem.getStoneGemName()).isEqualTo(UPDATED_STONE_GEM_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingStoneGem() throws Exception {
        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();
        stoneGem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoneGemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stoneGem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stoneGem))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStoneGem() throws Exception {
        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();
        stoneGem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoneGemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stoneGem))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStoneGem() throws Exception {
        int databaseSizeBeforeUpdate = stoneGemRepository.findAll().size();
        stoneGem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoneGemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stoneGem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoneGem in the database
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStoneGem() throws Exception {
        // Initialize the database
        stoneGemRepository.saveAndFlush(stoneGem);

        int databaseSizeBeforeDelete = stoneGemRepository.findAll().size();

        // Delete the stoneGem
        restStoneGemMockMvc
            .perform(delete(ENTITY_API_URL_ID, stoneGem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StoneGem> stoneGemList = stoneGemRepository.findAll();
        assertThat(stoneGemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
