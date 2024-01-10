package com.bosch.diabo.service;

import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.Material;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FlaggedMaterial}.
 */
public interface FlaggedMaterialService {
    /**
     * Save a flaggedMaterial.
     *
     * @param flaggedMaterial the entity to save.
     * @return the persisted entity.
     */
    FlaggedMaterial save(FlaggedMaterial flaggedMaterial);

    /**
     * Updates a flaggedMaterial.
     *
     * @param flaggedMaterial the entity to update.
     * @return the persisted entity.
     */
    FlaggedMaterial update(FlaggedMaterial flaggedMaterial);

    /**
     * Partially updates a flaggedMaterial.
     *
     * @param flaggedMaterial the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FlaggedMaterial> partialUpdate(FlaggedMaterial flaggedMaterial);

    /**
     * Get all the flaggedMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FlaggedMaterial> findAll(Pageable pageable);

    /**
     * Get the "id" flaggedMaterial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FlaggedMaterial> findOne(Long id);

    /**
     * Delete the "id" flaggedMaterial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Adds or removes material from flagged materials.
     *
     * @param flag flag of the material.
     * @param material existing material.
     */
    void updateFlaggedMaterials(Boolean flag, Material material);



    /**
     * Find Material by the material name.
     *
     * @param material the material name.
     * @return the entity.
     */
    Optional<FlaggedMaterial> findByMaterial(String material);


    /**
     * Update flagged.
     *
     * @param data data with id, flag and date of the flagged item to update.
     */
    void updateFlag(List<Object> data);
}
