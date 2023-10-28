package com.bosch.diabo.domain;

import com.bosch.diabo.domain.enumeration.ABCClassification;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Diabo.
 */
@Entity
@Table(name = "diabo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Diabo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "material")
    private String material;

    @Column(name = "material_description")
    private String materialDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_classification")
    private ABCClassification abcClassification;

    @Column(name = "avg_supplier_delay_last_90_days")
    private Integer avgSupplierDelayLast90Days;

    @Column(name = "max_supplier_delay_last_90_days")
    private Integer maxSupplierDelayLast90Days;

    @Column(name = "service_level")
    private Float serviceLevel;

    @Column(name = "current_sap_safety_stock")
    private Integer currentSapSafetyStock;

    @Column(name = "proposed_safety_stock")
    private Integer proposedSafetyStock;

    @Column(name = "delta_safety_stock")
    private Integer deltaSafetyStock;

    @Column(name = "current_sap_safety_time")
    private Integer currentSapSafetyTime;

    @Column(name = "proposed_safety_time")
    private Integer proposedSafetyTime;

    @Column(name = "delta_safety_time")
    private Integer deltaSafetyTime;

    @Column(name = "open_sap_md_04")
    private String openSapMd04;

    @Column(name = "current_inventory_value")
    private Float currentInventoryValue;

    @Column(name = "average_inventory_effect_after_change")
    private Float averageInventoryEffectAfterChange;

    @Column(name = "new_sap_safety_stock")
    private Integer newSapSafetyStock;

    @Column(name = "new_sap_safety_time")
    private Integer newSapSafetyTime;

    @Column(name = "select_entries_for_change_in_sap")
    private Boolean selectEntriesForChangeInSap;

    @Column(name = "flag_material_as_special_case")
    private Boolean flagMaterialAsSpecialCase;

    @Column(name = "commentary")
    private String commentary;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Diabo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterial() {
        return this.material;
    }

    public Diabo material(String material) {
        this.setMaterial(material);
        return this;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialDescription() {
        return this.materialDescription;
    }

    public Diabo materialDescription(String materialDescription) {
        this.setMaterialDescription(materialDescription);
        return this;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public ABCClassification getAbcClassification() {
        return this.abcClassification;
    }

    public Diabo abcClassification(ABCClassification abcClassification) {
        this.setAbcClassification(abcClassification);
        return this;
    }

    public void setAbcClassification(ABCClassification abcClassification) {
        this.abcClassification = abcClassification;
    }

    public Integer getAvgSupplierDelayLast90Days() {
        return this.avgSupplierDelayLast90Days;
    }

    public Diabo avgSupplierDelayLast90Days(Integer avgSupplierDelayLast90Days) {
        this.setAvgSupplierDelayLast90Days(avgSupplierDelayLast90Days);
        return this;
    }

    public void setAvgSupplierDelayLast90Days(Integer avgSupplierDelayLast90Days) {
        this.avgSupplierDelayLast90Days = avgSupplierDelayLast90Days;
    }

    public Integer getMaxSupplierDelayLast90Days() {
        return this.maxSupplierDelayLast90Days;
    }

    public Diabo maxSupplierDelayLast90Days(Integer maxSupplierDelayLast90Days) {
        this.setMaxSupplierDelayLast90Days(maxSupplierDelayLast90Days);
        return this;
    }

    public void setMaxSupplierDelayLast90Days(Integer maxSupplierDelayLast90Days) {
        this.maxSupplierDelayLast90Days = maxSupplierDelayLast90Days;
    }

    public Float getServiceLevel() {
        return this.serviceLevel;
    }

    public Diabo serviceLevel(Float serviceLevel) {
        this.setServiceLevel(serviceLevel);
        return this;
    }

    public void setServiceLevel(Float serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Integer getCurrentSapSafetyStock() {
        return this.currentSapSafetyStock;
    }

    public Diabo currentSapSafetyStock(Integer currentSapSafetyStock) {
        this.setCurrentSapSafetyStock(currentSapSafetyStock);
        return this;
    }

    public void setCurrentSapSafetyStock(Integer currentSapSafetyStock) {
        this.currentSapSafetyStock = currentSapSafetyStock;
    }

    public Integer getProposedSafetyStock() {
        return this.proposedSafetyStock;
    }

    public Diabo proposedSafetyStock(Integer proposedSafetyStock) {
        this.setProposedSafetyStock(proposedSafetyStock);
        return this;
    }

    public void setProposedSafetyStock(Integer proposedSafetyStock) {
        this.proposedSafetyStock = proposedSafetyStock;
    }

    public Integer getDeltaSafetyStock() {
        return this.deltaSafetyStock;
    }

    public Diabo deltaSafetyStock(Integer deltaSafetyStock) {
        this.setDeltaSafetyStock(deltaSafetyStock);
        return this;
    }

    public void setDeltaSafetyStock(Integer deltaSafetyStock) {
        this.deltaSafetyStock = deltaSafetyStock;
    }

    public Integer getCurrentSapSafetyTime() {
        return this.currentSapSafetyTime;
    }

    public Diabo currentSapSafetyTime(Integer currentSapSafetyTime) {
        this.setCurrentSapSafetyTime(currentSapSafetyTime);
        return this;
    }

    public void setCurrentSapSafetyTime(Integer currentSapSafetyTime) {
        this.currentSapSafetyTime = currentSapSafetyTime;
    }

    public Integer getProposedSafetyTime() {
        return this.proposedSafetyTime;
    }

    public Diabo proposedSafetyTime(Integer proposedSafetyTime) {
        this.setProposedSafetyTime(proposedSafetyTime);
        return this;
    }

    public void setProposedSafetyTime(Integer proposedSafetyTime) {
        this.proposedSafetyTime = proposedSafetyTime;
    }

    public Integer getDeltaSafetyTime() {
        return this.deltaSafetyTime;
    }

    public Diabo deltaSafetyTime(Integer deltaSafetyTime) {
        this.setDeltaSafetyTime(deltaSafetyTime);
        return this;
    }

    public void setDeltaSafetyTime(Integer deltaSafetyTime) {
        this.deltaSafetyTime = deltaSafetyTime;
    }

    public String getOpenSapMd04() {
        return this.openSapMd04;
    }

    public Diabo openSapMd04(String openSapMd04) {
        this.setOpenSapMd04(openSapMd04);
        return this;
    }

    public void setOpenSapMd04(String openSapMd04) {
        this.openSapMd04 = openSapMd04;
    }

    public Float getCurrentInventoryValue() {
        return this.currentInventoryValue;
    }

    public Diabo currentInventoryValue(Float currentInventoryValue) {
        this.setCurrentInventoryValue(currentInventoryValue);
        return this;
    }

    public void setCurrentInventoryValue(Float currentInventoryValue) {
        this.currentInventoryValue = currentInventoryValue;
    }

    public Float getAverageInventoryEffectAfterChange() {
        return this.averageInventoryEffectAfterChange;
    }

    public Diabo averageInventoryEffectAfterChange(Float averageInventoryEffectAfterChange) {
        this.setAverageInventoryEffectAfterChange(averageInventoryEffectAfterChange);
        return this;
    }

    public void setAverageInventoryEffectAfterChange(Float averageInventoryEffectAfterChange) {
        this.averageInventoryEffectAfterChange = averageInventoryEffectAfterChange;
    }

    public Integer getNewSapSafetyStock() {
        return this.newSapSafetyStock;
    }

    public Diabo newSapSafetyStock(Integer newSapSafetyStock) {
        this.setNewSapSafetyStock(newSapSafetyStock);
        return this;
    }

    public void setNewSapSafetyStock(Integer newSapSafetyStock) {
        this.newSapSafetyStock = newSapSafetyStock;
    }

    public Integer getNewSapSafetyTime() {
        return this.newSapSafetyTime;
    }

    public Diabo newSapSafetyTime(Integer newSapSafetyTime) {
        this.setNewSapSafetyTime(newSapSafetyTime);
        return this;
    }

    public void setNewSapSafetyTime(Integer newSapSafetyTime) {
        this.newSapSafetyTime = newSapSafetyTime;
    }

    public Boolean getSelectEntriesForChangeInSap() {
        return this.selectEntriesForChangeInSap;
    }

    public Diabo selectEntriesForChangeInSap(Boolean selectEntriesForChangeInSap) {
        this.setSelectEntriesForChangeInSap(selectEntriesForChangeInSap);
        return this;
    }

    public void setSelectEntriesForChangeInSap(Boolean selectEntriesForChangeInSap) {
        this.selectEntriesForChangeInSap = selectEntriesForChangeInSap;
    }

    public Boolean getFlagMaterialAsSpecialCase() {
        return this.flagMaterialAsSpecialCase;
    }

    public Diabo flagMaterialAsSpecialCase(Boolean flagMaterialAsSpecialCase) {
        this.setFlagMaterialAsSpecialCase(flagMaterialAsSpecialCase);
        return this;
    }

    public void setFlagMaterialAsSpecialCase(Boolean flagMaterialAsSpecialCase) {
        this.flagMaterialAsSpecialCase = flagMaterialAsSpecialCase;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public Diabo commentary(String commentary) {
        this.setCommentary(commentary);
        return this;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diabo)) {
            return false;
        }
        return id != null && id.equals(((Diabo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diabo{" +
            "id=" + getId() +
            ", material='" + getMaterial() + "'" +
            ", materialDescription='" + getMaterialDescription() + "'" +
            ", abcClassification='" + getAbcClassification() + "'" +
            ", avgSupplierDelayLast90Days=" + getAvgSupplierDelayLast90Days() +
            ", maxSupplierDelayLast90Days=" + getMaxSupplierDelayLast90Days() +
            ", serviceLevel=" + getServiceLevel() +
            ", currentSapSafetyStock=" + getCurrentSapSafetyStock() +
            ", proposedSafetyStock=" + getProposedSafetyStock() +
            ", deltaSafetyStock=" + getDeltaSafetyStock() +
            ", currentSapSafetyTime=" + getCurrentSapSafetyTime() +
            ", proposedSafetyTime=" + getProposedSafetyTime() +
            ", deltaSafetyTime=" + getDeltaSafetyTime() +
            ", openSapMd04='" + getOpenSapMd04() + "'" +
            ", currentInventoryValue=" + getCurrentInventoryValue() +
            ", averageInventoryEffectAfterChange=" + getAverageInventoryEffectAfterChange() +
            ", newSapSafetyStock=" + getNewSapSafetyStock() +
            ", newSapSafetyTime=" + getNewSapSafetyTime() +
            ", selectEntriesForChangeInSap='" + getSelectEntriesForChangeInSap() + "'" +
            ", flagMaterialAsSpecialCase='" + getFlagMaterialAsSpecialCase() + "'" +
            ", commentary='" + getCommentary() + "'" +
            "}";
    }
}
