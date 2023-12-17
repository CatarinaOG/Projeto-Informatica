package com.bosch.diabo.service;

import com.bosch.diabo.domain.*; // for static metamodels
import com.bosch.diabo.domain.Material;
import com.bosch.diabo.repository.MaterialRepository;
import com.bosch.diabo.service.criteria.MaterialCriteria;
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
 * Service for executing complex queries for {@link Material} entities in the database.
 * The main input is a {@link MaterialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Material} or a {@link Page} of {@link Material} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialQueryService extends QueryService<Material> {

    private final Logger log = LoggerFactory.getLogger(MaterialQueryService.class);

    private final MaterialRepository materialRepository;

    public MaterialQueryService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Return a {@link List} of {@link Material} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Material> findByCriteria(MaterialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Material} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Material> findByCriteria(MaterialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Material> createSpecification(MaterialCriteria criteria) {
        Specification<Material> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Material_.id));
            }
            if (criteria.getMaterial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterial(), Material_.material));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Material_.description));
            }
            if (criteria.getAbcClassification() != null) {
                specification = specification.and(buildSpecification(criteria.getAbcClassification(), Material_.abcClassification));
            }
            if (criteria.getAvgSupplierDelay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvgSupplierDelay(), Material_.avgSupplierDelay));
            }
            if (criteria.getMaxSupplierDelay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxSupplierDelay(), Material_.maxSupplierDelay));
            }
            if (criteria.getServiceLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServiceLevel(), Material_.serviceLevel));
            }
            if (criteria.getCurrSAPSafetyStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrSAPSafetyStock(), Material_.currSAPSafetyStock));
            }
            if (criteria.getProposedSST() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedSST(), Material_.proposedSST));
            }
            if (criteria.getDeltaSST() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeltaSST(), Material_.deltaSST));
            }
            if (criteria.getCurrentSAPSafeTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentSAPSafeTime(), Material_.currentSAPSafeTime));
            }
            if (criteria.getProposedST() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedST(), Material_.proposedST));
            }
            if (criteria.getDeltaST() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeltaST(), Material_.deltaST));
            }
            if (criteria.getOpenSAPmd04() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpenSAPmd04(), Material_.openSAPmd04));
            }
            if (criteria.getCurrentInventoryValue() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCurrentInventoryValue(), Material_.currentInventoryValue));
            }
            if (criteria.getUnitCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitCost(), Material_.unitCost));
            }
            if (criteria.getAvgDemand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvgDemand(), Material_.avgDemand));
            }
            if (criteria.getAvgInventoryEffectAfterChange() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAvgInventoryEffectAfterChange(), Material_.avgInventoryEffectAfterChange)
                    );
            }
            if (criteria.getNewSAPSafetyStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNewSAPSafetyStock(), Material_.newSAPSafetyStock));
            }
            if (criteria.getNewSAPSafetyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNewSAPSafetyTime(), Material_.newSAPSafetyTime));
            }
            if (criteria.getFlagMaterial() != null) {
                specification = specification.and(buildSpecification(criteria.getFlagMaterial(), Material_.flagMaterial));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Material_.comment));
            }
            if (criteria.getPlant() != null) {
                specification = specification.and(buildSpecification(criteria.getPlant(), Material_.plant));
            }
            if (criteria.getMRPcontroller() != null) {
                specification = specification.and(buildSpecification(criteria.getMRPcontroller(), Material_.mrpController));
            }
        }
        return specification;
    }
}
