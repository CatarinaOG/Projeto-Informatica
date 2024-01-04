package com.bosch.diabo.web.rest;

import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.repository.FlaggedMaterialRepository;
import com.bosch.diabo.service.FlaggedMaterialService;
import com.bosch.diabo.service.MaterialService;
import com.bosch.diabo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bosch.diabo.domain.FlaggedMaterial}.
 */
@RestController
@RequestMapping("/api")
public class FlaggedMaterialResource {

    private final Logger log = LoggerFactory.getLogger(FlaggedMaterialResource.class);

    private static final String ENTITY_NAME = "flaggedMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlaggedMaterialService flaggedMaterialService;

    private final MaterialService materialService;

    private final FlaggedMaterialRepository flaggedMaterialRepository;

    public FlaggedMaterialResource(FlaggedMaterialService flaggedMaterialService, FlaggedMaterialRepository flaggedMaterialRepository, MaterialService materialService) {
        this.flaggedMaterialService = flaggedMaterialService;
        this.flaggedMaterialRepository = flaggedMaterialRepository;
        this.materialService = materialService;
    }

    /**
     * {@code POST  /flagged-materials} : Create a new flaggedMaterial.
     *
     * @param flaggedMaterial the flaggedMaterial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flaggedMaterial, or with status {@code 400 (Bad Request)} if the flaggedMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flagged-materials")
    public ResponseEntity<FlaggedMaterial> createFlaggedMaterial(@RequestBody FlaggedMaterial flaggedMaterial) throws URISyntaxException {
        log.debug("REST request to save FlaggedMaterial : {}", flaggedMaterial);
        if (flaggedMaterial.getId() != null) {
            throw new BadRequestAlertException("A new flaggedMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FlaggedMaterial result = flaggedMaterialService.save(flaggedMaterial);
        return ResponseEntity
            .created(new URI("/api/flagged-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flagged-materials/:id} : Updates an existing flaggedMaterial.
     *
     * @param id the id of the flaggedMaterial to save.
     * @param flaggedMaterial the flaggedMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flaggedMaterial,
     * or with status {@code 400 (Bad Request)} if the flaggedMaterial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flaggedMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flagged-materials/{id}")
    public ResponseEntity<FlaggedMaterial> updateFlaggedMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FlaggedMaterial flaggedMaterial
    ) throws URISyntaxException {
        log.debug("REST request to update FlaggedMaterial : {}, {}", id, flaggedMaterial);
        if (flaggedMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flaggedMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flaggedMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FlaggedMaterial result = flaggedMaterialService.update(flaggedMaterial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flaggedMaterial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flagged-materials/:id} : Partial updates given fields of an existing flaggedMaterial, field will ignore if it is null
     *
     * @param id the id of the flaggedMaterial to save.
     * @param flaggedMaterial the flaggedMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flaggedMaterial,
     * or with status {@code 400 (Bad Request)} if the flaggedMaterial is not valid,
     * or with status {@code 404 (Not Found)} if the flaggedMaterial is not found,
     * or with status {@code 500 (Internal Server Error)} if the flaggedMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flagged-materials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FlaggedMaterial> partialUpdateFlaggedMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FlaggedMaterial flaggedMaterial
    ) throws URISyntaxException {
        log.debug("REST request to partial update FlaggedMaterial partially : {}, {}", id, flaggedMaterial);
        if (flaggedMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flaggedMaterial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flaggedMaterialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlaggedMaterial> result = flaggedMaterialService.partialUpdate(flaggedMaterial);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flaggedMaterial.getId().toString())
        );
    }

    /**
     * {@code GET  /flagged-materials} : get all the flaggedMaterials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flaggedMaterials in body.
     */
    @GetMapping("/flagged-materials")
    public List<FlaggedMaterial> getAllFlaggedMaterials() {
        log.debug("REST request to get all FlaggedMaterials");
        return flaggedMaterialService.findAll();
    }

    /**
     * {@code GET  /flagged-materials/:id} : get the "id" flaggedMaterial.
     *
     * @param id the id of the flaggedMaterial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flaggedMaterial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flagged-materials/{id}")
    public ResponseEntity<FlaggedMaterial> getFlaggedMaterial(@PathVariable Long id) {
        log.debug("REST request to get FlaggedMaterial : {}", id);
        Optional<FlaggedMaterial> flaggedMaterial = flaggedMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flaggedMaterial);
    }

    /**
     * {@code DELETE  /flagged-materials/:id} : delete the "id" flaggedMaterial.
     *
     * @param id the id of the flaggedMaterial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flagged-materials/{id}")
    public ResponseEntity<Void> deleteFlaggedMaterial(@PathVariable Long id) {
        log.debug("REST request to delete FlaggedMaterial : {}", id);
        flaggedMaterialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /flagged-materials/remove-flag/:id} : remove the flag from the material
     *
     * @param id the id of the flaggedMaterial to remove flag.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}
     */
    @PostMapping("/flagged-materials/update-flag")
    public ResponseEntity<String> updateFlag(@RequestBody List<Object> data) {
        log.debug("REST request to update flag from FlaggedMaterial");
        flaggedMaterialService.updateFlag(data);
        materialService.updateCorrespondingMaterial(data);
        
        return ResponseEntity.ok("");
    }
}
