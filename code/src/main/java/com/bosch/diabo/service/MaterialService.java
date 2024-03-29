package com.bosch.diabo.service;

import com.bosch.diabo.domain.Material;

import java.util.List;
import java.io.File;
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
     * Updates a material identified by the name.
     *
     * @param material the entity to update.
     * @return the persisted entity.
     */
    Optional<Material> updateByMaterialName(Material material);


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
     * Find Material by the material name.
     *
     * @param material the material name.
     * @return the entity.
     */
    Optional<Material> findByMaterial(String material);


    /**
     * Delete the "id" material.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Replace current data with new from the source file
     *
     * @param file new file source
     */
    void uploadFileReplace(File file) throws Exception;
    
    /**
     * Add new data or update current one from the source file
     *
     * @param file new file source
     */
    void uploadFileAddOrUpdate(File file) throws Exception;
    
    /**
     * Change materials given a list of the updated values
     *
     * @param data list of the updated values
     */
    void submitChanges(List<Object> data);


    /**
     * Get materials given a list of the updated values
     *
     * @param data list of the updated values
     */
    List<Material> getMaterialChanged(List<Object> data);


    /**
     * Update Material givem changes in the Flagged Material
     *
     * @param data list of the updated values
     */
    void updateCorrespondingMaterial(List<Object> data);

}
