package com.bosch.diabo.service.impl;

import com.bosch.diabo.domain.Material;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.repository.MaterialRepository;
import com.bosch.diabo.service.MaterialService;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Optional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.opencsv.CSVReader;

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
                if (material.getNewSAPSafetyStock() != null) {
                    existingMaterial.setNewSAPSafetyStock(material.getNewSAPSafetyStock());
                }
                if (material.getNewSAPSafetyTime() != null) {
                    existingMaterial.setNewSAPSafetyTime(material.getNewSAPSafetyTime());
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
            default:
                break;
        }

    }


    @Override
    public void uploadFileAddOrUpdate(File file){
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
            default:
                break;
        }
        
    }


    // ---------- > XLSX < ----------

    public void uploadFileXLSX(File file, Boolean toUpdate){

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            // Get header names and their column indices
            Map<String, Integer> headerMap = getHeaderMap(headerRow);

            int rownr = 0;

            while (rowIterator.hasNext() && rownr < 3) {
                Row row = rowIterator.next();
                Material material = parseMaterialXLSX(row, headerMap);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (toUpdate && opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material);

                rownr++;
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
        System.out.println("column: Material ");
        material.setMaterial(getStringCellValue(row, headerMap, "Material"));
        System.out.println("Material Description ");
        material.setDescription(getStringCellValue(row, headerMap, "Material Description"));
        System.out.println("ABC Classification");
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

        if (headerMap.containsKey("Flag material")) {
            material.setFlagMaterial(getFlagValue(getStringCellValue(row, headerMap, "Flag material")));
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
    
    private Boolean getFlagValue(String cellValue) {
        return cellValue != null && cellValue.equalsIgnoreCase("true");
    }

    // ---------- > XLS < ----------

    public void uploadFileXLS(File file, Boolean toUpdate) {
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new HSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            // Get header names and their column indices
            Map<String, Integer> headerMap = getHeaderMap(headerRow);

            int rownr = 0;

            while (rowIterator.hasNext() && rownr < 3) {
                Row row = rowIterator.next();
                Material material = parseMaterialXLSX(row, headerMap);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (toUpdate && opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material);

                rownr++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ---------- > CSV < ----------

    public void uploadFileCSV(File file, Boolean toUpdate) {
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] header = csvReader.readNext(); // Assuming the first row is the header

            int rownr = 0;
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null && rownr < 3) {
                Material material = parseMaterialCSV(header, nextRecord);
                Optional<Material> opcMaterial = materialRepository.findByMaterial(material.getMaterial());

                if (toUpdate && opcMaterial.isPresent())
                    updateByMaterialName(material);
                else
                    save(material);

                rownr++;
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

        if(getIndex(header, "Flag material") >= 0){
            material.setFlagMaterial(getFlagValueCSV(nextRecord[getIndex(header, "Flag material")]));
        }

        if(getIndex(header, "Comment") >= 0){
            material.setComment(nextRecord[getIndex(header, "Comment")]);
        }

        return material;
    }

    private Boolean getFlagValueCSV(String cellValue) {
        if (cellValue != null && !cellValue.isEmpty()) {
            return Boolean.parseBoolean(cellValue);
        }
        return false;
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


    /* --------------------------- SUBMIT CHANGES --------------------------- */

    @Override
    public void submitChanges(List<Object> data){

        for (Object item : data) {
            if (item instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) item;

                updateObject(
                    extractLong(dataMap.get("materialId")),
                    extractInt(dataMap.get("newSST")),
                    extractInt(dataMap.get("newST")),
                    (String) dataMap.get("newComment"),
                    (Boolean) dataMap.get("flag")
                );
            }
        }
    }

    public void updateObject(Long materialId, int newSST, int newST, String newComment, Boolean flag){

        materialRepository
        .findById(materialId)
        .map(existingMaterial -> {
            existingMaterial.setNewSAPSafetyStock(newSST);
            existingMaterial.setNewSAPSafetyTime(newST);
            existingMaterial.setDeltaSST(existingMaterial.getProposedSST() - newSST);
            existingMaterial.setDeltaST(existingMaterial.getProposedST() - newST);
            existingMaterial.setComment(newComment);
            existingMaterial.setFlagMaterial(flag);
            return existingMaterial;
        })
        .map(materialRepository::save);

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
}
