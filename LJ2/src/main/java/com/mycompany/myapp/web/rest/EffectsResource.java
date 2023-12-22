package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Effects;
import com.mycompany.myapp.repository.EffectsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Effects}.
 */
@RestController
@RequestMapping("/api/effects")
@Transactional
public class EffectsResource {

    private final Logger log = LoggerFactory.getLogger(EffectsResource.class);

    private static final String ENTITY_NAME = "effects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EffectsRepository effectsRepository;

    public EffectsResource(EffectsRepository effectsRepository) {
        this.effectsRepository = effectsRepository;
    }

    /**
     * {@code POST  /effects} : Create a new effects.
     *
     * @param effects the effects to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new effects, or with status {@code 400 (Bad Request)} if the effects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Effects> createEffects(@RequestBody Effects effects) throws URISyntaxException {
        log.debug("REST request to save Effects : {}", effects);
        if (effects.getId() != null) {
            throw new BadRequestAlertException("A new effects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Effects result = effectsRepository.save(effects);
        return ResponseEntity
            .created(new URI("/api/effects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /effects/:id} : Updates an existing effects.
     *
     * @param id the id of the effects to save.
     * @param effects the effects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated effects,
     * or with status {@code 400 (Bad Request)} if the effects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the effects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Effects> updateEffects(@PathVariable(value = "id", required = false) final Long id, @RequestBody Effects effects)
        throws URISyntaxException {
        log.debug("REST request to update Effects : {}, {}", id, effects);
        if (effects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, effects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!effectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Effects result = effectsRepository.save(effects);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, effects.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /effects/:id} : Partial updates given fields of an existing effects, field will ignore if it is null
     *
     * @param id the id of the effects to save.
     * @param effects the effects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated effects,
     * or with status {@code 400 (Bad Request)} if the effects is not valid,
     * or with status {@code 404 (Not Found)} if the effects is not found,
     * or with status {@code 500 (Internal Server Error)} if the effects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Effects> partialUpdateEffects(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Effects effects
    ) throws URISyntaxException {
        log.debug("REST request to partial update Effects partially : {}, {}", id, effects);
        if (effects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, effects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!effectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Effects> result = effectsRepository
            .findById(effects.getId())
            .map(existingEffects -> {
                if (effects.getEffect() != null) {
                    existingEffects.setEffect(effects.getEffect());
                }

                return existingEffects;
            })
            .map(effectsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, effects.getId().toString())
        );
    }

    /**
     * {@code GET  /effects} : get all the effects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of effects in body.
     */
    @GetMapping("")
    public List<Effects> getAllEffects() {
        log.debug("REST request to get all Effects");
        return effectsRepository.findAll();
    }

    /**
     * {@code GET  /effects/:id} : get the "id" effects.
     *
     * @param id the id of the effects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the effects, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Effects> getEffects(@PathVariable("id") Long id) {
        log.debug("REST request to get Effects : {}", id);
        Optional<Effects> effects = effectsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(effects);
    }

    /**
     * {@code DELETE  /effects/:id} : delete the "id" effects.
     *
     * @param id the id of the effects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEffects(@PathVariable("id") Long id) {
        log.debug("REST request to delete Effects : {}", id);
        effectsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
