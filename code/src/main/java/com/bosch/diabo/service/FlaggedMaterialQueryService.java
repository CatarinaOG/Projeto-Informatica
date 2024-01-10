package com.bosch.diabo.service;

import com.bosch.diabo.domain.*; // for static metamodels
import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.repository.FlaggedMaterialRepository;
import com.bosch.diabo.service.criteria.FlaggedMaterialCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FlaggedMaterial} entities in the database.
 * The main input is a {@link FlaggedMaterialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FlaggedMaterial} or a {@link Page} of {@link FlaggedMaterial} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FlaggedMaterialQueryService extends QueryService<FlaggedMaterial> {

    private final Logger log = LoggerFactory.getLogger(FlaggedMaterialQueryService.class);

    private final FlaggedMaterialRepository flaggedMaterialRepository;

    public FlaggedMaterialQueryService(FlaggedMaterialRepository flaggedMaterialRepository) {
        this.flaggedMaterialRepository = flaggedMaterialRepository;
    }

    /**
     * Return a {@link List} of {@link FlaggedMaterial} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FlaggedMaterial> findByCriteria(FlaggedMaterialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FlaggedMaterial> specification = createSpecification(criteria);
        return flaggedMaterialRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FlaggedMaterial} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FlaggedMaterial> findByCriteria(FlaggedMaterialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FlaggedMaterial> specification = createSpecification(criteria);
        return flaggedMaterialRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FlaggedMaterialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FlaggedMaterial> specification = createSpecification(criteria);
        return flaggedMaterialRepository.count(specification);
    }

    /**
     * Function to convert {@link FlaggedMaterialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FlaggedMaterial> createSpecification(FlaggedMaterialCriteria criteria) {
        Specification<FlaggedMaterial> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FlaggedMaterial_.id));
            }
            if (criteria.getMaterial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterial(), FlaggedMaterial_.material));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), FlaggedMaterial_.description));
            }
            if (criteria.getAbcClassification() != null) {
                specification = specification.and(buildSpecification(criteria.getAbcClassification(), FlaggedMaterial_.abcClassification));
            }
            if (criteria.getPlant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlant(), FlaggedMaterial_.plant));
            }
            if (criteria.getMrpController() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMrpController(), FlaggedMaterial_.mrpController));
            }
            if (criteria.getFlagMaterial() != null) {
                specification = specification.and(buildSpecification(criteria.getFlagMaterial(), FlaggedMaterial_.flagMaterial));
            }
            if (criteria.getFlagExpirationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFlagExpirationDate(), FlaggedMaterial_.flagExpirationDate));
            }
        }
        return specification;
    }
}
