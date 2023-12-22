package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PlatingMaterial;
import com.mycompany.myapp.repository.PlatingMaterialRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PlatingMaterial}.
 */
@RestController
@RequestMapping("/api/plating-materials")
@Transactional
public class PlatingMaterialResource {

    private final Logger log = LoggerFactory.getLogger(PlatingMaterialResource.class);

    private static final String ENTITY_NAME = "platingMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatingMaterialRepository platingMaterialRepository;

    public PlatingMaterialResource(PlatingMaterialRepository platingMaterialRepository) {
        this.platingMaterialRepository = platingMaterialRepository;
    }

    /**
     * {@code POST  /plating-materials} : Create a new platingMaterial.
     *
     * @param platingMaterial the platingMaterial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new platingMaterial, or with status {@code 400 (Bad Request)} if the platingMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlatingMaterial> createPlatingMaterial(@RequestBody PlatingMaterial platingMaterial) throws URISyntaxException {
        log.debug("REST request to save PlatingMaterial : {}", platingMaterial);
        if (platingMaterial.getId() != null) {
            throw new BadRequestAlertException("A new platingMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatingMaterial result = platingMaterialRepository.save(platingMaterial);
        return ResponseEntity
            .created(new URI("/api/plating-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plating-materials/:id} : Updates an existing platingMaterial.
     *
     * @param id the id of the platingMaterial to save.
     * @param platingMaterial the platingMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platingMaterial,
     * or with status {@code 400 (Bad Request)} if the platingMaterial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the platingMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlatingMaterial> updatePlatingMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlatingMaterial platingMaterial
    ) throws URISyntaxException {
        log.debug("REST request to update PlatingMaterial : {}, {}", id, platingMaterial);
        if (platingMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, platingMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!platingMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlatingMaterial result = platingMaterialRepository.save(platingMaterial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, platingMaterial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plating-materials/:id} : Partial updates given fields of an existing platingMaterial, field will ignore if it is null
     *
     * @param id the id of the platingMaterial to save.
     * @param platingMaterial the platingMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platingMaterial,
     * or with status {@code 400 (Bad Request)} if the platingMaterial is not valid,
     * or with status {@code 404 (Not Found)} if the platingMaterial is not found,
     * or with status {@code 500 (Internal Server Error)} if the platingMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlatingMaterial> partialUpdatePlatingMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlatingMaterial platingMaterial
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlatingMaterial partially : {}, {}", id, platingMaterial);
        if (platingMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, platingMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!platingMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlatingMaterial> result = platingMaterialRepository
            .findById(platingMaterial.getId())
            .map(existingPlatingMaterial -> {
                if (platingMaterial.getPlatingName() != null) {
                    existingPlatingMaterial.setPlatingName(platingMaterial.getPlatingName());
                }

                return existingPlatingMaterial;
            })
            .map(platingMaterialRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, platingMaterial.getId().toString())
        );
    }

    /**
     * {@code GET  /plating-materials} : get all the platingMaterials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of platingMaterials in body.
     */
    @GetMapping("")
    public List<PlatingMaterial> getAllPlatingMaterials() {
        log.debug("REST request to get all PlatingMaterials");
        return platingMaterialRepository.findAll();
    }

    /**
     * {@code GET  /plating-materials/:id} : get the "id" platingMaterial.
     *
     * @param id the id of the platingMaterial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the platingMaterial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlatingMaterial> getPlatingMaterial(@PathVariable("id") Long id) {
        log.debug("REST request to get PlatingMaterial : {}", id);
        Optional<PlatingMaterial> platingMaterial = platingMaterialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(platingMaterial);
    }

    /**
     * {@code DELETE  /plating-materials/:id} : delete the "id" platingMaterial.
     *
     * @param id the id of the platingMaterial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatingMaterial(@PathVariable("id") Long id) {
        log.debug("REST request to delete PlatingMaterial : {}", id);
        platingMaterialRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
