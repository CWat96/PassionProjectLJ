package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Effects;
import com.mycompany.myapp.repository.EffectsRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EffectsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EffectsResourceIT {

    private static final String DEFAULT_EFFECT = "AAAAAAAAAA";
    private static final String UPDATED_EFFECT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/effects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EffectsRepository effectsRepository;

    @Mock
    private EffectsRepository effectsRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEffectsMockMvc;

    private Effects effects;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Effects createEntity(EntityManager em) {
        Effects effects = new Effects().effect(DEFAULT_EFFECT);
        return effects;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Effects createUpdatedEntity(EntityManager em) {
        Effects effects = new Effects().effect(UPDATED_EFFECT);
        return effects;
    }

    @BeforeEach
    public void initTest() {
        effects = createEntity(em);
    }

    @Test
    @Transactional
    void createEffects() throws Exception {
        int databaseSizeBeforeCreate = effectsRepository.findAll().size();
        // Create the Effects
        restEffectsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effects)))
            .andExpect(status().isCreated());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeCreate + 1);
        Effects testEffects = effectsList.get(effectsList.size() - 1);
        assertThat(testEffects.getEffect()).isEqualTo(DEFAULT_EFFECT);
    }

    @Test
    @Transactional
    void createEffectsWithExistingId() throws Exception {
        // Create the Effects with an existing ID
        effects.setId(1L);

        int databaseSizeBeforeCreate = effectsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEffectsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effects)))
            .andExpect(status().isBadRequest());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEffects() throws Exception {
        // Initialize the database
        effectsRepository.saveAndFlush(effects);

        // Get all the effectsList
        restEffectsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(effects.getId().intValue())))
            .andExpect(jsonPath("$.[*].effect").value(hasItem(DEFAULT_EFFECT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEffectsWithEagerRelationshipsIsEnabled() throws Exception {
        when(effectsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEffectsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(effectsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEffectsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(effectsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEffectsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(effectsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEffects() throws Exception {
        // Initialize the database
        effectsRepository.saveAndFlush(effects);

        // Get the effects
        restEffectsMockMvc
            .perform(get(ENTITY_API_URL_ID, effects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(effects.getId().intValue()))
            .andExpect(jsonPath("$.effect").value(DEFAULT_EFFECT));
    }

    @Test
    @Transactional
    void getNonExistingEffects() throws Exception {
        // Get the effects
        restEffectsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEffects() throws Exception {
        // Initialize the database
        effectsRepository.saveAndFlush(effects);

        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();

        // Update the effects
        Effects updatedEffects = effectsRepository.findById(effects.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEffects are not directly saved in db
        em.detach(updatedEffects);
        updatedEffects.effect(UPDATED_EFFECT);

        restEffectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEffects.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEffects))
            )
            .andExpect(status().isOk());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
        Effects testEffects = effectsList.get(effectsList.size() - 1);
        assertThat(testEffects.getEffect()).isEqualTo(UPDATED_EFFECT);
    }

    @Test
    @Transactional
    void putNonExistingEffects() throws Exception {
        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();
        effects.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, effects.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(effects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEffects() throws Exception {
        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();
        effects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(effects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEffects() throws Exception {
        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();
        effects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(effects)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEffectsWithPatch() throws Exception {
        // Initialize the database
        effectsRepository.saveAndFlush(effects);

        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();

        // Update the effects using partial update
        Effects partialUpdatedEffects = new Effects();
        partialUpdatedEffects.setId(effects.getId());

        restEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEffects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEffects))
            )
            .andExpect(status().isOk());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
        Effects testEffects = effectsList.get(effectsList.size() - 1);
        assertThat(testEffects.getEffect()).isEqualTo(DEFAULT_EFFECT);
    }

    @Test
    @Transactional
    void fullUpdateEffectsWithPatch() throws Exception {
        // Initialize the database
        effectsRepository.saveAndFlush(effects);

        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();

        // Update the effects using partial update
        Effects partialUpdatedEffects = new Effects();
        partialUpdatedEffects.setId(effects.getId());

        partialUpdatedEffects.effect(UPDATED_EFFECT);

        restEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEffects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEffects))
            )
            .andExpect(status().isOk());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
        Effects testEffects = effectsList.get(effectsList.size() - 1);
        assertThat(testEffects.getEffect()).isEqualTo(UPDATED_EFFECT);
    }

    @Test
    @Transactional
    void patchNonExistingEffects() throws Exception {
        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();
        effects.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, effects.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(effects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEffects() throws Exception {
        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();
        effects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(effects))
            )
            .andExpect(status().isBadRequest());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEffects() throws Exception {
        int databaseSizeBeforeUpdate = effectsRepository.findAll().size();
        effects.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEffectsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(effects)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Effects in the database
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEffects() throws Exception {
        // Initialize the database
        effectsRepository.saveAndFlush(effects);

        int databaseSizeBeforeDelete = effectsRepository.findAll().size();

        // Delete the effects
        restEffectsMockMvc
            .perform(delete(ENTITY_API_URL_ID, effects.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Effects> effectsList = effectsRepository.findAll();
        assertThat(effectsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
