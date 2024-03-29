package com.bosch.diabo.repository;

import com.bosch.diabo.domain.Material;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Material entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

    // Função utilizada para encontrar um material pelo seu nome
    Optional<Material> findByMaterial(String material);

}
