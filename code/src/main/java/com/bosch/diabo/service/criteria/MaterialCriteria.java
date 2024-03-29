package com.bosch.diabo.service.criteria;

import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.domain.enumeration.Coin;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bosch.diabo.domain.Material} entity. This class is used
 * in {@link com.bosch.diabo.web.rest.MaterialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /materials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaterialCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering Coin
     */
    public static class CoinFilter extends Filter<Coin> {

        public CoinFilter() {}

        public CoinFilter(CoinFilter filter) {
            super(filter);
        }

        @Override
        public CoinFilter copy() {
            return new CoinFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter material;

    private StringFilter description;

    private ABCClassificationFilter abcClassification;

    private IntegerFilter plant;

    private StringFilter mrpController;

    private FloatFilter avgSupplierDelay;

    private FloatFilter maxSupplierDelay;

    private FloatFilter serviceLevel;

    private IntegerFilter currSAPSafetyStock;

    private IntegerFilter proposedSST;

    private IntegerFilter deltaSST;

    private IntegerFilter currentSAPSafeTime;

    private IntegerFilter proposedST;

    private IntegerFilter deltaST;

    private StringFilter openSAPmd04;

    private FloatFilter currentInventoryValue;

    private FloatFilter unitCost;

    private IntegerFilter avgDemand;

    private FloatFilter avgInventoryEffectAfterChange;

    private BooleanFilter flagMaterial;

    private LocalDateFilter flagExpirationDate;

    private StringFilter comment;

    private IntegerFilter newSAPSafetyStock;

    private IntegerFilter newSAPSafetyTime;

    private LocalDateFilter dateNewSS;

    private LocalDateFilter datNewST;

    private IntegerFilter previousSS;

    private IntegerFilter previousST;

    private LocalDateFilter datePreviousSS;

    private LocalDateFilter datePreviousST;

    private BooleanFilter toSaveUpdates;

    private CoinFilter currency;

    private Boolean distinct;

    public MaterialCriteria() {}

    public MaterialCriteria(MaterialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.material = other.material == null ? null : other.material.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.abcClassification = other.abcClassification == null ? null : other.abcClassification.copy();
        this.plant = other.plant == null ? null : other.plant.copy();
        this.mrpController = other.mrpController == null ? null : other.mrpController.copy();
        this.avgSupplierDelay = other.avgSupplierDelay == null ? null : other.avgSupplierDelay.copy();
        this.maxSupplierDelay = other.maxSupplierDelay == null ? null : other.maxSupplierDelay.copy();
        this.serviceLevel = other.serviceLevel == null ? null : other.serviceLevel.copy();
        this.currSAPSafetyStock = other.currSAPSafetyStock == null ? null : other.currSAPSafetyStock.copy();
        this.proposedSST = other.proposedSST == null ? null : other.proposedSST.copy();
        this.deltaSST = other.deltaSST == null ? null : other.deltaSST.copy();
        this.currentSAPSafeTime = other.currentSAPSafeTime == null ? null : other.currentSAPSafeTime.copy();
        this.proposedST = other.proposedST == null ? null : other.proposedST.copy();
        this.deltaST = other.deltaST == null ? null : other.deltaST.copy();
        this.openSAPmd04 = other.openSAPmd04 == null ? null : other.openSAPmd04.copy();
        this.currentInventoryValue = other.currentInventoryValue == null ? null : other.currentInventoryValue.copy();
        this.unitCost = other.unitCost == null ? null : other.unitCost.copy();
        this.avgDemand = other.avgDemand == null ? null : other.avgDemand.copy();
        this.avgInventoryEffectAfterChange =
            other.avgInventoryEffectAfterChange == null ? null : other.avgInventoryEffectAfterChange.copy();
        this.flagMaterial = other.flagMaterial == null ? null : other.flagMaterial.copy();
        this.flagExpirationDate = other.flagExpirationDate == null ? null : other.flagExpirationDate.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.newSAPSafetyStock = other.newSAPSafetyStock == null ? null : other.newSAPSafetyStock.copy();
        this.newSAPSafetyTime = other.newSAPSafetyTime == null ? null : other.newSAPSafetyTime.copy();
        this.dateNewSS = other.dateNewSS == null ? null : other.dateNewSS.copy();
        this.datNewST = other.datNewST == null ? null : other.datNewST.copy();
        this.previousSS = other.previousSS == null ? null : other.previousSS.copy();
        this.previousST = other.previousST == null ? null : other.previousST.copy();
        this.datePreviousSS = other.datePreviousSS == null ? null : other.datePreviousSS.copy();
        this.datePreviousST = other.datePreviousST == null ? null : other.datePreviousST.copy();
        this.toSaveUpdates = other.toSaveUpdates == null ? null : other.toSaveUpdates.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MaterialCriteria copy() {
        return new MaterialCriteria(this);
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

    public FloatFilter getAvgSupplierDelay() {
        return avgSupplierDelay;
    }

    public FloatFilter avgSupplierDelay() {
        if (avgSupplierDelay == null) {
            avgSupplierDelay = new FloatFilter();
        }
        return avgSupplierDelay;
    }

    public void setAvgSupplierDelay(FloatFilter avgSupplierDelay) {
        this.avgSupplierDelay = avgSupplierDelay;
    }

    public FloatFilter getMaxSupplierDelay() {
        return maxSupplierDelay;
    }

    public FloatFilter maxSupplierDelay() {
        if (maxSupplierDelay == null) {
            maxSupplierDelay = new FloatFilter();
        }
        return maxSupplierDelay;
    }

    public void setMaxSupplierDelay(FloatFilter maxSupplierDelay) {
        this.maxSupplierDelay = maxSupplierDelay;
    }

    public FloatFilter getServiceLevel() {
        return serviceLevel;
    }

    public FloatFilter serviceLevel() {
        if (serviceLevel == null) {
            serviceLevel = new FloatFilter();
        }
        return serviceLevel;
    }

    public void setServiceLevel(FloatFilter serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public IntegerFilter getCurrSAPSafetyStock() {
        return currSAPSafetyStock;
    }

    public IntegerFilter currSAPSafetyStock() {
        if (currSAPSafetyStock == null) {
            currSAPSafetyStock = new IntegerFilter();
        }
        return currSAPSafetyStock;
    }

    public void setCurrSAPSafetyStock(IntegerFilter currSAPSafetyStock) {
        this.currSAPSafetyStock = currSAPSafetyStock;
    }

    public IntegerFilter getProposedSST() {
        return proposedSST;
    }

    public IntegerFilter proposedSST() {
        if (proposedSST == null) {
            proposedSST = new IntegerFilter();
        }
        return proposedSST;
    }

    public void setProposedSST(IntegerFilter proposedSST) {
        this.proposedSST = proposedSST;
    }

    public IntegerFilter getDeltaSST() {
        return deltaSST;
    }

    public IntegerFilter deltaSST() {
        if (deltaSST == null) {
            deltaSST = new IntegerFilter();
        }
        return deltaSST;
    }

    public void setDeltaSST(IntegerFilter deltaSST) {
        this.deltaSST = deltaSST;
    }

    public IntegerFilter getCurrentSAPSafeTime() {
        return currentSAPSafeTime;
    }

    public IntegerFilter currentSAPSafeTime() {
        if (currentSAPSafeTime == null) {
            currentSAPSafeTime = new IntegerFilter();
        }
        return currentSAPSafeTime;
    }

    public void setCurrentSAPSafeTime(IntegerFilter currentSAPSafeTime) {
        this.currentSAPSafeTime = currentSAPSafeTime;
    }

    public IntegerFilter getProposedST() {
        return proposedST;
    }

    public IntegerFilter proposedST() {
        if (proposedST == null) {
            proposedST = new IntegerFilter();
        }
        return proposedST;
    }

    public void setProposedST(IntegerFilter proposedST) {
        this.proposedST = proposedST;
    }

    public IntegerFilter getDeltaST() {
        return deltaST;
    }

    public IntegerFilter deltaST() {
        if (deltaST == null) {
            deltaST = new IntegerFilter();
        }
        return deltaST;
    }

    public void setDeltaST(IntegerFilter deltaST) {
        this.deltaST = deltaST;
    }

    public StringFilter getOpenSAPmd04() {
        return openSAPmd04;
    }

    public StringFilter openSAPmd04() {
        if (openSAPmd04 == null) {
            openSAPmd04 = new StringFilter();
        }
        return openSAPmd04;
    }

    public void setOpenSAPmd04(StringFilter openSAPmd04) {
        this.openSAPmd04 = openSAPmd04;
    }

    public FloatFilter getCurrentInventoryValue() {
        return currentInventoryValue;
    }

    public FloatFilter currentInventoryValue() {
        if (currentInventoryValue == null) {
            currentInventoryValue = new FloatFilter();
        }
        return currentInventoryValue;
    }

    public void setCurrentInventoryValue(FloatFilter currentInventoryValue) {
        this.currentInventoryValue = currentInventoryValue;
    }

    public FloatFilter getUnitCost() {
        return unitCost;
    }

    public FloatFilter unitCost() {
        if (unitCost == null) {
            unitCost = new FloatFilter();
        }
        return unitCost;
    }

    public void setUnitCost(FloatFilter unitCost) {
        this.unitCost = unitCost;
    }

    public IntegerFilter getAvgDemand() {
        return avgDemand;
    }

    public IntegerFilter avgDemand() {
        if (avgDemand == null) {
            avgDemand = new IntegerFilter();
        }
        return avgDemand;
    }

    public void setAvgDemand(IntegerFilter avgDemand) {
        this.avgDemand = avgDemand;
    }

    public FloatFilter getAvgInventoryEffectAfterChange() {
        return avgInventoryEffectAfterChange;
    }

    public FloatFilter avgInventoryEffectAfterChange() {
        if (avgInventoryEffectAfterChange == null) {
            avgInventoryEffectAfterChange = new FloatFilter();
        }
        return avgInventoryEffectAfterChange;
    }

    public void setAvgInventoryEffectAfterChange(FloatFilter avgInventoryEffectAfterChange) {
        this.avgInventoryEffectAfterChange = avgInventoryEffectAfterChange;
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

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public IntegerFilter getNewSAPSafetyStock() {
        return newSAPSafetyStock;
    }

    public IntegerFilter newSAPSafetyStock() {
        if (newSAPSafetyStock == null) {
            newSAPSafetyStock = new IntegerFilter();
        }
        return newSAPSafetyStock;
    }

    public void setNewSAPSafetyStock(IntegerFilter newSAPSafetyStock) {
        this.newSAPSafetyStock = newSAPSafetyStock;
    }

    public IntegerFilter getNewSAPSafetyTime() {
        return newSAPSafetyTime;
    }

    public IntegerFilter newSAPSafetyTime() {
        if (newSAPSafetyTime == null) {
            newSAPSafetyTime = new IntegerFilter();
        }
        return newSAPSafetyTime;
    }

    public void setNewSAPSafetyTime(IntegerFilter newSAPSafetyTime) {
        this.newSAPSafetyTime = newSAPSafetyTime;
    }

    public LocalDateFilter getDateNewSS() {
        return dateNewSS;
    }

    public LocalDateFilter dateNewSS() {
        if (dateNewSS == null) {
            dateNewSS = new LocalDateFilter();
        }
        return dateNewSS;
    }

    public void setDateNewSS(LocalDateFilter dateNewSS) {
        this.dateNewSS = dateNewSS;
    }

    public LocalDateFilter getDatNewST() {
        return datNewST;
    }

    public LocalDateFilter datNewST() {
        if (datNewST == null) {
            datNewST = new LocalDateFilter();
        }
        return datNewST;
    }

    public void setDatNewST(LocalDateFilter datNewST) {
        this.datNewST = datNewST;
    }

    public IntegerFilter getPreviousSS() {
        return previousSS;
    }

    public IntegerFilter previousSS() {
        if (previousSS == null) {
            previousSS = new IntegerFilter();
        }
        return previousSS;
    }

    public void setPreviousSS(IntegerFilter previousSS) {
        this.previousSS = previousSS;
    }

    public IntegerFilter getPreviousST() {
        return previousST;
    }

    public IntegerFilter previousST() {
        if (previousST == null) {
            previousST = new IntegerFilter();
        }
        return previousST;
    }

    public void setPreviousST(IntegerFilter previousST) {
        this.previousST = previousST;
    }

    public LocalDateFilter getDatePreviousSS() {
        return datePreviousSS;
    }

    public LocalDateFilter datePreviousSS() {
        if (datePreviousSS == null) {
            datePreviousSS = new LocalDateFilter();
        }
        return datePreviousSS;
    }

    public void setDatePreviousSS(LocalDateFilter datePreviousSS) {
        this.datePreviousSS = datePreviousSS;
    }

    public LocalDateFilter getDatePreviousST() {
        return datePreviousST;
    }

    public LocalDateFilter datePreviousST() {
        if (datePreviousST == null) {
            datePreviousST = new LocalDateFilter();
        }
        return datePreviousST;
    }

    public void setDatePreviousST(LocalDateFilter datePreviousST) {
        this.datePreviousST = datePreviousST;
    }

    public BooleanFilter getToSaveUpdates() {
        return toSaveUpdates;
    }

    public BooleanFilter toSaveUpdates() {
        if (toSaveUpdates == null) {
            toSaveUpdates = new BooleanFilter();
        }
        return toSaveUpdates;
    }

    public void setToSaveUpdates(BooleanFilter toSaveUpdates) {
        this.toSaveUpdates = toSaveUpdates;
    }

    public CoinFilter getCurrency() {
        return currency;
    }

    public CoinFilter currency() {
        if (currency == null) {
            currency = new CoinFilter();
        }
        return currency;
    }

    public void setCurrency(CoinFilter currency) {
        this.currency = currency;
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
        final MaterialCriteria that = (MaterialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(material, that.material) &&
            Objects.equals(description, that.description) &&
            Objects.equals(abcClassification, that.abcClassification) &&
            Objects.equals(plant, that.plant) &&
            Objects.equals(mrpController, that.mrpController) &&
            Objects.equals(avgSupplierDelay, that.avgSupplierDelay) &&
            Objects.equals(maxSupplierDelay, that.maxSupplierDelay) &&
            Objects.equals(serviceLevel, that.serviceLevel) &&
            Objects.equals(currSAPSafetyStock, that.currSAPSafetyStock) &&
            Objects.equals(proposedSST, that.proposedSST) &&
            Objects.equals(deltaSST, that.deltaSST) &&
            Objects.equals(currentSAPSafeTime, that.currentSAPSafeTime) &&
            Objects.equals(proposedST, that.proposedST) &&
            Objects.equals(deltaST, that.deltaST) &&
            Objects.equals(openSAPmd04, that.openSAPmd04) &&
            Objects.equals(currentInventoryValue, that.currentInventoryValue) &&
            Objects.equals(unitCost, that.unitCost) &&
            Objects.equals(avgDemand, that.avgDemand) &&
            Objects.equals(avgInventoryEffectAfterChange, that.avgInventoryEffectAfterChange) &&
            Objects.equals(flagMaterial, that.flagMaterial) &&
            Objects.equals(flagExpirationDate, that.flagExpirationDate) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(newSAPSafetyStock, that.newSAPSafetyStock) &&
            Objects.equals(newSAPSafetyTime, that.newSAPSafetyTime) &&
            Objects.equals(dateNewSS, that.dateNewSS) &&
            Objects.equals(datNewST, that.datNewST) &&
            Objects.equals(previousSS, that.previousSS) &&
            Objects.equals(previousST, that.previousST) &&
            Objects.equals(datePreviousSS, that.datePreviousSS) &&
            Objects.equals(datePreviousST, that.datePreviousST) &&
            Objects.equals(toSaveUpdates, that.toSaveUpdates) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            material,
            description,
            abcClassification,
            plant,
            mrpController,
            avgSupplierDelay,
            maxSupplierDelay,
            serviceLevel,
            currSAPSafetyStock,
            proposedSST,
            deltaSST,
            currentSAPSafeTime,
            proposedST,
            deltaST,
            openSAPmd04,
            currentInventoryValue,
            unitCost,
            avgDemand,
            avgInventoryEffectAfterChange,
            flagMaterial,
            flagExpirationDate,
            comment,
            newSAPSafetyStock,
            newSAPSafetyTime,
            dateNewSS,
            datNewST,
            previousSS,
            previousST,
            datePreviousSS,
            datePreviousST,
            toSaveUpdates,
            currency,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (material != null ? "material=" + material + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (abcClassification != null ? "abcClassification=" + abcClassification + ", " : "") +
            (plant != null ? "plant=" + plant + ", " : "") +
            (mrpController != null ? "mrpController=" + mrpController + ", " : "") +
            (avgSupplierDelay != null ? "avgSupplierDelay=" + avgSupplierDelay + ", " : "") +
            (maxSupplierDelay != null ? "maxSupplierDelay=" + maxSupplierDelay + ", " : "") +
            (serviceLevel != null ? "serviceLevel=" + serviceLevel + ", " : "") +
            (currSAPSafetyStock != null ? "currSAPSafetyStock=" + currSAPSafetyStock + ", " : "") +
            (proposedSST != null ? "proposedSST=" + proposedSST + ", " : "") +
            (deltaSST != null ? "deltaSST=" + deltaSST + ", " : "") +
            (currentSAPSafeTime != null ? "currentSAPSafeTime=" + currentSAPSafeTime + ", " : "") +
            (proposedST != null ? "proposedST=" + proposedST + ", " : "") +
            (deltaST != null ? "deltaST=" + deltaST + ", " : "") +
            (openSAPmd04 != null ? "openSAPmd04=" + openSAPmd04 + ", " : "") +
            (currentInventoryValue != null ? "currentInventoryValue=" + currentInventoryValue + ", " : "") +
            (unitCost != null ? "unitCost=" + unitCost + ", " : "") +
            (avgDemand != null ? "avgDemand=" + avgDemand + ", " : "") +
            (avgInventoryEffectAfterChange != null ? "avgInventoryEffectAfterChange=" + avgInventoryEffectAfterChange + ", " : "") +
            (flagMaterial != null ? "flagMaterial=" + flagMaterial + ", " : "") +
            (flagExpirationDate != null ? "flagExpirationDate=" + flagExpirationDate + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (newSAPSafetyStock != null ? "newSAPSafetyStock=" + newSAPSafetyStock + ", " : "") +
            (newSAPSafetyTime != null ? "newSAPSafetyTime=" + newSAPSafetyTime + ", " : "") +
            (dateNewSS != null ? "dateNewSS=" + dateNewSS + ", " : "") +
            (datNewST != null ? "datNewST=" + datNewST + ", " : "") +
            (previousSS != null ? "previousSS=" + previousSS + ", " : "") +
            (previousST != null ? "previousST=" + previousST + ", " : "") +
            (datePreviousSS != null ? "datePreviousSS=" + datePreviousSS + ", " : "") +
            (datePreviousST != null ? "datePreviousST=" + datePreviousST + ", " : "") +
            (toSaveUpdates != null ? "toSaveUpdates=" + toSaveUpdates + ", " : "") +
            (currency != null ? "currency=" + currency + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
