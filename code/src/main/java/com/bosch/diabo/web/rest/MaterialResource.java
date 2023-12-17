package com.bosch.diabo.web.rest;

import com.bosch.diabo.domain.Material;
import com.bosch.diabo.repository.MaterialRepository;
import com.bosch.diabo.service.MaterialQueryService;
import com.bosch.diabo.service.MaterialService;
import com.bosch.diabo.service.criteria.MaterialCriteria;
import com.bosch.diabo.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import java.io.IOException;

/**
 * REST controller for managing {@link com.bosch.diabo.domain.Material}.
 */
@RestController
@RequestMapping("/api")
public class MaterialResource {
    
    @Value("${file.upload.directory}")
    private String uploadDirectory;

    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private static final String ENTITY_NAME = "material";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialService materialService;

    private final MaterialRepository materialRepository;

     private final MaterialQueryService materialQueryService;

    public MaterialResource(
        MaterialService materialService,
        MaterialRepository materialRepository,
        MaterialQueryService materialQueryService
    ) {
        this.materialService = materialService;
        this.materialRepository = materialRepository;
        this.materialQueryService = materialQueryService;
    }

    /**
     * {@code POST  /materials} : Create a new material.
     *
     * @param material the material to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new material, or with status {@code 400 (Bad Request)} if the material has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materials")
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to save Material : {}", material);
        if (material.getId() != null) {
            throw new BadRequestAlertException("A new material cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Material result = materialService.save(material);
        return ResponseEntity
            .created(new URI("/api/materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materials/:id} : Updates an existing material.
     *
     * @param id the id of the material to save.
     * @param material the material to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated material,
     * or with status {@code 400 (Bad Request)} if the material is not valid,
     * or with status {@code 500 (Internal Server Error)} if the material couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materials/{id}")
    public ResponseEntity<Material> updateMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Material material
    ) throws URISyntaxException {
        log.debug("REST request to update Material : {}, {}", id, material);
        if (material.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, material.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Material result = materialService.update(material);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, material.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /materials/:id} : Partial updates given fields of an existing material, field will ignore if it is null
     *
     * @param id the id of the material to save.
     * @param material the material to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated material,
     * or with status {@code 400 (Bad Request)} if the material is not valid,
     * or with status {@code 404 (Not Found)} if the material is not found,
     * or with status {@code 500 (Internal Server Error)} if the material couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/materials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Material> partialUpdateMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Material material
    ) throws URISyntaxException {
        log.debug("REST request to partial update Material partially : {}, {}", id, material);
        if (material.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, material.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Material> result = materialService.partialUpdate(material);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, material.getId().toString())
        );
    }

    /**
     * {@code GET  /materials} : get all the materials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materials in body.
     */
    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getAllMaterials(
        MaterialCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Materials by criteria: {}", criteria);
        Page<Material> page = materialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /materials/count} : count all the materials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/materials/count")
    public ResponseEntity<Long> countMaterials(MaterialCriteria criteria) {
        log.debug("REST request to count Materials by criteria: {}", criteria);
        return ResponseEntity.ok().body(materialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /materials/:id} : get the "id" material.
     *
     * @param id the id of the material to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the material, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materials/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(material);
    }

    /**
     * {@code DELETE  /materials/:id} : delete the "id" material.
     *
     * @param id the id of the material to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /materials/uploadFileReplace} : upload file to replace current database
     *
     * @param multipartFile the file with data to add to the database.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error)} if the server can't read the file.
     * @throws IOException if file reading failed
     */
    @PostMapping("/materials/uploadFileReplace")
    public ResponseEntity<String> uploadFileReplace(@RequestParam("file") MultipartFile multipartFile) {
        log.debug("REST request to upload file");
        
        File convertedFile = new File(uploadDirectory+multipartFile.getOriginalFilename());
        
        try {
            multipartFile.transferTo(convertedFile);
            materialService.uploadFileReplace(convertedFile);
            convertedFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
        // Return a response indicating success or failure
        return ResponseEntity.ok("");
    }
    /**
     * {@code POST  /materials/uploadFileReplace} : upload file to add or update current database
     *
     * @param multipartFile the file with data to add or update the database.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error)} if the server can't read the file.
     * @throws IOException if file reading failed
     */
    @PostMapping("/materials/uploadFileAddOrUpdate")
    public ResponseEntity<String> uploadFileAddOrUpdate(@RequestParam("file") MultipartFile multipartFile) {
        log.debug("REST request to upload file");
        
        File convertedFile = new File(uploadDirectory+multipartFile.getOriginalFilename());
        
        try {
            multipartFile.transferTo(convertedFile);
            materialService.uploadFileAddOrUpdate(convertedFile);
            convertedFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
        // Return a response indicating success or failure
        return ResponseEntity.ok("");
    }
    /**
     * {@code POST  /materials/submitChanges} : submit new changes to the database
     *
     * @param data new data to be submited
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 500 (Internal Server Error)} if the server can't read the file.
     * @throws IOException if file reading failed
     */
    @PostMapping("/materials/submitChanges")
    public ResponseEntity<ByteArrayResource> submitChanges(@RequestBody List<Object> data) {
        log.debug("REST request submit changes");
        materialService.submitChanges(data);
        List<Material> data2 = materialService.getMatrialChanged(data);
        byte[] file;
        try {
            file = generateExcelFile(data2);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "DataChanges.xlsx")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new ByteArrayResource(file));
        
    }

    @GetMapping("/materials/download") 
    public ResponseEntity<ByteArrayResource> downloadExcel() throws IOException {
        // Query database and get result set
        String fileName = "Data.xlsx";
        MaterialCriteria mc = new MaterialCriteria();
        List<Material> materials = materialQueryService.findByCriteria(mc);
        byte[] data;

        try{
            data = generateExcelFile(materials);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new ByteArrayResource(data));
    }

    public byte[] generateExcelFile(List<Material> materials) throws IOException{
        System.out.println("Creating Workbook");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Data");
        // Add headers
        XSSFRow headerRow = sheet.createRow(0);
        System.out.println("Adding Headers");
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Material");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("ABC Classification");
        headerRow.createCell(4).setCellValue("Average Supplier Delay");
        headerRow.createCell(5).setCellValue("Maximum Supplier Delay");
        headerRow.createCell(6).setCellValue("Service Level");
        headerRow.createCell(7).setCellValue("Current SAP Safety Stock");
        headerRow.createCell(8).setCellValue("Proposed SST");
        headerRow.createCell(9).setCellValue("Delta SST");
        headerRow.createCell(10).setCellValue("Current SAP Safety Time");
        headerRow.createCell(11).setCellValue("Proposed ST");
        headerRow.createCell(12).setCellValue("Delta ST");
        headerRow.createCell(13).setCellValue("Open SAP md04");
        headerRow.createCell(14).setCellValue("Current Inventory Value");
        headerRow.createCell(15).setCellValue("Unit Cost");
        headerRow.createCell(16).setCellValue("Average Demand");
        headerRow.createCell(17).setCellValue("Average Inventory Effect After Change");
        headerRow.createCell(18).setCellValue("New SAP Safety Stock");
        headerRow.createCell(19).setCellValue("New SAP Safety Time");
        headerRow.createCell(20).setCellValue("Flag");
        headerRow.createCell(21).setCellValue("Comments");
        // Add data 
        System.out.println("Adding Data");
        int rowNum = 1;
        for (Material mat : materials) {
            System.out.println("Added Material: " + rowNum);
            try{
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(mat.getId());
                row.createCell(1).setCellValue(mat.getMaterial());
                row.createCell(2).setCellValue(mat.getDescription());
                row.createCell(3).setCellValue(mat.getAbcClassification().toString());
                row.createCell(4).setCellValue(mat.getAvgSupplierDelay());
                row.createCell(5).setCellValue(mat.getMaxSupplierDelay());
                row.createCell(6).setCellValue(mat.getServiceLevel());
                row.createCell(7).setCellValue(mat.getCurrSAPSafetyStock());
                row.createCell(8).setCellValue(mat.getProposedSST());
                row.createCell(9).setCellValue(mat.getDeltaSST());
                row.createCell(10).setCellValue(mat.getCurrentSAPSafeTime());
                row.createCell(11).setCellValue(mat.getProposedST());
                row.createCell(12).setCellValue(mat.getDeltaST());
                row.createCell(13).setCellValue(mat.getOpenSAPmd04());
                row.createCell(14).setCellValue(mat.getCurrentInventoryValue());
                row.createCell(15).setCellValue(mat.getUnitCost());
                row.createCell(16).setCellValue(mat.getAvgDemand());
                row.createCell(17).setCellValue(mat.getAvgInventoryEffectAfterChange());
                row.createCell(18).setCellValue(mat.getNewSAPSafetyStock());
                row.createCell(19).setCellValue(mat.getNewSAPSafetyTime());
                row.createCell(20).setCellValue(mat.getFlagMaterial());
                row.createCell(21).setCellValue(mat.getComment());
            }
            catch (Exception e){
                System.out.println("No Material for row " + rowNum);
            }
        }
        System.out.println("Ended Adding DATA");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        System.out.println("Workbook write outputstream");
        byte[] r = outputStream.toByteArray();
        System.out.println("outputstream to byte []");
        outputStream.close();
        System.out.println("OutputStream closed");
        workbook.close();
        System.out.println("Workbook closed");

        return r;
    }

    
}
