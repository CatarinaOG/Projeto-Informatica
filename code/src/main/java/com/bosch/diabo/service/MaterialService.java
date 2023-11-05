package com.bosch.diabo.service;

import com.bosch.diabo.domain.Material;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Material}.
 */
public interface MaterialService {
    /**
     * Save a material.
     *
     * @param material the entity to save.
     * @return the persisted entity.
     */
    Material save(Material material);

    /**
     * Updates a material.
     *
     * @param material the entity to update.
     * @return the persisted entity.
     */
    Material update(Material material);

    /**
     * Partially updates a material.
     *
     * @param material the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Material> partialUpdate(Material material);

    /**
     * Get all the materials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Material> findAll(Pageable pageable);

    /**
     * Get the "id" material.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Material> findOne(Long id);

    /**
     * Delete the "id" material.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}