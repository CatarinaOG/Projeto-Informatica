package com.bosch.diabo.web.rest;

import com.bosch.diabo.domain.Diabo;
import com.bosch.diabo.repository.DiaboRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bosch.diabo.domain.Diabo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiaboResource {

    private final Logger log = LoggerFactory.getLogger(DiaboResource.class);

    private static final String ENTITY_NAME = "diabo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiaboRepository diaboRepository;

    public DiaboResource(DiaboRepository diaboRepository) {
        this.diaboRepository = diaboRepository;
    }

    /**
     * {@code POST  /diabos} : Create a new diabo.
     *
     * @param diabo the diabo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diabo, or with status {@code 400 (Bad Request)} if the diabo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diabos")
    public ResponseEntity<Diabo> createDiabo(@RequestBody Diabo diabo) throws URISyntaxException {
        log.debug("REST request to save Diabo : {}", diabo);
        if (diabo.getId() != null) {
            throw new BadRequestAlertException("A new diabo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diabo result = diaboRepository.save(diabo);
        return ResponseEntity
            .created(new URI("/api/diabos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diabos/:id} : Updates an existing diabo.
     *
     * @param id the id of the diabo to save.
     * @param diabo the diabo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diabo,
     * or with status {@code 400 (Bad Request)} if the diabo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diabo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diabos/{id}")
    public ResponseEntity<Diabo> updateDiabo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Diabo diabo)
        throws URISyntaxException {
        log.debug("REST request to update Diabo : {}, {}", id, diabo);
        if (diabo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diabo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Diabo result = diaboRepository.save(diabo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diabo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /diabos/:id} : Partial updates given fields of an existing diabo, field will ignore if it is null
     *
     * @param id the id of the diabo to save.
     * @param diabo the diabo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diabo,
     * or with status {@code 400 (Bad Request)} if the diabo is not valid,
     * or with status {@code 404 (Not Found)} if the diabo is not found,
     * or with status {@code 500 (Internal Server Error)} if the diabo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/diabos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Diabo> partialUpdateDiabo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Diabo diabo)
        throws URISyntaxException {
        log.debug("REST request to partial update Diabo partially : {}, {}", id, diabo);
        if (diabo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diabo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Diabo> result = diaboRepository
            .findById(diabo.getId())
            .map(existingDiabo -> {
                if (diabo.getMaterial() != null) {
                    existingDiabo.setMaterial(diabo.getMaterial());
                }
                if (diabo.getMaterialDescription() != null) {
                    existingDiabo.setMaterialDescription(diabo.getMaterialDescription());
                }
                if (diabo.getAbcClassification() != null) {
                    existingDiabo.setAbcClassification(diabo.getAbcClassification());
                }
                if (diabo.getAvgSupplierDelayLast90Days() != null) {
                    existingDiabo.setAvgSupplierDelayLast90Days(diabo.getAvgSupplierDelayLast90Days());
                }
                if (diabo.getMaxSupplierDelayLast90Days() != null) {
                    existingDiabo.setMaxSupplierDelayLast90Days(diabo.getMaxSupplierDelayLast90Days());
                }
                if (diabo.getServiceLevel() != null) {
                    existingDiabo.setServiceLevel(diabo.getServiceLevel());
                }
                if (diabo.getCurrentSapSafetyStock() != null) {
                    existingDiabo.setCurrentSapSafetyStock(diabo.getCurrentSapSafetyStock());
                }
                if (diabo.getProposedSafetyStock() != null) {
                    existingDiabo.setProposedSafetyStock(diabo.getProposedSafetyStock());
                }
                if (diabo.getDeltaSafetyStock() != null) {
                    existingDiabo.setDeltaSafetyStock(diabo.getDeltaSafetyStock());
                }
                if (diabo.getCurrentSapSafetyTime() != null) {
                    existingDiabo.setCurrentSapSafetyTime(diabo.getCurrentSapSafetyTime());
                }
                if (diabo.getProposedSafetyTime() != null) {
                    existingDiabo.setProposedSafetyTime(diabo.getProposedSafetyTime());
                }
                if (diabo.getDeltaSafetyTime() != null) {
                    existingDiabo.setDeltaSafetyTime(diabo.getDeltaSafetyTime());
                }
                if (diabo.getOpenSapMd04() != null) {
                    existingDiabo.setOpenSapMd04(diabo.getOpenSapMd04());
                }
                if (diabo.getCurrentInventoryValue() != null) {
                    existingDiabo.setCurrentInventoryValue(diabo.getCurrentInventoryValue());
                }
                if (diabo.getAverageInventoryEffectAfterChange() != null) {
                    existingDiabo.setAverageInventoryEffectAfterChange(diabo.getAverageInventoryEffectAfterChange());
                }
                if (diabo.getNewSapSafetyStock() != null) {
                    existingDiabo.setNewSapSafetyStock(diabo.getNewSapSafetyStock());
                }
                if (diabo.getNewSapSafetyTime() != null) {
                    existingDiabo.setNewSapSafetyTime(diabo.getNewSapSafetyTime());
                }
                if (diabo.getSelectEntriesForChangeInSap() != null) {
                    existingDiabo.setSelectEntriesForChangeInSap(diabo.getSelectEntriesForChangeInSap());
                }
                if (diabo.getFlagMaterialAsSpecialCase() != null) {
                    existingDiabo.setFlagMaterialAsSpecialCase(diabo.getFlagMaterialAsSpecialCase());
                }
                if (diabo.getCommentary() != null) {
                    existingDiabo.setCommentary(diabo.getCommentary());
                }

                return existingDiabo;
            })
            .map(diaboRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diabo.getId().toString())
        );
    }

    /**
     * {@code GET  /diabos} : get all the diabos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diabos in body.
     */
    @GetMapping("/diabos")
    public List<Diabo> getAllDiabos() {
        log.debug("REST request to get all Diabos");
        return diaboRepository.findAll();
    }

    /**
     * {@code GET  /diabos/:id} : get the "id" diabo.
     *
     * @param id the id of the diabo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diabo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diabos/{id}")
    public ResponseEntity<Diabo> getDiabo(@PathVariable Long id) {
        log.debug("REST request to get Diabo : {}", id);
        Optional<Diabo> diabo = diaboRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diabo);
    }

    /**
     * {@code DELETE  /diabos/:id} : delete the "id" diabo.
     *
     * @param id the id of the diabo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diabos/{id}")
    public ResponseEntity<Void> deleteDiabo(@PathVariable Long id) {
        log.debug("REST request to delete Diabo : {}", id);
        diaboRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
