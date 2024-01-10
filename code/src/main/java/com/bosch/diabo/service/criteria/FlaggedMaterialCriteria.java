package com.bosch.diabo.service.criteria;

import com.bosch.diabo.domain.enumeration.ABCClassification;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bosch.diabo.domain.FlaggedMaterial} entity. This class is used
 * in {@link com.bosch.diabo.web.rest.FlaggedMaterialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /flagged-materials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FlaggedMaterialCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ABCClassification
     */
    public static class ABCClassificationFilter extends Filter<ABCClassification> {

        public ABCClassificationFilter() {}

        public ABCClassificationFilter(ABCClassificationFilter filter) {
            super(filter);
        }

        @Override
        public ABCClassificationFilter copy() {
            return new ABCClassificationFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter material;

    private StringFilter description;

    private ABCClassificationFilter abcClassification;

    private IntegerFilter plant;

    private StringFilter mrpController;

    private BooleanFilter flagMaterial;

    private LocalDateFilter flagExpirationDate;

    private Boolean distinct;

    public FlaggedMaterialCriteria() {}

    public FlaggedMaterialCriteria(FlaggedMaterialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.material = other.material == null ? null : other.material.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.abcClassification = other.abcClassification == null ? null : other.abcClassification.copy();
        this.plant = other.plant == null ? null : other.plant.copy();
        this.mrpController = other.mrpController == null ? null : other.mrpController.copy();
        this.flagMaterial = other.flagMaterial == null ? null : other.flagMaterial.copy();
        this.flagExpirationDate = other.flagExpirationDate == null ? null : other.flagExpirationDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FlaggedMaterialCriteria copy() {
        return new FlaggedMaterialCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMaterial() {
        return material;
    }

    public StringFilter material() {
        if (material == null) {
            material = new StringFilter();
        }
        return material;
    }

    public void setMaterial(StringFilter material) {
        this.material = material;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ABCClassificationFilter getAbcClassification() {
        return abcClassification;
    }

    public ABCClassificationFilter abcClassification() {
        if (abcClassification == null) {
            abcClassification = new ABCClassificationFilter();
        }
        return abcClassification;
    }

    public void setAbcClassification(ABCClassificationFilter abcClassification) {
        this.abcClassification = abcClassification;
    }

    public IntegerFilter getPlant() {
        return plant;
    }

    public IntegerFilter plant() {
        if (plant == null) {
            plant = new IntegerFilter();
        }
        return plant;
    }

    public void setPlant(IntegerFilter plant) {
        this.plant = plant;
    }

    public StringFilter getMrpController() {
        return mrpController;
    }

    public StringFilter mrpController() {
        if (mrpController == null) {
            mrpController = new StringFilter();
        }
        return mrpController;
    }

    public void setMrpController(StringFilter mrpController) {
        this.mrpController = mrpController;
    }

    public BooleanFilter getFlagMaterial() {
        return flagMaterial;
    }

    public BooleanFilter flagMaterial() {
        if (flagMaterial == null) {
            flagMaterial = new BooleanFilter();
        }
        return flagMaterial;
    }

    public void setFlagMaterial(BooleanFilter flagMaterial) {
        this.flagMaterial = flagMaterial;
    }

    public LocalDateFilter getFlagExpirationDate() {
        return flagExpirationDate;
    }

    public LocalDateFilter flagExpirationDate() {
        if (flagExpirationDate == null) {
            flagExpirationDate = new LocalDateFilter();
        }
        return flagExpirationDate;
    }

    public void setFlagExpirationDate(LocalDateFilter flagExpirationDate) {
        this.flagExpirationDate = flagExpirationDate;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FlaggedMaterialCriteria that = (FlaggedMaterialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(material, that.material) &&
            Objects.equals(description, that.description) &&
            Objects.equals(abcClassification, that.abcClassification) &&
            Objects.equals(plant, that.plant) &&
            Objects.equals(mrpController, that.mrpController) &&
            Objects.equals(flagMaterial, that.flagMaterial) &&
            Objects.equals(flagExpirationDate, that.flagExpirationDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, material, description, abcClassification, plant, mrpController, flagMaterial, flagExpirationDate, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FlaggedMaterialCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (material != null ? "material=" + material + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (abcClassification != null ? "abcClassification=" + abcClassification + ", " : "") +
            (plant != null ? "plant=" + plant + ", " : "") +
            (mrpController != null ? "mrpController=" + mrpController + ", " : "") +
            (flagMaterial != null ? "flagMaterial=" + flagMaterial + ", " : "") +
            (flagExpirationDate != null ? "flagExpirationDate=" + flagExpirationDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
