package com.bosch.diabo.service.impl;

import com.bosch.diabo.domain.Material;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.repository.MaterialRepository;
import com.bosch.diabo.service.MaterialService;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Service Implementation for managing {@link Material}.
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material save(Material material) {
        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    @Override
    public Material update(Material material) {
        log.debug("Request to update Material : {}", material);
        return materialRepository.save(material);
    }

    @Override
    public Optional<Material> updateByMaterialName(Material material){
        log.debug("Request to update Material by the name : {}", material);
        return materialRepository
            .findByMaterial(material.getMaterial())
            .map(existingMaterial -> {
                existingMaterial.setMaterial(material.getMaterial());
                existingMaterial.setDescription(material.getDescription());
                existingMaterial.setAbcClassification(material.getAbcClassification());
                existingMaterial.setAvgSupplierDelay(material.getAvgSupplierDelay());
                existingMaterial.setMaxSupplierDelay(material.getMaxSupplierDelay());
                existingMaterial.setServiceLevel(material.getServiceLevel());
                existingMaterial.setCurrSAPSafetyStock(material.getCurrSAPSafetyStock());
                existingMaterial.setProposedSST(material.getProposedSST());
                existingMaterial.setDeltaSST(material.getDeltaSST());
                existingMaterial.setCurrentSAPSafeTime(material.getCurrentSAPSafeTime());
                existingMaterial.setProposedST(material.getProposedST());
                existingMaterial.setDeltaST(material.getDeltaST());
                existingMaterial.setOpenSAPmd04(material.getOpenSAPmd04());
                existingMaterial.setCurrentInventoryValue(material.getCurrentInventoryValue());
                existingMaterial.setUnitCost(material.getUnitCost());
                existingMaterial.setAvgDemand(material.getAvgDemand());
                existingMaterial.setAvgInventoryEffectAfterChange(material.getAvgInventoryEffectAfterChange());
                existingMaterial.setFlagMaterial(material.getFlagMaterial());
                existingMaterial.setComment(material.getComment());
                
                System.out.println("-----------------new name: "+material.getDescription()+"-----------------");

                return existingMaterial;
            })
            .map(materialRepository::save);
    }

    @Override
    public Optional<Material> partialUpdate(Material material) {
        log.debug("Request to partially update Material : {}", material);

        return materialRepository
            .findById(material.getId())
            .map(existingMaterial -> {
                if (material.getMaterial() != null) {
                    existingMaterial.setMaterial(material.getMaterial());
                }
                if (material.getDescription() != null) {
                    existingMaterial.setDescription(material.getDescription());
                }
                if (material.getAbcClassification() != null) {
                    existingMaterial.setAbcClassification(material.getAbcClassification());
                }
                if (material.getAvgSupplierDelay() != null) {
                    existingMaterial.setAvgSupplierDelay(material.getAvgSupplierDelay());
                }
                if (material.getMaxSupplierDelay() != null) {
                    existingMaterial.setMaxSupplierDelay(material.getMaxSupplierDelay());
                }
                if (material.getServiceLevel() != null) {
                    existingMaterial.setServiceLevel(material.getServiceLevel());
                }
                if (material.getCurrSAPSafetyStock() != null) {
                    existingMaterial.setCurrSAPSafetyStock(material.getCurrSAPSafetyStock());
                }
                if (material.getProposedSST() != null) {
                    existingMaterial.setProposedSST(material.getProposedSST());
                }
                if (material.getDeltaSST() != null) {
                    existingMaterial.setDeltaSST(material.getDeltaSST());
                }
                if (material.getCurrentSAPSafeTime() != null) {
                    existingMaterial.setCurrentSAPSafeTime(material.getCurrentSAPSafeTime());
                }
                if (material.getProposedST() != null) {
                    existingMaterial.setProposedST(material.getProposedST());
                }
                if (material.getDeltaST() != null) {
                    existingMaterial.setDeltaST(material.getDeltaST());
                }
                if (material.getOpenSAPmd04() != null) {
                    existingMaterial.setOpenSAPmd04(material.getOpenSAPmd04());
                }
                if (material.getCurrentInventoryValue() != null) {
                    existingMaterial.setCurrentInventoryValue(material.getCurrentInventoryValue());
                }
                if (material.getUnitCost() != null) {
                    existingMaterial.setUnitCost(material.getUnitCost());
                }
                if (material.getAvgDemand() != null) {
                    existingMaterial.setAvgDemand(material.getAvgDemand());
                }
                if (material.getAvgInventoryEffectAfterChange() != null) {
                    existingMaterial.setAvgInventoryEffectAfterChange(material.getAvgInventoryEffectAfterChange());
                }
                if (material.getFlagMaterial() != null) {
                    existingMaterial.setFlagMaterial(material.getFlagMaterial());
                }
                if (material.getComment() != null) {
                    existingMaterial.setComment(material.getComment());
                }

                return existingMaterial;
            })
            .map(materialRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Material> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Material> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
    }

    @Override
    public Optional<Material> findByMaterial(String material){
        log.debug("Request to find Material by name");
        return materialRepository.findByMaterial(material);
    }

    public void deleteAll() {
        materialRepository.deleteAll();
    }


    @Override
    public void uploadFileReplace(File file){
        log.debug("Request to new source file : {}", file.getName());   
        deleteAll();
        int rownr = 0;
        
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Ignore header row

            while (rowIterator.hasNext() && rownr < 3) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Material material = parseMaterial(cellIterator);
                materialRepository.save(material);
                rownr++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    @Override
    public void uploadFileAddOrUpdate(File file){
        log.debug("Request to new source file : {}", file.getName());   
        
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            int rownr = 0;

            while (rowIterator.hasNext() && rownr < 3) {

                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Material material = parseMaterial(cellIterator);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());
                
                if (opcMaterial.isPresent()) {
                    updateByMaterialName(material);
                } else {
                    save(material);
                }
                rownr++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


    public Material parseMaterial(Iterator<Cell> cellIterator){
        Material material = new Material();
        material.setMaterial(cellIterator.next().getStringCellValue());
        material.setDescription(cellIterator.next().getStringCellValue());
        material.setAbcClassification(ABCClassification.fromString(cellIterator.next().getStringCellValue()));
        material.setAvgSupplierDelay((float) cellIterator.next().getNumericCellValue());
        material.setMaxSupplierDelay((float) cellIterator.next().getNumericCellValue());
        material.setServiceLevel((float) cellIterator.next().getNumericCellValue());

        int currSAPSafetyStock = (int) cellIterator.next().getNumericCellValue();
        material.setCurrSAPSafetyStock(currSAPSafetyStock);

        int proposedSST = (int) cellIterator.next().getNumericCellValue();
        material.setProposedSST(proposedSST);

        int deltaSST = proposedSST - currSAPSafetyStock;
        material.setDeltaSST(deltaSST);
        cellIterator.next();
        material.setCurrentSAPSafeTime((int) cellIterator.next().getNumericCellValue());
        material.setProposedST((int) cellIterator.next().getNumericCellValue());

        int deltaST = material.getProposedST() - material.getCurrentSAPSafeTime();
        material.setDeltaST(deltaST);
        cellIterator.next();
        material.setOpenSAPmd04(cellIterator.next().getStringCellValue());
        material.setCurrentInventoryValue((float) cellIterator.next().getNumericCellValue());

        Float unitCost = (float) cellIterator.next().getNumericCellValue();
        material.setUnitCost(unitCost);

        int avgDemand = (int) cellIterator.next().getNumericCellValue();
        material.setAvgDemand(avgDemand);
        material.setAvgInventoryEffectAfterChange(deltaSST * unitCost + deltaST * avgDemand * unitCost);
        cellIterator.next();
        
        if(cellIterator.hasNext())
            material.setFlagMaterial(getFlagValue(cellIterator.next()));
        else {
            material.setFlagMaterial(false);
            material.setComment("");
        }
        if(cellIterator.hasNext())
            material.setComment(cellIterator.next().getStringCellValue());
        else
            material.setComment("");
        return material;
    }

    
    public Boolean getFlagValue(Cell cell){
        if(cell.getCellType() == CellType.BOOLEAN)
            return cell.getBooleanCellValue();
        else
            return false;
    }
}
