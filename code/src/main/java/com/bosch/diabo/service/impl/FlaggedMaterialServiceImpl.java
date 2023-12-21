package com.bosch.diabo.service.impl;

import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.Material;
import com.bosch.diabo.repository.FlaggedMaterialRepository;
import com.bosch.diabo.service.FlaggedMaterialService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FlaggedMaterial}.
 */
@Service
@Transactional
public class FlaggedMaterialServiceImpl implements FlaggedMaterialService {

    private final Logger log = LoggerFactory.getLogger(FlaggedMaterialServiceImpl.class);

    private final FlaggedMaterialRepository flaggedMaterialRepository;

    public FlaggedMaterialServiceImpl(FlaggedMaterialRepository flaggedMaterialRepository) {
        this.flaggedMaterialRepository = flaggedMaterialRepository;
    }

    @Override
    public FlaggedMaterial save(FlaggedMaterial flaggedMaterial) {
        log.debug("Request to save FlaggedMaterial : {}", flaggedMaterial);
        return flaggedMaterialRepository.save(flaggedMaterial);
    }

    @Override
    public FlaggedMaterial update(FlaggedMaterial flaggedMaterial) {
        log.debug("Request to update FlaggedMaterial : {}", flaggedMaterial);
        return flaggedMaterialRepository.save(flaggedMaterial);
    }

    @Override
    public Optional<FlaggedMaterial> partialUpdate(FlaggedMaterial flaggedMaterial) {
        log.debug("Request to partially update FlaggedMaterial : {}", flaggedMaterial);

        return flaggedMaterialRepository
            .findById(flaggedMaterial.getId())
            .map(existingFlaggedMaterial -> {
                if (flaggedMaterial.getMaterial() != null) {
                    existingFlaggedMaterial.setMaterial(flaggedMaterial.getMaterial());
                }
                if (flaggedMaterial.getDescription() != null) {
                    existingFlaggedMaterial.setDescription(flaggedMaterial.getDescription());
                }
                if (flaggedMaterial.getAbcClassification() != null) {
                    existingFlaggedMaterial.setAbcClassification(flaggedMaterial.getAbcClassification());
                }
                if (flaggedMaterial.getAvgSupplierDelay() != null) {
                    existingFlaggedMaterial.setAvgSupplierDelay(flaggedMaterial.getAvgSupplierDelay());
                }
                if (flaggedMaterial.getMaxSupplierDelay() != null) {
                    existingFlaggedMaterial.setMaxSupplierDelay(flaggedMaterial.getMaxSupplierDelay());
                }
                if (flaggedMaterial.getServiceLevel() != null) {
                    existingFlaggedMaterial.setServiceLevel(flaggedMaterial.getServiceLevel());
                }
                if (flaggedMaterial.getPlant() != null) {
                    existingFlaggedMaterial.setPlant(flaggedMaterial.getPlant());
                }
                if (flaggedMaterial.getMrpController() != null) {
                    existingFlaggedMaterial.setMrpController(flaggedMaterial.getMrpController());
                }
                if (flaggedMaterial.getCurrSAPSafetyStock() != null) {
                    existingFlaggedMaterial.setCurrSAPSafetyStock(flaggedMaterial.getCurrSAPSafetyStock());
                }
                if (flaggedMaterial.getProposedSST() != null) {
                    existingFlaggedMaterial.setProposedSST(flaggedMaterial.getProposedSST());
                }
                if (flaggedMaterial.getDeltaSST() != null) {
                    existingFlaggedMaterial.setDeltaSST(flaggedMaterial.getDeltaSST());
                }
                if (flaggedMaterial.getCurrentSAPSafeTime() != null) {
                    existingFlaggedMaterial.setCurrentSAPSafeTime(flaggedMaterial.getCurrentSAPSafeTime());
                }
                if (flaggedMaterial.getProposedST() != null) {
                    existingFlaggedMaterial.setProposedST(flaggedMaterial.getProposedST());
                }
                if (flaggedMaterial.getDeltaST() != null) {
                    existingFlaggedMaterial.setDeltaST(flaggedMaterial.getDeltaST());
                }
                if (flaggedMaterial.getOpenSAPmd04() != null) {
                    existingFlaggedMaterial.setOpenSAPmd04(flaggedMaterial.getOpenSAPmd04());
                }
                if (flaggedMaterial.getCurrentInventoryValue() != null) {
                    existingFlaggedMaterial.setCurrentInventoryValue(flaggedMaterial.getCurrentInventoryValue());
                }
                if (flaggedMaterial.getUnitCost() != null) {
                    existingFlaggedMaterial.setUnitCost(flaggedMaterial.getUnitCost());
                }
                if (flaggedMaterial.getAvgDemand() != null) {
                    existingFlaggedMaterial.setAvgDemand(flaggedMaterial.getAvgDemand());
                }
                if (flaggedMaterial.getAvgInventoryEffectAfterChange() != null) {
                    existingFlaggedMaterial.setAvgInventoryEffectAfterChange(flaggedMaterial.getAvgInventoryEffectAfterChange());
                }
                if (flaggedMaterial.getFlagMaterial() != null) {
                    existingFlaggedMaterial.setFlagMaterial(flaggedMaterial.getFlagMaterial());
                }
                if (flaggedMaterial.getFlagExpirationDate() != null) {
                    existingFlaggedMaterial.setFlagExpirationDate(flaggedMaterial.getFlagExpirationDate());
                }
                if (flaggedMaterial.getValueOfUpdatedSS() != null) {
                    existingFlaggedMaterial.setValueOfUpdatedSS(flaggedMaterial.getValueOfUpdatedSS());
                }
                if (flaggedMaterial.getValueOfUpdatedST() != null) {
                    existingFlaggedMaterial.setValueOfUpdatedST(flaggedMaterial.getValueOfUpdatedST());
                }
                if (flaggedMaterial.getDateOfUpdatedSS() != null) {
                    existingFlaggedMaterial.setDateOfUpdatedSS(flaggedMaterial.getDateOfUpdatedSS());
                }
                if (flaggedMaterial.getDateOfUpdatedST() != null) {
                    existingFlaggedMaterial.setDateOfUpdatedST(flaggedMaterial.getDateOfUpdatedST());
                }
                if (flaggedMaterial.getToSaveUpdates() != null) {
                    existingFlaggedMaterial.setToSaveUpdates(flaggedMaterial.getToSaveUpdates());
                }
                if (flaggedMaterial.getCurrency() != null) {
                    existingFlaggedMaterial.setCurrency(flaggedMaterial.getCurrency());
                }

                return existingFlaggedMaterial;
            })
            .map(flaggedMaterialRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlaggedMaterial> findAll() {
        log.debug("Request to get all FlaggedMaterials");
        return flaggedMaterialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlaggedMaterial> findOne(Long id) {
        log.debug("Request to get FlaggedMaterial : {}", id);
        return flaggedMaterialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FlaggedMaterial : {}", id);
        flaggedMaterialRepository.deleteById(id);
    }

    @Override
    public void updateFlaggedMaterials(Boolean flag, Material material){
        log.debug("Request to update FlaggedMaterial from a Material : {}", material.getId());
        
        if(flag) createOrUpdateFlagMaterial(material);
        else deleteFlaggedMaterial(material.getMaterial());

    }

    public void createOrUpdateFlagMaterial(Material material){

        FlaggedMaterial flaggedMaterial = flaggedMaterialRepository
            .findByMaterial(material.getMaterial())
            .map(existingFlaggedMaterial -> {

                existingFlaggedMaterial.setDateOfUpdatedSS(material.getDateOfUpdatedSS());
                existingFlaggedMaterial.setValueOfUpdatedSS(material.getValueOfUpdatedSS());
                existingFlaggedMaterial.setDateOfUpdatedST(material.getDateOfUpdatedST());
                existingFlaggedMaterial.setValueOfUpdatedST(material.getValueOfUpdatedST());
                existingFlaggedMaterial.setDeltaSST(material.getDeltaSST());
                existingFlaggedMaterial.setDeltaST(material.getDeltaST());

                return existingFlaggedMaterial;
            })
            .orElseGet(() -> {

                FlaggedMaterial newFlaggedMaterial = new FlaggedMaterial();
                newFlaggedMaterial.setMaterial(material.getMaterial());
                newFlaggedMaterial.setDescription(material.getDescription());
                newFlaggedMaterial.setAbcClassification(material.getAbcClassification());
                newFlaggedMaterial.setAvgSupplierDelay(material.getAvgSupplierDelay());
                newFlaggedMaterial.setMaxSupplierDelay(material.getMaxSupplierDelay());
                newFlaggedMaterial.setServiceLevel(material.getServiceLevel());
                newFlaggedMaterial.setPlant(material.getPlant());
                newFlaggedMaterial.setMrpController(material.getMrpController());
                newFlaggedMaterial.setCurrSAPSafetyStock(material.getCurrSAPSafetyStock());
                newFlaggedMaterial.setProposedSST(material.getProposedSST());
                newFlaggedMaterial.setDeltaSST(material.getDeltaSST());
                newFlaggedMaterial.setCurrentSAPSafeTime(material.getCurrentSAPSafeTime());
                newFlaggedMaterial.setProposedST(material.getProposedST());
                newFlaggedMaterial.setDeltaST(material.getDeltaST());
                newFlaggedMaterial.setOpenSAPmd04(material.getOpenSAPmd04());
                newFlaggedMaterial.setCurrentInventoryValue(material.getCurrentInventoryValue());
                newFlaggedMaterial.setUnitCost(material.getUnitCost());
                newFlaggedMaterial.setAvgDemand(material.getAvgDemand());
                newFlaggedMaterial.setAvgInventoryEffectAfterChange(material.getAvgInventoryEffectAfterChange());
                newFlaggedMaterial.setFlagMaterial(material.getFlagMaterial());
                newFlaggedMaterial.setFlagExpirationDate(material.getFlagExpirationDate());
                newFlaggedMaterial.setValueOfUpdatedSS(material.getValueOfUpdatedSS());
                newFlaggedMaterial.setValueOfUpdatedST(material.getValueOfUpdatedST());
                newFlaggedMaterial.setDateOfUpdatedSS(material.getDateOfUpdatedSS());
                newFlaggedMaterial.setDateOfUpdatedST(material.getDateOfUpdatedST());
                newFlaggedMaterial.setToSaveUpdates(material.getToSaveUpdates());
                newFlaggedMaterial.setCurrency(material.getCurrency());

                return newFlaggedMaterial; 
            });

        flaggedMaterialRepository.save(flaggedMaterial);
    }

    
    public void deleteFlaggedMaterial(String material){

        flaggedMaterialRepository
            .findByMaterial(material)
            .ifPresent(existingMaterial -> {
                flaggedMaterialRepository.deleteById(existingMaterial.getId());
            });

    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void removeExpiredFlags(){

        LocalDate currentDate = LocalDate.now();
        List<FlaggedMaterial> allMaterials = flaggedMaterialRepository.findAll();

        allMaterials.stream()
            .filter(material -> material.getFlagExpirationDate().isBefore(currentDate))
            .forEach(material -> flaggedMaterialRepository.delete(material));
    }
}
