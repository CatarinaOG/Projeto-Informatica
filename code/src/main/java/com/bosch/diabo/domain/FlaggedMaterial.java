package com.bosch.diabo.domain;

import com.bosch.diabo.domain.enumeration.ABCClassification;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCEGENERATOR")
    @SequenceGenerator(name = "SEQUENCEGENERATOR")
    @Column(name = "id")
    private Long id;

    @Column(name = "material")
    private String material;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_classification")
    private ABCClassification abcClassification;

    @Column(name = "plant")
    private Integer plant;

    @Column(name = "mrp_controller")
    private String mrpController;

    @Column(name = "flag_material")
    private Boolean flagMaterial;

    @Column(name = "flag_expiration_date")
    private LocalDate flagExpirationDate;

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

    public Integer getPlant() {
        return this.plant;
    }

    public FlaggedMaterial plant(Integer plant) {
        this.setPlant(plant);
        return this;
    }

    public void setPlant(Integer plant) {
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
            ", plant=" + getPlant() +
            ", mrpController='" + getMrpController() + "'" +
            ", flagMaterial='" + getFlagMaterial() + "'" +
            ", flagExpirationDate='" + getFlagExpirationDate() + "'" +
            "}";
    }
}
