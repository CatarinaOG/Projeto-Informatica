package com.bosch.diabo.repository;

import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.Material;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FlaggedMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlaggedMaterialRepository extends JpaRepository<FlaggedMaterial, Long>,  JpaSpecificationExecutor<Material> {
        Optional<FlaggedMaterial> findByMaterial(String material);
}
