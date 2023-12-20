package com.bosch.diabo.domain;

import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.domain.enumeration.Coin;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FlaggedMaterial.
 */
@Entity
@Table(name = "flagged_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FlaggedMaterial implements Serializable {

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

    @Column(name = "plant")
    private String plant;

    @Column(name = "mrp_controller")
    private String mrpController;

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

    @Column(name = "flag_material")
    private Boolean flagMaterial;

    @Column(name = "flag_expiration_date")
    private LocalDate flagExpirationDate;

    @Column(name = "value_of_updated_ss")
    private Integer valueOfUpdatedSS;

    @Column(name = "value_of_updated_st")
    private Integer valueOfUpdatedST;

    @Column(name = "date_of_updated_ss")
    private LocalDate dateOfUpdatedSS;

    @Column(name = "date_of_updated_st")
    private LocalDate dateOfUpdatedST;

    @Column(name = "to_save_updates")
    private Boolean toSaveUpdates;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Coin currency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FlaggedMaterial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterial() {
        return this.material;
    }

    public FlaggedMaterial material(String material) {
        this.setMaterial(material);
        return this;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return this.description;
    }

    public FlaggedMaterial description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ABCClassification getAbcClassification() {
        return this.abcClassification;
    }

    public FlaggedMaterial abcClassification(ABCClassification abcClassification) {
        this.setAbcClassification(abcClassification);
        return this;
    }

    public void setAbcClassification(ABCClassification abcClassification) {
        this.abcClassification = abcClassification;
    }

    public Float getAvgSupplierDelay() {
        return this.avgSupplierDelay;
    }

    public FlaggedMaterial avgSupplierDelay(Float avgSupplierDelay) {
        this.setAvgSupplierDelay(avgSupplierDelay);
        return this;
    }

    public void setAvgSupplierDelay(Float avgSupplierDelay) {
        this.avgSupplierDelay = avgSupplierDelay;
    }

    public Float getMaxSupplierDelay() {
        return this.maxSupplierDelay;
    }

    public FlaggedMaterial maxSupplierDelay(Float maxSupplierDelay) {
        this.setMaxSupplierDelay(maxSupplierDelay);
        return this;
    }

    public void setMaxSupplierDelay(Float maxSupplierDelay) {
        this.maxSupplierDelay = maxSupplierDelay;
    }

    public Float getServiceLevel() {
        return this.serviceLevel;
    }

    public FlaggedMaterial serviceLevel(Float serviceLevel) {
        this.setServiceLevel(serviceLevel);
        return this;
    }

    public void setServiceLevel(Float serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getPlant() {
        return this.plant;
    }

    public FlaggedMaterial plant(String plant) {
        this.setPlant(plant);
        return this;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getMrpController() {
        return this.mrpController;
    }

    public FlaggedMaterial mrpController(String mrpController) {
        this.setMrpController(mrpController);
        return this;
    }

    public void setMrpController(String mrpController) {
        this.mrpController = mrpController;
    }

    public Integer getCurrSAPSafetyStock() {
        return this.currSAPSafetyStock;
    }

    public FlaggedMaterial currSAPSafetyStock(Integer currSAPSafetyStock) {
        this.setCurrSAPSafetyStock(currSAPSafetyStock);
        return this;
    }

    public void setCurrSAPSafetyStock(Integer currSAPSafetyStock) {
        this.currSAPSafetyStock = currSAPSafetyStock;
    }

    public Integer getProposedSST() {
        return this.proposedSST;
    }

    public FlaggedMaterial proposedSST(Integer proposedSST) {
        this.setProposedSST(proposedSST);
        return this;
    }

    public void setProposedSST(Integer proposedSST) {
        this.proposedSST = proposedSST;
    }

    public Integer getDeltaSST() {
        return this.deltaSST;
    }

    public FlaggedMaterial deltaSST(Integer deltaSST) {
        this.setDeltaSST(deltaSST);
        return this;
    }

    public void setDeltaSST(Integer deltaSST) {
        this.deltaSST = deltaSST;
    }

    public Integer getCurrentSAPSafeTime() {
        return this.currentSAPSafeTime;
    }

    public FlaggedMaterial currentSAPSafeTime(Integer currentSAPSafeTime) {
        this.setCurrentSAPSafeTime(currentSAPSafeTime);
        return this;
    }

    public void setCurrentSAPSafeTime(Integer currentSAPSafeTime) {
        this.currentSAPSafeTime = currentSAPSafeTime;
    }

    public Integer getProposedST() {
        return this.proposedST;
    }

    public FlaggedMaterial proposedST(Integer proposedST) {
        this.setProposedST(proposedST);
        return this;
    }

    public void setProposedST(Integer proposedST) {
        this.proposedST = proposedST;
    }

    public Integer getDeltaST() {
        return this.deltaST;
    }

    public FlaggedMaterial deltaST(Integer deltaST) {
        this.setDeltaST(deltaST);
        return this;
    }

    public void setDeltaST(Integer deltaST) {
        this.deltaST = deltaST;
    }

    public String getOpenSAPmd04() {
        return this.openSAPmd04;
    }

    public FlaggedMaterial openSAPmd04(String openSAPmd04) {
        this.setOpenSAPmd04(openSAPmd04);
        return this;
    }

    public void setOpenSAPmd04(String openSAPmd04) {
        this.openSAPmd04 = openSAPmd04;
    }

    public Float getCurrentInventoryValue() {
        return this.currentInventoryValue;
    }

    public FlaggedMaterial currentInventoryValue(Float currentInventoryValue) {
        this.setCurrentInventoryValue(currentInventoryValue);
        return this;
    }

    public void setCurrentInventoryValue(Float currentInventoryValue) {
        this.currentInventoryValue = currentInventoryValue;
    }

    public Float getUnitCost() {
        return this.unitCost;
    }

    public FlaggedMaterial unitCost(Float unitCost) {
        this.setUnitCost(unitCost);
        return this;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getAvgDemand() {
        return this.avgDemand;
    }

    public FlaggedMaterial avgDemand(Integer avgDemand) {
        this.setAvgDemand(avgDemand);
        return this;
    }

    public void setAvgDemand(Integer avgDemand) {
        this.avgDemand = avgDemand;
    }

    public Float getAvgInventoryEffectAfterChange() {
        return this.avgInventoryEffectAfterChange;
    }

    public FlaggedMaterial avgInventoryEffectAfterChange(Float avgInventoryEffectAfterChange) {
        this.setAvgInventoryEffectAfterChange(avgInventoryEffectAfterChange);
        return this;
    }

    public void setAvgInventoryEffectAfterChange(Float avgInventoryEffectAfterChange) {
        this.avgInventoryEffectAfterChange = avgInventoryEffectAfterChange;
    }

    public Boolean getFlagMaterial() {
        return this.flagMaterial;
    }

    public FlaggedMaterial flagMaterial(Boolean flagMaterial) {
        this.setFlagMaterial(flagMaterial);
        return this;
    }

    public void setFlagMaterial(Boolean flagMaterial) {
        this.flagMaterial = flagMaterial;
    }

    public LocalDate getFlagExpirationDate() {
        return this.flagExpirationDate;
    }

    public FlaggedMaterial flagExpirationDate(LocalDate flagExpirationDate) {
        this.setFlagExpirationDate(flagExpirationDate);
        return this;
    }

    public void setFlagExpirationDate(LocalDate flagExpirationDate) {
        this.flagExpirationDate = flagExpirationDate;
    }

    public Integer getValueOfUpdatedSS() {
        return this.valueOfUpdatedSS;
    }

    public FlaggedMaterial valueOfUpdatedSS(Integer valueOfUpdatedSS) {
        this.setValueOfUpdatedSS(valueOfUpdatedSS);
        return this;
    }

    public void setValueOfUpdatedSS(Integer valueOfUpdatedSS) {
        this.valueOfUpdatedSS = valueOfUpdatedSS;
    }

    public Integer getValueOfUpdatedST() {
        return this.valueOfUpdatedST;
    }

    public FlaggedMaterial valueOfUpdatedST(Integer valueOfUpdatedST) {
        this.setValueOfUpdatedST(valueOfUpdatedST);
        return this;
    }

    public void setValueOfUpdatedST(Integer valueOfUpdatedST) {
        this.valueOfUpdatedST = valueOfUpdatedST;
    }

    public LocalDate getDateOfUpdatedSS() {
        return this.dateOfUpdatedSS;
    }

    public FlaggedMaterial dateOfUpdatedSS(LocalDate dateOfUpdatedSS) {
        this.setDateOfUpdatedSS(dateOfUpdatedSS);
        return this;
    }

    public void setDateOfUpdatedSS(LocalDate dateOfUpdatedSS) {
        this.dateOfUpdatedSS = dateOfUpdatedSS;
    }

    public LocalDate getDateOfUpdatedST() {
        return this.dateOfUpdatedST;
    }

    public FlaggedMaterial dateOfUpdatedST(LocalDate dateOfUpdatedST) {
        this.setDateOfUpdatedST(dateOfUpdatedST);
        return this;
    }

    public void setDateOfUpdatedST(LocalDate dateOfUpdatedST) {
        this.dateOfUpdatedST = dateOfUpdatedST;
    }

    public Boolean getToSaveUpdates() {
        return this.toSaveUpdates;
    }

    public FlaggedMaterial toSaveUpdates(Boolean toSaveUpdates) {
        this.setToSaveUpdates(toSaveUpdates);
        return this;
    }

    public void setToSaveUpdates(Boolean toSaveUpdates) {
        this.toSaveUpdates = toSaveUpdates;
    }

    public Coin getCurrency() {
        return this.currency;
    }

    public FlaggedMaterial currency(Coin currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Coin currency) {
        this.currency = currency;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlaggedMaterial)) {
            return false;
        }
        return id != null && id.equals(((FlaggedMaterial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FlaggedMaterial{" +
            "id=" + getId() +
            ", material='" + getMaterial() + "'" +
            ", description='" + getDescription() + "'" +
            ", abcClassification='" + getAbcClassification() + "'" +
            ", avgSupplierDelay=" + getAvgSupplierDelay() +
            ", maxSupplierDelay=" + getMaxSupplierDelay() +
            ", serviceLevel=" + getServiceLevel() +
            ", plant='" + getPlant() + "'" +
            ", mrpController='" + getMrpController() + "'" +
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
            ", flagMaterial='" + getFlagMaterial() + "'" +
            ", flagExpirationDate='" + getFlagExpirationDate() + "'" +
            ", valueOfUpdatedSS=" + getValueOfUpdatedSS() +
            ", valueOfUpdatedST=" + getValueOfUpdatedST() +
            ", dateOfUpdatedSS='" + getDateOfUpdatedSS() + "'" +
            ", dateOfUpdatedST='" + getDateOfUpdatedST() + "'" +
            ", toSaveUpdates='" + getToSaveUpdates() + "'" +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
