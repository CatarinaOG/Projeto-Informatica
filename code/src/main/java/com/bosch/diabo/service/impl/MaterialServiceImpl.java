package com.bosch.diabo.service.impl;

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
import java.time.format.DateTimeParseException;
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
                existingMaterial.setAvgDemand(material.getAvgDemand());
                existingMaterial.setAvgInventoryEffectAfterChange(material.getAvgInventoryEffectAfterChange());
                existingMaterial.setFlagMaterial(material.getFlagMaterial());
                existingMaterial.setFlagExpirationDate(material.getFlagExpirationDate());
                existingMaterial.setComment(material.getComment());
                existingMaterial.setNewSAPSafetyStock(material.getNewSAPSafetyStock());
                existingMaterial.setNewSAPSafetyTime(material.getNewSAPSafetyTime());
                existingMaterial.setToSaveUpdates(material.getToSaveUpdates());
                existingMaterial.setCurrency(material.getCurrency());
                existingMaterial.setDateOfUpdatedSS(material.getDateOfUpdatedSS());
                existingMaterial.setDateOfUpdatedST(material.getDateOfUpdatedST());
                existingMaterial.setMrpController(material.getMrpController());
                existingMaterial.setPlant(material.getPlant());
                existingMaterial.setToSaveUpdates(material.getToSaveUpdates());
                existingMaterial.setValueOfUpdatedSS(material.getValueOfUpdatedSS());
                existingMaterial.setValueOfUpdatedST(material.getValueOfUpdatedST());

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


    @Override
    public void uploadFileReplace(File file){
        log.debug("Request to new source file : {}", file.getName());   
        deleteAll();

        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        String fileExtension = lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
        
        switch (fileExtension.toLowerCase()) {
            case "xlsx":
                uploadFileXLSX(file,false);
                break;
            case "csv":
                uploadFileCSV(file,false);
                break;
            case "xls":
                uploadFileXLS(file,false);
                break;
            case "ods":
                uploadFileODS(file, false);
                break;
            default:
                break;
        }

    }


    @Override
    public void uploadFileAddOrUpdate(File file){
        try
        { 
            log.debug("Request to new source file : {}", file.getName());   
            
            String fileName = file.getName();
            int lastDotIndex = fileName.lastIndexOf('.');
            String fileExtension = lastDotIndex == -1 ? "" : fileName.substring(lastDotIndex + 1);
            
            switch (fileExtension.toLowerCase()) {
                case "xlsx":
                    uploadFileXLSX(file,true);
                    break;
                case "csv":
                    uploadFileCSV(file,true);
                    break;
                case "xls":
                    uploadFileXLS(file,true);
                    break;
                case "ods":
                    uploadFileODS(file, true);
                default:
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }


    // ---------- > XLSX < ----------

    public void uploadFileXLSX(File file, Boolean toUpdate){

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

                if (toUpdate && opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material);

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
        material.setMaterial(getStringCellValue(row, headerMap, "Material"));
        material.setDescription(getStringCellValue(row, headerMap, "Material Description"));
        material.setAbcClassification(ABCClassification.fromString(getStringCellValue(row, headerMap, "ABC Classification")));
        material.setAvgSupplierDelay(getFloatCellValue(row, headerMap, "Avg. Supplier Delay"));
        material.setMaxSupplierDelay(getFloatCellValue(row, headerMap, "Max Supplier delay"));
        material.setServiceLevel(getFloatCellValue(row, headerMap, "Service Level"));
        material.setCurrSAPSafetyStock(getIntCellValue(row, headerMap, "Current SAP Safety Stock"));
        material.setProposedSST(getIntCellValue(row, headerMap, "Proposed SST"));
        material.setDeltaSST(getIntCellValue(row, headerMap, "Delta SST"));
        material.setCurrentSAPSafeTime(getIntCellValue(row, headerMap, "Current SAP Safety Time"));
        material.setProposedST(getIntCellValue(row, headerMap, "Proposed ST"));
        material.setDeltaST(getIntCellValue(row, headerMap, "delta ST"));
        material.setOpenSAPmd04(getStringCellValue(row, headerMap, "Open SAP md04"));
        material.setCurrentInventoryValue(getFloatCellValue(row, headerMap, "Current Inventory Value"));
        material.setUnitCost(getFloatCellValue(row, headerMap, "Unit Cost"));
        material.setAvgDemand(getIntCellValue(row, headerMap, "Avg Demand"));
        material.setAvgInventoryEffectAfterChange(getFloatCellValue(row, headerMap, "Average inventory effect after change"));
        material.setCurrency(Coin.EUR);
        material.setFlagMaterial(false);
        material.setMrpController(getStringCellValue(row, headerMap, "MRP Controller"));
        material.setPlant(getStringCellValue(row, headerMap, "Plant"));

        if (headerMap.containsKey("New SAP SS")) {
            try {
                material.setNewSAPSafetyStock(Integer.parseInt(getStringCellValue(row, headerMap, "New SAP SS")));
            } catch (NumberFormatException e) {
                material.setNewSAPSafetyStock(material.getProposedSST());
            }
        }

        if (headerMap.containsKey("New SAP Safety Time")) {
            try {
                material.setNewSAPSafetyTime(Integer.parseInt(getStringCellValue(row, headerMap, "New SAP Safety Time")));
            } catch (NumberFormatException e) {
                material.setNewSAPSafetyTime(material.getProposedST());
            }
        }

        if (headerMap.containsKey("Comment")) {
            material.setComment(getStringCellValue(row, headerMap, "Comment"));
        }

        return material;

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

    public void uploadFileXLS(File file, Boolean toUpdate) {
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

                if (toUpdate && opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ---------- > CSV < ----------

    public void uploadFileCSV(File file, Boolean toUpdate) {
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] header = csvReader.readNext(); // Assuming the first row is the header

            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                Material material = parseMaterialCSV(header, nextRecord);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (toUpdate && opcMaterial.isPresent())
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

        material.setMaterial(nextRecord[getIndex(header, "Material")]);
        material.setDescription(nextRecord[getIndex(header, "Material Description")]);
        material.setAbcClassification(ABCClassification.fromString(nextRecord[getIndex(header, "ABC Classification")]));
        material.setAvgSupplierDelay(Float.parseFloat(nextRecord[getIndex(header, "Avg. Supplier Delay")]));
        material.setMaxSupplierDelay(Float.parseFloat(nextRecord[getIndex(header, "Max Supplier delay")]));
        material.setServiceLevel(Float.parseFloat(nextRecord[getIndex(header, "Service Level")]));
        material.setCurrSAPSafetyStock(Integer.parseInt(nextRecord[getIndex(header, "Current SAP Safety Stock")]));
        material.setProposedSST(Integer.parseInt(nextRecord[getIndex(header, "Proposed SST")]));
        material.setDeltaSST(Integer.parseInt(nextRecord[getIndex(header, "Delta SST")]));
        material.setCurrentSAPSafeTime(Integer.parseInt(nextRecord[getIndex(header, "Current SAP Safety Time")]));
        material.setProposedST(Integer.parseInt(nextRecord[getIndex(header, "Proposed ST")]));
        material.setDeltaST(Integer.parseInt(nextRecord[getIndex(header, "delta ST")]));
        material.setOpenSAPmd04(nextRecord[getIndex(header, "Open SAP md04")]);
        material.setCurrentInventoryValue(parseNumericValue(nextRecord[getIndex(header, "Current Inventory Value")]));
        material.setUnitCost(parseNumericValue(nextRecord[getIndex(header, "Unit Cost")]));
        material.setAvgDemand(Integer.parseInt(nextRecord[getIndex(header, "Avg Demand")]));
        material.setAvgInventoryEffectAfterChange(parseNumericValue(nextRecord[getIndex(header, "Average inventory effect after change")]));
        material.setFlagMaterial(false);
        material.setCurrency(Coin.EUR);
        material.setFlagExpirationDate(null);
        material.setMrpController(nextRecord[getIndex(header, "MRP Controller")]);
        material.setPlant(nextRecord[getIndex(header, "Plant")]);

        if(getIndex(header, "New SAP SS") >= 0){
            try {
                material.setNewSAPSafetyStock(Integer.parseInt(nextRecord[getIndex(header, "New SAP SS")]));
            } catch (NumberFormatException e) {
                material.setNewSAPSafetyStock(material.getProposedSST());
            }
        }

        if(getIndex(header, "New SAP Safety Time") >= 0){
            try {
                material.setNewSAPSafetyTime(Integer.parseInt(nextRecord[getIndex(header, "New SAP Safety Time")]));
            } catch (NumberFormatException e) {
                material.setNewSAPSafetyTime(material.getProposedST());
            }
        }

        if(getIndex(header, "Comment") >= 0){
            material.setComment(nextRecord[getIndex(header, "Comment")]);
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

    public void uploadFileODS(File file, Boolean toUpdate) {
        try {
            com.github.miachm.sods.Sheet sheet = new SpreadSheet(file).getSheet(0);
            Range data = sheet.getDataRange();
            Object[][] materials = data.getValues();
            System.out.println("Materials have " + materials.length + " rows and " + materials[0].length + "collumns");
            // Assuming that the 1st row is the header
            Object[] header = materials[0];
            System.out.println("Starting parsing of ODS file");
            for (int i = 1; i < data.getNumRows(); i++) {
                System.out.println("Material " + i + " " + materials[i]);
                Material material = parseMaterialODS(materials[i], header);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (toUpdate && opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material); 
            }

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Material parseMaterialODS(Object[] row, Object[] header) {
        Material material = new Material();

        material.setMaterial(row[getIndexOBJ(header, "Material")].toString());
        material.setDescription(row[getIndexOBJ(header, "Description")].toString());
        material.setAbcClassification(ABCClassification.fromString(row[getIndexOBJ(header, "ABC Classification")].toString()));
        material.setAvgSupplierDelay(Float.parseFloat(row[getIndexOBJ(header, "Average Supplier Delay")].toString()));
        material.setMaxSupplierDelay(Float.parseFloat(row[getIndexOBJ(header, "Maximum Supplier Delay")].toString()));
        material.setServiceLevel(Float.parseFloat(row[getIndexOBJ(header, "Service Level")].toString()));
        material.setCurrSAPSafetyStock((int) Float.parseFloat(row[getIndexOBJ(header, "Current SAP Safety Stock")].toString()));
        material.setProposedSST((int) Float.parseFloat(row[getIndexOBJ(header, "Proposed SST")].toString()));
        material.setDeltaSST((int) Float.parseFloat(row[getIndexOBJ(header, "Delta SST")].toString()));
        material.setCurrentSAPSafeTime((int) Float.parseFloat(row[getIndexOBJ(header, "Current SAP Safety Time")].toString()));
        material.setProposedST((int) Float.parseFloat(row[getIndexOBJ(header, "Proposed ST")].toString()));
        material.setDeltaST((int) Float.parseFloat(row[getIndexOBJ(header, "Delta ST")].toString()));
        material.setOpenSAPmd04(row[getIndexOBJ(header, "Open SAP md04")].toString());
        material.setPlant(row[getIndexOBJ(header, "Plant")].toString());
        material.setMrpController(row[getIndexOBJ(header, "MRP Controller")].toString());
        material.setCurrentInventoryValue(parseNumericValue(row[getIndexOBJ(header, "Current Inventory Value")].toString()));
        material.setUnitCost(parseNumericValue(row[getIndexOBJ(header, "Unit Cost")].toString()));
        material.setAvgDemand((int) Float.parseFloat(row[getIndexOBJ(header, "Average Demand")].toString()));
        material.setAvgInventoryEffectAfterChange(parseNumericValue(row[getIndexOBJ(header, "Average Inventory Effect After Change")].toString()));
        material.setFlagMaterial(false);
        material.setCurrency(Coin.EUR);
        material.setFlagExpirationDate(null);
        material.setMrpController(row[getIndexOBJ(header, "MRP Controller")].toString());
        material.setPlant(row[getIndexOBJ(header, "Plant")].toString());

        if(getIndexOBJ(header, "New SAP SS") >= 0){
            try {
                material.setNewSAPSafetyStock(Integer.parseInt(row[getIndexOBJ(header, "New SAP SS")].toString()));
            } catch (NumberFormatException e) {
                material.setNewSAPSafetyStock(material.getProposedSST());
            }
        }

        if(getIndexOBJ(header, "New SAP Safety Time") >= 0){
            try {
                material.setNewSAPSafetyTime(Integer.parseInt(row[getIndexOBJ(header, "New SAP Safety Time")].toString()));
            } catch (NumberFormatException e) {
                material.setNewSAPSafetyTime(material.getProposedST());
            }
        }

        if(getIndexOBJ(header, "Comment") >= 0){
            material.setComment(row[getIndexOBJ(header, "Comment")].toString());
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


}
