package com.bosch.diabo.repository;

import com.bosch.diabo.domain.Diabo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Diabo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiaboRepository extends JpaRepository<Diabo, Long> {}
