package com.bosch.diabo.service.impl;

import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.Material;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.domain.enumeration.Coin;
import com.bosch.diabo.repository.MaterialRepository;
import com.bosch.diabo.service.MaterialService;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.github.miachm.sods.*;

import com.opencsv.CSVReader;

/**
 * Service Implementation for managing {@link Material}.
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    private final String file_material = "Material";
    private final String file_material_description = "Material Description";
    private final String file_abc_classification = "ABC Classification";
    private final String file_avg_supplier_delay = "Avg. Supplier Delay";
    private final String file_max_supplier_delay = "Max Supplier delay";
    private final String file_service_level = "Service Level";
    private final String file_current_sap_safety_stock = "Current SAP Safety Stock";
    private final String file_proposed_sst = "Proposed SST";
    private final String file_delta_sst = "Delta SST";
    private final String file_current_sap_safety_time = "Current SAP Safety Time";
    private final String file_proposed_st = "Proposed ST";
    private final String file_delta_st = "delta ST";
    private final String file_open_sap_md04 = "Open SAP md04";
    private final String file_current_inventory_value = "Current Inventory Value";
    private final String file_currency = "Currency";
    private final String file_unit_cost = "Unit Cost";
    private final String file_avg_demand = "Avg Demand";
    private final String file_average_inventory_effect_after_change = "Average inventory effect after change";
    private final String file_mrp_controller = "MRP Controller";
    private final String file_plant = "Plant";
    private final String file_comment = "Comment";


    @Autowired
    private FlaggedMaterialServiceImpl flaggedMaterialServiceImpl;

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
                existingMaterial.setCurrency(material.getCurrency());
                existingMaterial.setAvgDemand(material.getAvgDemand());
                existingMaterial.setAvgInventoryEffectAfterChange(material.getAvgInventoryEffectAfterChange());
                existingMaterial.setFlagMaterial(material.getFlagMaterial());
                existingMaterial.setFlagExpirationDate(material.getFlagExpirationDate());
                existingMaterial.setComment(material.getComment());
                existingMaterial.setNewSAPSafetyStock(material.getNewSAPSafetyStock());
                existingMaterial.setNewSAPSafetyTime(material.getNewSAPSafetyTime());
                existingMaterial.setMrpController(material.getMrpController());
                existingMaterial.setPlant(material.getPlant());
                existingMaterial.setToSaveUpdates(true);

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
                if (material.getPlant() != null) {
                    existingMaterial.setPlant(material.getPlant());
                }
                if (material.getMrpController() != null) {
                    existingMaterial.setMrpController(material.getMrpController());
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
                if (material.getFlagExpirationDate() != null) {
                    existingMaterial.setFlagExpirationDate(material.getFlagExpirationDate());
                }
                if (material.getComment() != null) {
                    existingMaterial.setComment(material.getComment());
                }
                if (material.getNewSAPSafetyStock() != null) {
                    existingMaterial.setNewSAPSafetyStock(material.getNewSAPSafetyStock());
                }
                if (material.getNewSAPSafetyTime() != null) {
                    existingMaterial.setNewSAPSafetyTime(material.getNewSAPSafetyTime());
                }
                if (material.getToSaveUpdates() != null) {
                    existingMaterial.setToSaveUpdates(material.getToSaveUpdates());
                }
                if (material.getCurrency() != null) {
                    existingMaterial.setCurrency(material.getCurrency());
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

    public void deleteEveryMaterialNotSaved(){
        materialRepository
            .findAll()
            .stream()
            .filter(material -> material.getToSaveUpdates() != true)
            .forEach(material -> {
                materialRepository.deleteById(material.getId());
            });
    }

    public void setEveryMaterialToNotSave(){
        materialRepository
        .findAll()
        .stream()
        .peek(material -> {
            material.setToSaveUpdates(false);
        })
        .forEach(materialRepository::save);
    }


    @Override
    public void uploadFileReplace(File file){
        log.debug("Request to new source file : {}", file.getName());   
        
        try{ 
            
            String fileName = file.getName();
            int lastDotIndex = fileName.lastIndexOf('.');
            String fileExtension = lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
            
            switch (fileExtension.toLowerCase()) {
                case "xlsx":
                    uploadFileXLSX(file);
                    break;
                case "csv":
                    uploadFileCSV(file);
                    break;
                case "xls":
                    uploadFileXLS(file);
                    break;
                case "ods":
                    uploadFileODS(file);
                    break;
                default:
                    break;
            }

            deleteEveryMaterialNotSaved();
            setEveryMaterialToNotSave();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void uploadFileAddOrUpdate(File file){
        try{ 
            log.debug("Request to new source file : {}", file.getName());   
            
            String fileName = file.getName();
            int lastDotIndex = fileName.lastIndexOf('.');
            String fileExtension = lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
            
            switch (fileExtension.toLowerCase()) {
                case "xlsx":
                    uploadFileXLSX(file);
                    break;
                case "csv":
                    uploadFileCSV(file);
                    break;
                case "xls":
                    uploadFileXLS(file);
                    break;
                case "ods":
                    uploadFileODS(file);
                default:
                    break;
            }

            setEveryMaterialToNotSave();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }


    // ---------- > XLSX < ----------

    public void uploadFileXLSX(File file){

        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis)) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            // Get header names and their column indices
            Map<String, Integer> headerMap = getHeaderMap(headerRow);

            while (rowIterator.hasNext() ) {
                Row row = rowIterator.next();
                Material material = parseMaterialXLSX(row, headerMap);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (opcMaterial.isPresent())
                    updateByMaterialName(material);
                else{
                    material.setToSaveUpdates(true);                    
                    save(material);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<String, Integer> getHeaderMap(Row headerRow) {
        Map<String, Integer> headerMap = new HashMap<>();
        Iterator<Cell> cellIterator = headerRow.cellIterator();

        int columnIndex = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            headerMap.put(cell.getStringCellValue(), columnIndex);
            columnIndex++;
        }

        return headerMap;
    }


    private Material parseMaterialXLSX(Row row, Map<String, Integer> headerMap){
        Material material = new Material();
        material.setMaterial(getStringCellValue(row, headerMap, file_material));
        material.setDescription(getStringCellValue(row, headerMap, file_material_description));
        material.setAbcClassification(ABCClassification.fromString(getStringCellValue(row, headerMap, file_abc_classification)));
        material.setAvgSupplierDelay(getFloatCellValue(row, headerMap, file_avg_supplier_delay));
        material.setMaxSupplierDelay(getFloatCellValue(row, headerMap, file_max_supplier_delay));
        material.setServiceLevel(getFloatCellValue(row, headerMap, file_service_level));
        material.setCurrSAPSafetyStock(getIntCellValue(row, headerMap, file_current_sap_safety_stock));
        material.setProposedSST(getIntCellValue(row, headerMap, file_proposed_sst));
        material.setDeltaSST(getIntCellValue(row, headerMap, file_delta_sst));
        material.setCurrentSAPSafeTime(getIntCellValue(row, headerMap, file_current_sap_safety_time));
        material.setProposedST(getIntCellValue(row, headerMap, file_proposed_st));
        material.setDeltaST(getIntCellValue(row, headerMap, file_delta_st));
        material.setOpenSAPmd04(getStringCellValue(row, headerMap, file_open_sap_md04));
        material.setCurrentInventoryValue(getFloatCellValue(row, headerMap, file_current_inventory_value));
        material.setCurrency(Coin.fromString(getStringCellValue(row, headerMap, file_currency)));
        material.setUnitCost(getFloatCellValue(row, headerMap, file_unit_cost));
        material.setAvgDemand(getIntCellValue(row, headerMap, file_avg_demand));
        material.setAvgInventoryEffectAfterChange(getFloatCellValue(row, headerMap, file_average_inventory_effect_after_change));
        material.setFlagMaterial(getFlagFromFlaggedMaterials(material.getMaterial()));
        material.setNewSAPSafetyStock(material.getProposedSST());
        material.setNewSAPSafetyTime(material.getProposedST());


        if (headerMap.containsKey(file_mrp_controller)) {
            material.setMrpController(getStringCellValue(row, headerMap, file_mrp_controller));
        }

        if (headerMap.containsKey(file_plant)) {
            material.setPlant(getStringCellValue(row, headerMap, file_plant));
        }

        if (headerMap.containsKey(file_comment)) {
            material.setComment(getStringCellValue(row, headerMap, file_comment));
        }


        return material;

    }

    private Boolean getFlagFromFlaggedMaterials(String materialName){

        Optional<FlaggedMaterial> flaggedMaterial = flaggedMaterialServiceImpl.findByMaterial(materialName);
        if(flaggedMaterial.isPresent()) return true;

        return false;
    }
    
    private String getStringCellValue(Row row, Map<String, Integer> headerMap, String headerName) {
        int columnIndex = headerMap.get(headerName);
        Cell cell = row.getCell(columnIndex);
        return cell != null ? cell.getStringCellValue() : null;
    }
    
    private float getFloatCellValue(Row row, Map<String, Integer> headerMap, String headerName) {
        int columnIndex = headerMap.get(headerName);
        Cell cell = row.getCell(columnIndex);

        return cell != null ? (float) cell.getNumericCellValue() : 0.0f; // Set a default value if the cell is null
    }
    
    private int getIntCellValue(Row row, Map<String, Integer> headerMap, String headerName) {
        int columnIndex = headerMap.get(headerName);
        Cell cell = row.getCell(columnIndex);
        return cell != null ? (int) cell.getNumericCellValue() : 0; // Set a default value if the cell is null
    }
    

    // ---------- > XLS < ----------

    public void uploadFileXLS(File file) {
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new HSSFWorkbook(fis)) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            // Get header names and their column indices
            Map<String, Integer> headerMap = getHeaderMap(headerRow);


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Material material = parseMaterialXLSX(row, headerMap);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (opcMaterial.isPresent())
                    updateByMaterialName(material);
                else{
                    material.setToSaveUpdates(true);  
                    save(material);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ---------- > CSV < ----------

    public void uploadFileCSV(File file) {
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] header = csvReader.readNext(); // Assuming the first row is the header

            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                Material material = parseMaterialCSV(header, nextRecord);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public Material parseMaterialCSV(String[] header, String[] nextRecord) {
        Material material = new Material();

        material.setMaterial(nextRecord[getIndex(header, file_material)]);
        material.setDescription(nextRecord[getIndex(header, file_material_description)]);
        material.setAbcClassification(ABCClassification.fromString(nextRecord[getIndex(header, file_abc_classification)]));
        material.setAvgSupplierDelay(Float.parseFloat(nextRecord[getIndex(header, file_avg_supplier_delay)]));
        material.setMaxSupplierDelay(Float.parseFloat(nextRecord[getIndex(header, file_max_supplier_delay)]));
        material.setServiceLevel(Float.parseFloat(nextRecord[getIndex(header, file_service_level)]));
        material.setCurrSAPSafetyStock(Integer.parseInt(nextRecord[getIndex(header, file_current_sap_safety_stock)]));
        material.setProposedSST(Integer.parseInt(nextRecord[getIndex(header, file_proposed_sst)]));
        material.setDeltaSST(Integer.parseInt(nextRecord[getIndex(header, file_delta_sst)]));
        material.setCurrentSAPSafeTime(Integer.parseInt(nextRecord[getIndex(header, file_current_sap_safety_time)]));
        material.setProposedST(Integer.parseInt(nextRecord[getIndex(header, file_proposed_st)]));
        material.setDeltaST(Integer.parseInt(nextRecord[getIndex(header, file_delta_st)]));
        material.setOpenSAPmd04(nextRecord[getIndex(header, file_open_sap_md04)]);
        material.setCurrentInventoryValue(parseNumericValue(nextRecord[getIndex(header, file_current_inventory_value)]));
        material.setCurrency(Coin.fromString(nextRecord[getIndex(header, file_currency)]));
        material.setUnitCost(parseNumericValue(nextRecord[getIndex(header, file_unit_cost)]));
        material.setAvgDemand(Integer.parseInt(nextRecord[getIndex(header, file_avg_demand)]));
        material.setAvgInventoryEffectAfterChange(parseNumericValue(nextRecord[getIndex(header, file_average_inventory_effect_after_change)]));
        material.setFlagMaterial(getFlagFromFlaggedMaterials(material.getMaterial()));
        material.setNewSAPSafetyStock(material.getProposedSST());
        material.setNewSAPSafetyTime(material.getProposedST());
        
        if(getIndex(header, file_mrp_controller) >= 0){
            material.setMrpController(nextRecord[getIndex(header, file_mrp_controller)]);
        }

        if(getIndex(header, file_plant) >= 0){
            material.setPlant(nextRecord[getIndex(header, file_plant)]);
        }

        if(getIndex(header, file_comment) >= 0){
            material.setComment(nextRecord[getIndex(header, file_comment)]);
        }


        return material;
    }


    private int getIndex(String[] header, String columnName) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].equals(columnName)) {
                return i;
            }
        }
        return -1; // Column not found
    }

    private float parseNumericValue(String cellValue) {
        String numericValue = cellValue.replaceAll("[^\\d.]", "");
        return Float.parseFloat(numericValue);
    }

    // -------------> ODS <-------------

    public void uploadFileODS(File file) {
        try {
            com.github.miachm.sods.Sheet sheet = new SpreadSheet(file).getSheet(0);
            Range data = sheet.getDataRange();
            Object[][] materials = data.getValues();
            // Assuming that the 1st row is the header
            Object[] header = materials[0];
            for (int i = 1; i < data.getNumRows(); i++) {
                Material material = parseMaterialODS(materials[i], header);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (opcMaterial.isPresent())
                    updateByMaterialName(material);
                else{
                    material.setToSaveUpdates(true);  
                    save(material);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Material parseMaterialODS(Object[] row, Object[] header) {
        Material material = new Material();

        material.setMaterial(row[getIndexOBJ(header, file_material)].toString());
        material.setDescription(row[getIndexOBJ(header, file_material_description)].toString());
        material.setAbcClassification(ABCClassification.fromString(row[getIndexOBJ(header, file_abc_classification)].toString()));
        material.setAvgSupplierDelay(Float.parseFloat(row[getIndexOBJ(header, file_avg_supplier_delay)].toString()));
        material.setMaxSupplierDelay(Float.parseFloat(row[getIndexOBJ(header, file_max_supplier_delay)].toString()));
        material.setServiceLevel(Float.parseFloat(row[getIndexOBJ(header, file_service_level)].toString()));
        material.setCurrSAPSafetyStock((int) Float.parseFloat(row[getIndexOBJ(header, file_current_sap_safety_stock)].toString()));
        material.setProposedSST((int) Float.parseFloat(row[getIndexOBJ(header, file_proposed_sst)].toString()));
        material.setDeltaSST((int) Float.parseFloat(row[getIndexOBJ(header, file_delta_sst)].toString()));
        material.setCurrentSAPSafeTime((int) Float.parseFloat(row[getIndexOBJ(header, file_current_sap_safety_time)].toString()));
        material.setProposedST((int) Float.parseFloat(row[getIndexOBJ(header, file_proposed_st)].toString()));
        material.setDeltaST((int) Float.parseFloat(row[getIndexOBJ(header, file_delta_st)].toString()));
        material.setOpenSAPmd04(row[getIndexOBJ(header, file_open_sap_md04)].toString());
        material.setCurrentInventoryValue(parseNumericValue(row[getIndexOBJ(header, file_current_inventory_value)].toString()));
        material.setCurrency(Coin.fromString(row[getIndexOBJ(header, file_currency)].toString()));
        material.setUnitCost(parseNumericValue(row[getIndexOBJ(header, file_unit_cost)].toString()));
        material.setAvgDemand((int) Float.parseFloat(row[getIndexOBJ(header, file_avg_demand)].toString()));
        material.setAvgInventoryEffectAfterChange(parseNumericValue(row[getIndexOBJ(header, file_average_inventory_effect_after_change)].toString()));
        material.setFlagMaterial(getFlagFromFlaggedMaterials(material.getMaterial()));
        material.setNewSAPSafetyStock(material.getProposedSST());
        material.setNewSAPSafetyTime(material.getProposedST());


        if(getIndexOBJ(header, file_mrp_controller) >= 0){
            material.setMrpController(row[getIndexOBJ(header, file_mrp_controller)].toString());
        }

        if(getIndexOBJ(header, file_plant) >= 0){
            material.setPlant(row[getIndexOBJ(header, file_plant)].toString());
        }

        if(getIndexOBJ(header, file_comment) >= 0){
            material.setComment(row[getIndexOBJ(header, file_comment)].toString());
        }


        return material;
    }

    private int getIndexOBJ(Object[] header, String columnName) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].toString().equals(columnName)) {
                return i;
            }
        }
        return -1; // Column not found
    }


    /* --------------------------- SUBMIT CHANGES --------------------------- */

    @Override
    public void submitChanges(List<Object> data){

        for (Object item : data) {
            if (item instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) item;

                String stringFlagDate;

                if(dataMap.containsKey("dateFlag")){
                    stringFlagDate = (String) dataMap.get("dateFlag");
                    
                    updateObject(
                        extractLong(dataMap.get("materialId")),
                        extractInt(dataMap.get("newSST")),
                        extractInt(dataMap.get("newST")),
                        (String) dataMap.get("newComment"),
                        (Boolean) dataMap.get("flag"),
                        LocalDate.parse(stringFlagDate)
                    );
                } else {

                    updateObject(
                        extractLong(dataMap.get("materialId")),
                        extractInt(dataMap.get("newSST")),
                        extractInt(dataMap.get("newST")),
                        (String) dataMap.get("newComment"),
                        (Boolean) dataMap.get("flag"),
                        LocalDate.now()
                    );
                }
            }
        }
    }

    public List<Material> getMaterialChanged(List<Object> data){
        ArrayList<Material> l = new ArrayList<>();
        for (Object item : data) {
            if (item instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) item;
                Optional<Material> mat = findOne(extractLong(dataMap.get("materialId")));
                mat.ifPresent(m -> l.add(m));
            }
        }
        return l;
    }

    public void updateObject(Long materialId, int newSST, int newST, String newComment, Boolean flag, LocalDate flagDate){

        materialRepository
        .findById(materialId)
        .ifPresent(existingMaterial -> {

            LocalDate currentDate = LocalDate.now();

            if(existingMaterial.getNewSAPSafetyStock() != newSST){
                existingMaterial.setDateOfUpdatedSS(currentDate);
                existingMaterial.setValueOfUpdatedSS(newSST);
            }

            if(existingMaterial.getNewSAPSafetyTime() != newST){
                existingMaterial.setDateOfUpdatedST(currentDate);
                existingMaterial.setValueOfUpdatedST(newST);
            }
            
            existingMaterial.setNewSAPSafetyStock(newSST);
            existingMaterial.setNewSAPSafetyTime(newST);
            existingMaterial.setDeltaSST(existingMaterial.getProposedSST() - newSST);
            existingMaterial.setDeltaST(existingMaterial.getProposedST() - newST);
            existingMaterial.setComment(newComment);
            existingMaterial.setFlagMaterial(flag);
            if(flag) existingMaterial.setFlagExpirationDate(flagDate);

            updateFlaggedMaterials(flag, existingMaterial);
            
            materialRepository.save(existingMaterial);
        });

    }

    public void updateFlaggedMaterials(Boolean flag, Material existingMaterial){
        flaggedMaterialServiceImpl.updateFlaggedMaterials(flag,existingMaterial);
    }


    private Long extractLong(Object value) {

        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        throw new IllegalArgumentException("Invalid type for key " + value);
    }
    
    private int extractInt(Object value) {

        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        throw new IllegalArgumentException("Invalid type for key " + value);
    }

    /* --------------------------- FLAG ROUTINE --------------------------- */

    
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeExpiredFlags(){

        LocalDate currentDate = LocalDate.now();
        List<Material> allMaterials = materialRepository.findAll();

        allMaterials = allMaterials.stream()
            .filter(material -> material.getFlagExpirationDate().isBefore(currentDate))
            .map(material -> {
                material.setFlagMaterial(false);
                material.setFlagExpirationDate(null);
                return material;
            })
            .collect(Collectors.toList());

        materialRepository.saveAll(allMaterials);
    }


    @Override
    public void updateCorrespondingMaterial(List<Object> data){

        for (Object item : data) {
            if (item instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) item;

                String material = (String) dataMap.get("material");
                LocalDate flagDate = LocalDate.parse((String) dataMap.get("dateFlag"));
                Boolean flagged = (Boolean) dataMap.get("flag");
                
                if(flagged) updateFlaggedDate(material,flagDate); 
                else removeFlag(material);

            }
        }
    }

    private void updateFlaggedDate(String material, LocalDate date){
        materialRepository
            .findByMaterial(material)
            .ifPresent(existingMaterial -> {
                existingMaterial.setFlagExpirationDate(date);
                materialRepository.save(existingMaterial);
            });
    }


    public void removeFlag(String material){
        materialRepository
            .findByMaterial(material)
            .ifPresent(existingMaterial -> {
                existingMaterial.setFlagMaterial(false);
                existingMaterial.setFlagExpirationDate(null);

                materialRepository.save(existingMaterial);
            });
    }

}
