package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaterialSideEffects;
import com.mycompany.myapp.repository.MaterialSideEffectsRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterialSideEffects}.
 */
@RestController
@RequestMapping("/api/material-side-effects")
@Transactional
public class MaterialSideEffectsResource {

    private final Logger log = LoggerFactory.getLogger(MaterialSideEffectsResource.class);

    private static final String ENTITY_NAME = "materialSideEffects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialSideEffectsRepository materialSideEffectsRepository;

    public MaterialSideEffectsResource(MaterialSideEffectsRepository materialSideEffectsRepository) {
        this.materialSideEffectsRepository = materialSideEffectsRepository;
    }

    /**
     * {@code POST  /material-side-effects} : Create a new materialSideEffects.
     *
     * @param materialSideEffects the materialSideEffects to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialSideEffects, or with status {@code 400 (Bad Request)} if the materialSideEffects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MaterialSideEffects> createMaterialSideEffects(@RequestBody MaterialSideEffects materialSideEffects)
        throws URISyntaxException {
        log.debug("REST request to save MaterialSideEffects : {}", materialSideEffects);
        if (materialSideEffects.getId() != null) {
            throw new BadRequestAlertException("A new materialSideEffects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialSideEffects result = materialSideEffectsRepository.save(materialSideEffects);
        return ResponseEntity
            .created(new URI("/api/material-side-effects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-side-effects/:id} : Updates an existing materialSideEffects.
     *
     * @param id the id of the materialSideEffects to save.
     * @param materialSideEffects the materialSideEffects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialSideEffects,
     * or with status {@code 400 (Bad Request)} if the materialSideEffects is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialSideEffects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaterialSideEffects> updateMaterialSideEffects(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialSideEffects materialSideEffects
    ) throws URISyntaxException {
        log.debug("REST request to update MaterialSideEffects : {}, {}", id, materialSideEffects);
        if (materialSideEffects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialSideEffects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialSideEffectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaterialSideEffects result = materialSideEffectsRepository.save(materialSideEffects);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialSideEffects.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /material-side-effects/:id} : Partial updates given fields of an existing materialSideEffects, field will ignore if it is null
     *
     * @param id the id of the materialSideEffects to save.
     * @param materialSideEffects the materialSideEffects to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialSideEffects,
     * or with status {@code 400 (Bad Request)} if the materialSideEffects is not valid,
     * or with status {@code 404 (Not Found)} if the materialSideEffects is not found,
     * or with status {@code 500 (Internal Server Error)} if the materialSideEffects couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterialSideEffects> partialUpdateMaterialSideEffects(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialSideEffects materialSideEffects
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaterialSideEffects partially : {}, {}", id, materialSideEffects);
        if (materialSideEffects.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialSideEffects.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialSideEffectsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterialSideEffects> result = materialSideEffectsRepository
            .findById(materialSideEffects.getId())
            .map(existingMaterialSideEffects -> {
                if (materialSideEffects.getMaterialSideEffectsName() != null) {
                    existingMaterialSideEffects.setMaterialSideEffectsName(materialSideEffects.getMaterialSideEffectsName());
                }

                return existingMaterialSideEffects;
            })
            .map(materialSideEffectsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialSideEffects.getId().toString())
        );
    }

    /**
     * {@code GET  /material-side-effects} : get all the materialSideEffects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialSideEffects in body.
     */
    @GetMapping("")
    public List<MaterialSideEffects> getAllMaterialSideEffects() {
        log.debug("REST request to get all MaterialSideEffects");
        return materialSideEffectsRepository.findAll();
    }

    /**
     * {@code GET  /material-side-effects/:id} : get the "id" materialSideEffects.
     *
     * @param id the id of the materialSideEffects to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialSideEffects, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaterialSideEffects> getMaterialSideEffects(@PathVariable("id") Long id) {
        log.debug("REST request to get MaterialSideEffects : {}", id);
        Optional<MaterialSideEffects> materialSideEffects = materialSideEffectsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materialSideEffects);
    }

    /**
     * {@code DELETE  /material-side-effects/:id} : delete the "id" materialSideEffects.
     *
     * @param id the id of the materialSideEffects to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterialSideEffects(@PathVariable("id") Long id) {
        log.debug("REST request to delete MaterialSideEffects : {}", id);
        materialSideEffectsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
