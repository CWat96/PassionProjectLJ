package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.StoneGem;
import com.mycompany.myapp.repository.StoneGemRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.StoneGem}.
 */
@RestController
@RequestMapping("/api/stone-gems")
@Transactional
public class StoneGemResource {

    private final Logger log = LoggerFactory.getLogger(StoneGemResource.class);

    private static final String ENTITY_NAME = "stoneGem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoneGemRepository stoneGemRepository;

    public StoneGemResource(StoneGemRepository stoneGemRepository) {
        this.stoneGemRepository = stoneGemRepository;
    }

    /**
     * {@code POST  /stone-gems} : Create a new stoneGem.
     *
     * @param stoneGem the stoneGem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stoneGem, or with status {@code 400 (Bad Request)} if the stoneGem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StoneGem> createStoneGem(@RequestBody StoneGem stoneGem) throws URISyntaxException {
        log.debug("REST request to save StoneGem : {}", stoneGem);
        if (stoneGem.getId() != null) {
            throw new BadRequestAlertException("A new stoneGem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoneGem result = stoneGemRepository.save(stoneGem);
        return ResponseEntity
            .created(new URI("/api/stone-gems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stone-gems/:id} : Updates an existing stoneGem.
     *
     * @param id the id of the stoneGem to save.
     * @param stoneGem the stoneGem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stoneGem,
     * or with status {@code 400 (Bad Request)} if the stoneGem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stoneGem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StoneGem> updateStoneGem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StoneGem stoneGem
    ) throws URISyntaxException {
        log.debug("REST request to update StoneGem : {}, {}", id, stoneGem);
        if (stoneGem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stoneGem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stoneGemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StoneGem result = stoneGemRepository.save(stoneGem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stoneGem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stone-gems/:id} : Partial updates given fields of an existing stoneGem, field will ignore if it is null
     *
     * @param id the id of the stoneGem to save.
     * @param stoneGem the stoneGem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stoneGem,
     * or with status {@code 400 (Bad Request)} if the stoneGem is not valid,
     * or with status {@code 404 (Not Found)} if the stoneGem is not found,
     * or with status {@code 500 (Internal Server Error)} if the stoneGem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StoneGem> partialUpdateStoneGem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StoneGem stoneGem
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoneGem partially : {}, {}", id, stoneGem);
        if (stoneGem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stoneGem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stoneGemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StoneGem> result = stoneGemRepository
            .findById(stoneGem.getId())
            .map(existingStoneGem -> {
                if (stoneGem.getStoneGemName() != null) {
                    existingStoneGem.setStoneGemName(stoneGem.getStoneGemName());
                }

                return existingStoneGem;
            })
            .map(stoneGemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stoneGem.getId().toString())
        );
    }

    /**
     * {@code GET  /stone-gems} : get all the stoneGems.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stoneGems in body.
     */
    @GetMapping("")
    public List<StoneGem> getAllStoneGems(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all StoneGems");
        if (eagerload) {
            return stoneGemRepository.findAllWithEagerRelationships();
        } else {
            return stoneGemRepository.findAll();
        }
    }

    /**
     * {@code GET  /stone-gems/:id} : get the "id" stoneGem.
     *
     * @param id the id of the stoneGem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stoneGem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StoneGem> getStoneGem(@PathVariable("id") Long id) {
        log.debug("REST request to get StoneGem : {}", id);
        Optional<StoneGem> stoneGem = stoneGemRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(stoneGem);
    }

    /**
     * {@code DELETE  /stone-gems/:id} : delete the "id" stoneGem.
     *
     * @param id the id of the stoneGem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoneGem(@PathVariable("id") Long id) {
        log.debug("REST request to delete StoneGem : {}", id);
        stoneGemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
