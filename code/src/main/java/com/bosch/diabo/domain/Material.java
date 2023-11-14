package com.bosch.diabo.domain;

import com.bosch.diabo.domain.enumeration.ABCClassification;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "material")
    private String material;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_classification")
    private ABCClassification abcClassification;

    @Column(name = "avg_supplier_delay")
    private Float avgSupplierDelay;

    @Column(name = "max_supplier_delay")
    private Float maxSupplierDelay;

    @Column(name = "service_level")
    private Float serviceLevel;

    @Column(name = "curr_sap_safety_stock")
    private Integer currSAPSafetyStock;

    @Column(name = "proposed_sst")
    private Integer proposedSST;

    @Column(name = "delta_sst")
    private Integer deltaSST;

    @Column(name = "current_sap_safe_time")
    private Integer currentSAPSafeTime;

    @Column(name = "proposed_st")
    private Integer proposedST;

    @Column(name = "delta_st")
    private Integer deltaST;

    @Column(name = "open_sa_pmd_04")
    private String openSAPmd04;

    @Column(name = "current_inventory_value")
    private Float currentInventoryValue;

    @Column(name = "unit_cost")
    private Float unitCost;

    @Column(name = "avg_demand")
    private Integer avgDemand;

    @Column(name = "avg_inventory_effect_after_change")
    private Float avgInventoryEffectAfterChange;

    @Column(name = "new_sap_safety_stock")
    private Integer newSAPSafetyStock;

    @Column(name = "new_sap_safety_time")
    private Integer newSAPSafetyTime;

    @Column(name = "flag_material")
    private Boolean flagMaterial;

    @Column(name = "jhi_comment")
    private String comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Material id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterial() {
        return this.material;
    }

    public Material material(String material) {
        this.setMaterial(material);
        return this;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return this.description;
    }

    public Material description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ABCClassification getAbcClassification() {
        return this.abcClassification;
    }

    public Material abcClassification(ABCClassification abcClassification) {
        this.setAbcClassification(abcClassification);
        return this;
    }

    public void setAbcClassification(ABCClassification abcClassification) {
        this.abcClassification = abcClassification;
    }

    public Float getAvgSupplierDelay() {
        return this.avgSupplierDelay;
    }

    public Material avgSupplierDelay(Float avgSupplierDelay) {
        this.setAvgSupplierDelay(avgSupplierDelay);
        return this;
    }

    public void setAvgSupplierDelay(Float avgSupplierDelay) {
        this.avgSupplierDelay = avgSupplierDelay;
    }

    public Float getMaxSupplierDelay() {
        return this.maxSupplierDelay;
    }

    public Material maxSupplierDelay(Float maxSupplierDelay) {
        this.setMaxSupplierDelay(maxSupplierDelay);
        return this;
    }

    public void setMaxSupplierDelay(Float maxSupplierDelay) {
        this.maxSupplierDelay = maxSupplierDelay;
    }

    public Float getServiceLevel() {
        return this.serviceLevel;
    }

    public Material serviceLevel(Float serviceLevel) {
        this.setServiceLevel(serviceLevel);
        return this;
    }

    public void setServiceLevel(Float serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Integer getCurrSAPSafetyStock() {
        return this.currSAPSafetyStock;
    }

    public Material currSAPSafetyStock(Integer currSAPSafetyStock) {
        this.setCurrSAPSafetyStock(currSAPSafetyStock);
        return this;
    }

    public void setCurrSAPSafetyStock(Integer currSAPSafetyStock) {
        this.currSAPSafetyStock = currSAPSafetyStock;
    }

    public Integer getProposedSST() {
        return this.proposedSST;
    }

    public Material proposedSST(Integer proposedSST) {
        this.setProposedSST(proposedSST);
        return this;
    }

    public void setProposedSST(Integer proposedSST) {
        this.proposedSST = proposedSST;
    }

    public Integer getDeltaSST() {
        return this.deltaSST;
    }

    public Material deltaSST(Integer deltaSST) {
        this.setDeltaSST(deltaSST);
        return this;
    }

    public void setDeltaSST(Integer deltaSST) {
        this.deltaSST = deltaSST;
    }

    public Integer getCurrentSAPSafeTime() {
        return this.currentSAPSafeTime;
    }

    public Material currentSAPSafeTime(Integer currentSAPSafeTime) {
        this.setCurrentSAPSafeTime(currentSAPSafeTime);
        return this;
    }

    public void setCurrentSAPSafeTime(Integer currentSAPSafeTime) {
        this.currentSAPSafeTime = currentSAPSafeTime;
    }

    public Integer getProposedST() {
        return this.proposedST;
    }

    public Material proposedST(Integer proposedST) {
        this.setProposedST(proposedST);
        return this;
    }

    public void setProposedST(Integer proposedST) {
        this.proposedST = proposedST;
    }

    public Integer getDeltaST() {
        return this.deltaST;
    }

    public Material deltaST(Integer deltaST) {
        this.setDeltaST(deltaST);
        return this;
    }

    public void setDeltaST(Integer deltaST) {
        this.deltaST = deltaST;
    }

    public String getOpenSAPmd04() {
        return this.openSAPmd04;
    }

    public Material openSAPmd04(String openSAPmd04) {
        this.setOpenSAPmd04(openSAPmd04);
        return this;
    }

    public void setOpenSAPmd04(String openSAPmd04) {
        this.openSAPmd04 = openSAPmd04;
    }

    public Float getCurrentInventoryValue() {
        return this.currentInventoryValue;
    }

    public Material currentInventoryValue(Float currentInventoryValue) {
        this.setCurrentInventoryValue(currentInventoryValue);
        return this;
    }

    public void setCurrentInventoryValue(Float currentInventoryValue) {
        this.currentInventoryValue = currentInventoryValue;
    }

    public Float getUnitCost() {
        return this.unitCost;
    }

    public Material unitCost(Float unitCost) {
        this.setUnitCost(unitCost);
        return this;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getAvgDemand() {
        return this.avgDemand;
    }

    public Material avgDemand(Integer avgDemand) {
        this.setAvgDemand(avgDemand);
        return this;
    }

    public void setAvgDemand(Integer avgDemand) {
        this.avgDemand = avgDemand;
    }

    public Float getAvgInventoryEffectAfterChange() {
        return this.avgInventoryEffectAfterChange;
    }

    public Material avgInventoryEffectAfterChange(Float avgInventoryEffectAfterChange) {
        this.setAvgInventoryEffectAfterChange(avgInventoryEffectAfterChange);
        return this;
    }

    public void setAvgInventoryEffectAfterChange(Float avgInventoryEffectAfterChange) {
        this.avgInventoryEffectAfterChange = avgInventoryEffectAfterChange;
    }

    public Integer getNewSAPSafetyStock() {
        return this.newSAPSafetyStock;
    }

    public Material newSAPSafetyStock(Integer newSAPSafetyStock) {
        this.setNewSAPSafetyStock(newSAPSafetyStock);
        return this;
    }

    public void setNewSAPSafetyStock(Integer newSAPSafetyStock) {
        this.newSAPSafetyStock = newSAPSafetyStock;
    }

    public Integer getNewSAPSafetyTime() {
        return this.newSAPSafetyTime;
    }

    public Material newSAPSafetyTime(Integer newSAPSafetyTime) {
        this.setNewSAPSafetyTime(newSAPSafetyTime);
        return this;
    }

    public void setNewSAPSafetyTime(Integer newSAPSafetyTime) {
        this.newSAPSafetyTime = newSAPSafetyTime;
    }

    public Boolean getFlagMaterial() {
        return this.flagMaterial;
    }

    public Material flagMaterial(Boolean flagMaterial) {
        this.setFlagMaterial(flagMaterial);
        return this;
    }

    public void setFlagMaterial(Boolean flagMaterial) {
        this.flagMaterial = flagMaterial;
    }

    public String getComment() {
        return this.comment;
    }

    public Material comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Material)) {
            return false;
        }
        return id != null && id.equals(((Material) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", material='" + getMaterial() + "'" +
            ", description='" + getDescription() + "'" +
            ", abcClassification='" + getAbcClassification() + "'" +
            ", avgSupplierDelay=" + getAvgSupplierDelay() +
            ", maxSupplierDelay=" + getMaxSupplierDelay() +
            ", serviceLevel=" + getServiceLevel() +
            ", currSAPSafetyStock=" + getCurrSAPSafetyStock() +
            ", proposedSST=" + getProposedSST() +
            ", deltaSST=" + getDeltaSST() +
            ", currentSAPSafeTime=" + getCurrentSAPSafeTime() +
            ", proposedST=" + getProposedST() +
            ", deltaST=" + getDeltaST() +
            ", openSAPmd04='" + getOpenSAPmd04() + "'" +
            ", currentInventoryValue=" + getCurrentInventoryValue() +
            ", unitCost=" + getUnitCost() +
            ", avgDemand=" + getAvgDemand() +
            ", avgInventoryEffectAfterChange=" + getAvgInventoryEffectAfterChange() +
            ", newSAPSafetyStock=" + getNewSAPSafetyStock() +
            ", newSAPSafetyTime=" + getNewSAPSafetyTime() +
            ", flagMaterial='" + getFlagMaterial() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
