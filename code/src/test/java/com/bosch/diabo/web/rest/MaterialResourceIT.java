package com.bosch.diabo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bosch.diabo.IntegrationTest;
import com.bosch.diabo.domain.Material;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.repository.MaterialRepository;
import com.bosch.diabo.service.criteria.MaterialCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialResourceIT {

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ABCClassification DEFAULT_ABC_CLASSIFICATION = ABCClassification.A;
    private static final ABCClassification UPDATED_ABC_CLASSIFICATION = ABCClassification.B;

    private static final Float DEFAULT_AVG_SUPPLIER_DELAY = 1F;
    private static final Float UPDATED_AVG_SUPPLIER_DELAY = 2F;
    private static final Float SMALLER_AVG_SUPPLIER_DELAY = 1F - 1F;

    private static final Float DEFAULT_MAX_SUPPLIER_DELAY = 1F;
    private static final Float UPDATED_MAX_SUPPLIER_DELAY = 2F;
    private static final Float SMALLER_MAX_SUPPLIER_DELAY = 1F - 1F;

    private static final Float DEFAULT_SERVICE_LEVEL = 1F;
    private static final Float UPDATED_SERVICE_LEVEL = 2F;
    private static final Float SMALLER_SERVICE_LEVEL = 1F - 1F;

    private static final Integer DEFAULT_CURR_SAP_SAFETY_STOCK = 1;
    private static final Integer UPDATED_CURR_SAP_SAFETY_STOCK = 2;
    private static final Integer SMALLER_CURR_SAP_SAFETY_STOCK = 1 - 1;

    private static final Integer DEFAULT_PROPOSED_SST = 1;
    private static final Integer UPDATED_PROPOSED_SST = 2;
    private static final Integer SMALLER_PROPOSED_SST = 1 - 1;

    private static final Integer DEFAULT_DELTA_SST = 1;
    private static final Integer UPDATED_DELTA_SST = 2;
    private static final Integer SMALLER_DELTA_SST = 1 - 1;

    private static final Integer DEFAULT_CURRENT_SAP_SAFE_TIME = 1;
    private static final Integer UPDATED_CURRENT_SAP_SAFE_TIME = 2;
    private static final Integer SMALLER_CURRENT_SAP_SAFE_TIME = 1 - 1;

    private static final Integer DEFAULT_PROPOSED_ST = 1;
    private static final Integer UPDATED_PROPOSED_ST = 2;
    private static final Integer SMALLER_PROPOSED_ST = 1 - 1;

    private static final Integer DEFAULT_DELTA_ST = 1;
    private static final Integer UPDATED_DELTA_ST = 2;
    private static final Integer SMALLER_DELTA_ST = 1 - 1;

    private static final String DEFAULT_OPEN_SA_PMD_04 = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_SA_PMD_04 = "BBBBBBBBBB";

    private static final Float DEFAULT_CURRENT_INVENTORY_VALUE = 1F;
    private static final Float UPDATED_CURRENT_INVENTORY_VALUE = 2F;
    private static final Float SMALLER_CURRENT_INVENTORY_VALUE = 1F - 1F;

    private static final Float DEFAULT_UNIT_COST = 1F;
    private static final Float UPDATED_UNIT_COST = 2F;
    private static final Float SMALLER_UNIT_COST = 1F - 1F;

    private static final Integer DEFAULT_AVG_DEMAND = 1;
    private static final Integer UPDATED_AVG_DEMAND = 2;
    private static final Integer SMALLER_AVG_DEMAND = 1 - 1;

    private static final Float DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE = 1F;
    private static final Float UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE = 2F;
    private static final Float SMALLER_AVG_INVENTORY_EFFECT_AFTER_CHANGE = 1F - 1F;

    private static final Integer DEFAULT_NEW_SAP_SAFETY_STOCK = 1;
    private static final Integer UPDATED_NEW_SAP_SAFETY_STOCK = 2;
    private static final Integer SMALLER_NEW_SAP_SAFETY_STOCK = 1 - 1;

    private static final Integer DEFAULT_NEW_SAP_SAFETY_TIME = 1;
    private static final Integer UPDATED_NEW_SAP_SAFETY_TIME = 2;
    private static final Integer SMALLER_NEW_SAP_SAFETY_TIME = 1 - 1;

    private static final Boolean DEFAULT_FLAG_MATERIAL = false;
    private static final Boolean UPDATED_FLAG_MATERIAL = true;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialMockMvc;

    private Material material;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material()
            .material(DEFAULT_MATERIAL)
            .description(DEFAULT_DESCRIPTION)
            .abcClassification(DEFAULT_ABC_CLASSIFICATION)
            .avgSupplierDelay(DEFAULT_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(DEFAULT_MAX_SUPPLIER_DELAY)
            .serviceLevel(DEFAULT_SERVICE_LEVEL)
            .currSAPSafetyStock(DEFAULT_CURR_SAP_SAFETY_STOCK)
            .proposedSST(DEFAULT_PROPOSED_SST)
            .deltaSST(DEFAULT_DELTA_SST)
            .currentSAPSafeTime(DEFAULT_CURRENT_SAP_SAFE_TIME)
            .proposedST(DEFAULT_PROPOSED_ST)
            .deltaST(DEFAULT_DELTA_ST)
            .openSAPmd04(DEFAULT_OPEN_SA_PMD_04)
            .currentInventoryValue(DEFAULT_CURRENT_INVENTORY_VALUE)
            .unitCost(DEFAULT_UNIT_COST)
            .avgDemand(DEFAULT_AVG_DEMAND)
            .avgInventoryEffectAfterChange(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSAPSafetyStock(DEFAULT_NEW_SAP_SAFETY_STOCK)
            .newSAPSafetyTime(DEFAULT_NEW_SAP_SAFETY_TIME)
            .flagMaterial(DEFAULT_FLAG_MATERIAL)
            .comment(DEFAULT_COMMENT);
        return material;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material()
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .currSAPSafetyStock(UPDATED_CURR_SAP_SAFETY_STOCK)
            .proposedSST(UPDATED_PROPOSED_SST)
            .deltaSST(UPDATED_DELTA_SST)
            .currentSAPSafeTime(UPDATED_CURRENT_SAP_SAFE_TIME)
            .proposedST(UPDATED_PROPOSED_ST)
            .deltaST(UPDATED_DELTA_ST)
            .openSAPmd04(UPDATED_OPEN_SA_PMD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .unitCost(UPDATED_UNIT_COST)
            .avgDemand(UPDATED_AVG_DEMAND)
            .avgInventoryEffectAfterChange(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSAPSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .newSAPSafetyTime(UPDATED_NEW_SAP_SAFETY_TIME)
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .comment(UPDATED_COMMENT);
        return material;
    }

    @BeforeEach
    public void initTest() {
        material = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterial() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();
        // Create the Material
        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isCreated());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testMaterial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMaterial.getAbcClassification()).isEqualTo(DEFAULT_ABC_CLASSIFICATION);
        assertThat(testMaterial.getAvgSupplierDelay()).isEqualTo(DEFAULT_AVG_SUPPLIER_DELAY);
        assertThat(testMaterial.getMaxSupplierDelay()).isEqualTo(DEFAULT_MAX_SUPPLIER_DELAY);
        assertThat(testMaterial.getServiceLevel()).isEqualTo(DEFAULT_SERVICE_LEVEL);
        assertThat(testMaterial.getCurrSAPSafetyStock()).isEqualTo(DEFAULT_CURR_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getProposedSST()).isEqualTo(DEFAULT_PROPOSED_SST);
        assertThat(testMaterial.getDeltaSST()).isEqualTo(DEFAULT_DELTA_SST);
        assertThat(testMaterial.getCurrentSAPSafeTime()).isEqualTo(DEFAULT_CURRENT_SAP_SAFE_TIME);
        assertThat(testMaterial.getProposedST()).isEqualTo(DEFAULT_PROPOSED_ST);
        assertThat(testMaterial.getDeltaST()).isEqualTo(DEFAULT_DELTA_ST);
        assertThat(testMaterial.getOpenSAPmd04()).isEqualTo(DEFAULT_OPEN_SA_PMD_04);
        assertThat(testMaterial.getCurrentInventoryValue()).isEqualTo(DEFAULT_CURRENT_INVENTORY_VALUE);
        assertThat(testMaterial.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
        assertThat(testMaterial.getAvgDemand()).isEqualTo(DEFAULT_AVG_DEMAND);
        assertThat(testMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testMaterial.getNewSAPSafetyStock()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getNewSAPSafetyTime()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_TIME);
        assertThat(testMaterial.getFlagMaterial()).isEqualTo(DEFAULT_FLAG_MATERIAL);
        assertThat(testMaterial.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void createMaterialWithExistingId() throws Exception {
        // Create the Material with an existing ID
        material.setId(1L);

        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].abcClassification").value(hasItem(DEFAULT_ABC_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].avgSupplierDelay").value(hasItem(DEFAULT_AVG_SUPPLIER_DELAY.doubleValue())))
            .andExpect(jsonPath("$.[*].maxSupplierDelay").value(hasItem(DEFAULT_MAX_SUPPLIER_DELAY.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceLevel").value(hasItem(DEFAULT_SERVICE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].currSAPSafetyStock").value(hasItem(DEFAULT_CURR_SAP_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].proposedSST").value(hasItem(DEFAULT_PROPOSED_SST)))
            .andExpect(jsonPath("$.[*].deltaSST").value(hasItem(DEFAULT_DELTA_SST)))
            .andExpect(jsonPath("$.[*].currentSAPSafeTime").value(hasItem(DEFAULT_CURRENT_SAP_SAFE_TIME)))
            .andExpect(jsonPath("$.[*].proposedST").value(hasItem(DEFAULT_PROPOSED_ST)))
            .andExpect(jsonPath("$.[*].deltaST").value(hasItem(DEFAULT_DELTA_ST)))
            .andExpect(jsonPath("$.[*].openSAPmd04").value(hasItem(DEFAULT_OPEN_SA_PMD_04)))
            .andExpect(jsonPath("$.[*].currentInventoryValue").value(hasItem(DEFAULT_CURRENT_INVENTORY_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].avgDemand").value(hasItem(DEFAULT_AVG_DEMAND)))
            .andExpect(
                jsonPath("$.[*].avgInventoryEffectAfterChange").value(hasItem(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].newSAPSafetyStock").value(hasItem(DEFAULT_NEW_SAP_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].newSAPSafetyTime").value(hasItem(DEFAULT_NEW_SAP_SAFETY_TIME)))
            .andExpect(jsonPath("$.[*].flagMaterial").value(hasItem(DEFAULT_FLAG_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }

    @Test
    @Transactional
    void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(material.getId().intValue()))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.abcClassification").value(DEFAULT_ABC_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.avgSupplierDelay").value(DEFAULT_AVG_SUPPLIER_DELAY.doubleValue()))
            .andExpect(jsonPath("$.maxSupplierDelay").value(DEFAULT_MAX_SUPPLIER_DELAY.doubleValue()))
            .andExpect(jsonPath("$.serviceLevel").value(DEFAULT_SERVICE_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.currSAPSafetyStock").value(DEFAULT_CURR_SAP_SAFETY_STOCK))
            .andExpect(jsonPath("$.proposedSST").value(DEFAULT_PROPOSED_SST))
            .andExpect(jsonPath("$.deltaSST").value(DEFAULT_DELTA_SST))
            .andExpect(jsonPath("$.currentSAPSafeTime").value(DEFAULT_CURRENT_SAP_SAFE_TIME))
            .andExpect(jsonPath("$.proposedST").value(DEFAULT_PROPOSED_ST))
            .andExpect(jsonPath("$.deltaST").value(DEFAULT_DELTA_ST))
            .andExpect(jsonPath("$.openSAPmd04").value(DEFAULT_OPEN_SA_PMD_04))
            .andExpect(jsonPath("$.currentInventoryValue").value(DEFAULT_CURRENT_INVENTORY_VALUE.doubleValue()))
            .andExpect(jsonPath("$.unitCost").value(DEFAULT_UNIT_COST.doubleValue()))
            .andExpect(jsonPath("$.avgDemand").value(DEFAULT_AVG_DEMAND))
            .andExpect(jsonPath("$.avgInventoryEffectAfterChange").value(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE.doubleValue()))
            .andExpect(jsonPath("$.newSAPSafetyStock").value(DEFAULT_NEW_SAP_SAFETY_STOCK))
            .andExpect(jsonPath("$.newSAPSafetyTime").value(DEFAULT_NEW_SAP_SAFETY_TIME))
            .andExpect(jsonPath("$.flagMaterial").value(DEFAULT_FLAG_MATERIAL.booleanValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    void getMaterialsByIdFiltering() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        Long id = material.getId();

        defaultMaterialShouldBeFound("id.equals=" + id);
        defaultMaterialShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where material equals to DEFAULT_MATERIAL
        defaultMaterialShouldBeFound("material.equals=" + DEFAULT_MATERIAL);

        // Get all the materialList where material equals to UPDATED_MATERIAL
        defaultMaterialShouldNotBeFound("material.equals=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where material in DEFAULT_MATERIAL or UPDATED_MATERIAL
        defaultMaterialShouldBeFound("material.in=" + DEFAULT_MATERIAL + "," + UPDATED_MATERIAL);

        // Get all the materialList where material equals to UPDATED_MATERIAL
        defaultMaterialShouldNotBeFound("material.in=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where material is not null
        defaultMaterialShouldBeFound("material.specified=true");

        // Get all the materialList where material is null
        defaultMaterialShouldNotBeFound("material.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByMaterialContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where material contains DEFAULT_MATERIAL
        defaultMaterialShouldBeFound("material.contains=" + DEFAULT_MATERIAL);

        // Get all the materialList where material contains UPDATED_MATERIAL
        defaultMaterialShouldNotBeFound("material.contains=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaterialNotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where material does not contain DEFAULT_MATERIAL
        defaultMaterialShouldNotBeFound("material.doesNotContain=" + DEFAULT_MATERIAL);

        // Get all the materialList where material does not contain UPDATED_MATERIAL
        defaultMaterialShouldBeFound("material.doesNotContain=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllMaterialsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where description equals to DEFAULT_DESCRIPTION
        defaultMaterialShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the materialList where description equals to UPDATED_DESCRIPTION
        defaultMaterialShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMaterialShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the materialList where description equals to UPDATED_DESCRIPTION
        defaultMaterialShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where description is not null
        defaultMaterialShouldBeFound("description.specified=true");

        // Get all the materialList where description is null
        defaultMaterialShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where description contains DEFAULT_DESCRIPTION
        defaultMaterialShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the materialList where description contains UPDATED_DESCRIPTION
        defaultMaterialShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where description does not contain DEFAULT_DESCRIPTION
        defaultMaterialShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the materialList where description does not contain UPDATED_DESCRIPTION
        defaultMaterialShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMaterialsByAbcClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where abcClassification equals to DEFAULT_ABC_CLASSIFICATION
        defaultMaterialShouldBeFound("abcClassification.equals=" + DEFAULT_ABC_CLASSIFICATION);

        // Get all the materialList where abcClassification equals to UPDATED_ABC_CLASSIFICATION
        defaultMaterialShouldNotBeFound("abcClassification.equals=" + UPDATED_ABC_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByAbcClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where abcClassification in DEFAULT_ABC_CLASSIFICATION or UPDATED_ABC_CLASSIFICATION
        defaultMaterialShouldBeFound("abcClassification.in=" + DEFAULT_ABC_CLASSIFICATION + "," + UPDATED_ABC_CLASSIFICATION);

        // Get all the materialList where abcClassification equals to UPDATED_ABC_CLASSIFICATION
        defaultMaterialShouldNotBeFound("abcClassification.in=" + UPDATED_ABC_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByAbcClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where abcClassification is not null
        defaultMaterialShouldBeFound("abcClassification.specified=true");

        // Get all the materialList where abcClassification is null
        defaultMaterialShouldNotBeFound("abcClassification.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay equals to DEFAULT_AVG_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("avgSupplierDelay.equals=" + DEFAULT_AVG_SUPPLIER_DELAY);

        // Get all the materialList where avgSupplierDelay equals to UPDATED_AVG_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("avgSupplierDelay.equals=" + UPDATED_AVG_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay in DEFAULT_AVG_SUPPLIER_DELAY or UPDATED_AVG_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("avgSupplierDelay.in=" + DEFAULT_AVG_SUPPLIER_DELAY + "," + UPDATED_AVG_SUPPLIER_DELAY);

        // Get all the materialList where avgSupplierDelay equals to UPDATED_AVG_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("avgSupplierDelay.in=" + UPDATED_AVG_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay is not null
        defaultMaterialShouldBeFound("avgSupplierDelay.specified=true");

        // Get all the materialList where avgSupplierDelay is null
        defaultMaterialShouldNotBeFound("avgSupplierDelay.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay is greater than or equal to DEFAULT_AVG_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("avgSupplierDelay.greaterThanOrEqual=" + DEFAULT_AVG_SUPPLIER_DELAY);

        // Get all the materialList where avgSupplierDelay is greater than or equal to UPDATED_AVG_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("avgSupplierDelay.greaterThanOrEqual=" + UPDATED_AVG_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay is less than or equal to DEFAULT_AVG_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("avgSupplierDelay.lessThanOrEqual=" + DEFAULT_AVG_SUPPLIER_DELAY);

        // Get all the materialList where avgSupplierDelay is less than or equal to SMALLER_AVG_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("avgSupplierDelay.lessThanOrEqual=" + SMALLER_AVG_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay is less than DEFAULT_AVG_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("avgSupplierDelay.lessThan=" + DEFAULT_AVG_SUPPLIER_DELAY);

        // Get all the materialList where avgSupplierDelay is less than UPDATED_AVG_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("avgSupplierDelay.lessThan=" + UPDATED_AVG_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgSupplierDelayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgSupplierDelay is greater than DEFAULT_AVG_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("avgSupplierDelay.greaterThan=" + DEFAULT_AVG_SUPPLIER_DELAY);

        // Get all the materialList where avgSupplierDelay is greater than SMALLER_AVG_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("avgSupplierDelay.greaterThan=" + SMALLER_AVG_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay equals to DEFAULT_MAX_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("maxSupplierDelay.equals=" + DEFAULT_MAX_SUPPLIER_DELAY);

        // Get all the materialList where maxSupplierDelay equals to UPDATED_MAX_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("maxSupplierDelay.equals=" + UPDATED_MAX_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay in DEFAULT_MAX_SUPPLIER_DELAY or UPDATED_MAX_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("maxSupplierDelay.in=" + DEFAULT_MAX_SUPPLIER_DELAY + "," + UPDATED_MAX_SUPPLIER_DELAY);

        // Get all the materialList where maxSupplierDelay equals to UPDATED_MAX_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("maxSupplierDelay.in=" + UPDATED_MAX_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay is not null
        defaultMaterialShouldBeFound("maxSupplierDelay.specified=true");

        // Get all the materialList where maxSupplierDelay is null
        defaultMaterialShouldNotBeFound("maxSupplierDelay.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay is greater than or equal to DEFAULT_MAX_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("maxSupplierDelay.greaterThanOrEqual=" + DEFAULT_MAX_SUPPLIER_DELAY);

        // Get all the materialList where maxSupplierDelay is greater than or equal to UPDATED_MAX_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("maxSupplierDelay.greaterThanOrEqual=" + UPDATED_MAX_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay is less than or equal to DEFAULT_MAX_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("maxSupplierDelay.lessThanOrEqual=" + DEFAULT_MAX_SUPPLIER_DELAY);

        // Get all the materialList where maxSupplierDelay is less than or equal to SMALLER_MAX_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("maxSupplierDelay.lessThanOrEqual=" + SMALLER_MAX_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay is less than DEFAULT_MAX_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("maxSupplierDelay.lessThan=" + DEFAULT_MAX_SUPPLIER_DELAY);

        // Get all the materialList where maxSupplierDelay is less than UPDATED_MAX_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("maxSupplierDelay.lessThan=" + UPDATED_MAX_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaxSupplierDelayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where maxSupplierDelay is greater than DEFAULT_MAX_SUPPLIER_DELAY
        defaultMaterialShouldNotBeFound("maxSupplierDelay.greaterThan=" + DEFAULT_MAX_SUPPLIER_DELAY);

        // Get all the materialList where maxSupplierDelay is greater than SMALLER_MAX_SUPPLIER_DELAY
        defaultMaterialShouldBeFound("maxSupplierDelay.greaterThan=" + SMALLER_MAX_SUPPLIER_DELAY);
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel equals to DEFAULT_SERVICE_LEVEL
        defaultMaterialShouldBeFound("serviceLevel.equals=" + DEFAULT_SERVICE_LEVEL);

        // Get all the materialList where serviceLevel equals to UPDATED_SERVICE_LEVEL
        defaultMaterialShouldNotBeFound("serviceLevel.equals=" + UPDATED_SERVICE_LEVEL);
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel in DEFAULT_SERVICE_LEVEL or UPDATED_SERVICE_LEVEL
        defaultMaterialShouldBeFound("serviceLevel.in=" + DEFAULT_SERVICE_LEVEL + "," + UPDATED_SERVICE_LEVEL);

        // Get all the materialList where serviceLevel equals to UPDATED_SERVICE_LEVEL
        defaultMaterialShouldNotBeFound("serviceLevel.in=" + UPDATED_SERVICE_LEVEL);
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel is not null
        defaultMaterialShouldBeFound("serviceLevel.specified=true");

        // Get all the materialList where serviceLevel is null
        defaultMaterialShouldNotBeFound("serviceLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel is greater than or equal to DEFAULT_SERVICE_LEVEL
        defaultMaterialShouldBeFound("serviceLevel.greaterThanOrEqual=" + DEFAULT_SERVICE_LEVEL);

        // Get all the materialList where serviceLevel is greater than or equal to UPDATED_SERVICE_LEVEL
        defaultMaterialShouldNotBeFound("serviceLevel.greaterThanOrEqual=" + UPDATED_SERVICE_LEVEL);
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel is less than or equal to DEFAULT_SERVICE_LEVEL
        defaultMaterialShouldBeFound("serviceLevel.lessThanOrEqual=" + DEFAULT_SERVICE_LEVEL);

        // Get all the materialList where serviceLevel is less than or equal to SMALLER_SERVICE_LEVEL
        defaultMaterialShouldNotBeFound("serviceLevel.lessThanOrEqual=" + SMALLER_SERVICE_LEVEL);
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel is less than DEFAULT_SERVICE_LEVEL
        defaultMaterialShouldNotBeFound("serviceLevel.lessThan=" + DEFAULT_SERVICE_LEVEL);

        // Get all the materialList where serviceLevel is less than UPDATED_SERVICE_LEVEL
        defaultMaterialShouldBeFound("serviceLevel.lessThan=" + UPDATED_SERVICE_LEVEL);
    }

    @Test
    @Transactional
    void getAllMaterialsByServiceLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where serviceLevel is greater than DEFAULT_SERVICE_LEVEL
        defaultMaterialShouldNotBeFound("serviceLevel.greaterThan=" + DEFAULT_SERVICE_LEVEL);

        // Get all the materialList where serviceLevel is greater than SMALLER_SERVICE_LEVEL
        defaultMaterialShouldBeFound("serviceLevel.greaterThan=" + SMALLER_SERVICE_LEVEL);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock equals to DEFAULT_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("currSAPSafetyStock.equals=" + DEFAULT_CURR_SAP_SAFETY_STOCK);

        // Get all the materialList where currSAPSafetyStock equals to UPDATED_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.equals=" + UPDATED_CURR_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock in DEFAULT_CURR_SAP_SAFETY_STOCK or UPDATED_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("currSAPSafetyStock.in=" + DEFAULT_CURR_SAP_SAFETY_STOCK + "," + UPDATED_CURR_SAP_SAFETY_STOCK);

        // Get all the materialList where currSAPSafetyStock equals to UPDATED_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.in=" + UPDATED_CURR_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock is not null
        defaultMaterialShouldBeFound("currSAPSafetyStock.specified=true");

        // Get all the materialList where currSAPSafetyStock is null
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock is greater than or equal to DEFAULT_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("currSAPSafetyStock.greaterThanOrEqual=" + DEFAULT_CURR_SAP_SAFETY_STOCK);

        // Get all the materialList where currSAPSafetyStock is greater than or equal to UPDATED_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.greaterThanOrEqual=" + UPDATED_CURR_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock is less than or equal to DEFAULT_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("currSAPSafetyStock.lessThanOrEqual=" + DEFAULT_CURR_SAP_SAFETY_STOCK);

        // Get all the materialList where currSAPSafetyStock is less than or equal to SMALLER_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.lessThanOrEqual=" + SMALLER_CURR_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock is less than DEFAULT_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.lessThan=" + DEFAULT_CURR_SAP_SAFETY_STOCK);

        // Get all the materialList where currSAPSafetyStock is less than UPDATED_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("currSAPSafetyStock.lessThan=" + UPDATED_CURR_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrSAPSafetyStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currSAPSafetyStock is greater than DEFAULT_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("currSAPSafetyStock.greaterThan=" + DEFAULT_CURR_SAP_SAFETY_STOCK);

        // Get all the materialList where currSAPSafetyStock is greater than SMALLER_CURR_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("currSAPSafetyStock.greaterThan=" + SMALLER_CURR_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST equals to DEFAULT_PROPOSED_SST
        defaultMaterialShouldBeFound("proposedSST.equals=" + DEFAULT_PROPOSED_SST);

        // Get all the materialList where proposedSST equals to UPDATED_PROPOSED_SST
        defaultMaterialShouldNotBeFound("proposedSST.equals=" + UPDATED_PROPOSED_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST in DEFAULT_PROPOSED_SST or UPDATED_PROPOSED_SST
        defaultMaterialShouldBeFound("proposedSST.in=" + DEFAULT_PROPOSED_SST + "," + UPDATED_PROPOSED_SST);

        // Get all the materialList where proposedSST equals to UPDATED_PROPOSED_SST
        defaultMaterialShouldNotBeFound("proposedSST.in=" + UPDATED_PROPOSED_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST is not null
        defaultMaterialShouldBeFound("proposedSST.specified=true");

        // Get all the materialList where proposedSST is null
        defaultMaterialShouldNotBeFound("proposedSST.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST is greater than or equal to DEFAULT_PROPOSED_SST
        defaultMaterialShouldBeFound("proposedSST.greaterThanOrEqual=" + DEFAULT_PROPOSED_SST);

        // Get all the materialList where proposedSST is greater than or equal to UPDATED_PROPOSED_SST
        defaultMaterialShouldNotBeFound("proposedSST.greaterThanOrEqual=" + UPDATED_PROPOSED_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST is less than or equal to DEFAULT_PROPOSED_SST
        defaultMaterialShouldBeFound("proposedSST.lessThanOrEqual=" + DEFAULT_PROPOSED_SST);

        // Get all the materialList where proposedSST is less than or equal to SMALLER_PROPOSED_SST
        defaultMaterialShouldNotBeFound("proposedSST.lessThanOrEqual=" + SMALLER_PROPOSED_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST is less than DEFAULT_PROPOSED_SST
        defaultMaterialShouldNotBeFound("proposedSST.lessThan=" + DEFAULT_PROPOSED_SST);

        // Get all the materialList where proposedSST is less than UPDATED_PROPOSED_SST
        defaultMaterialShouldBeFound("proposedSST.lessThan=" + UPDATED_PROPOSED_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSSTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedSST is greater than DEFAULT_PROPOSED_SST
        defaultMaterialShouldNotBeFound("proposedSST.greaterThan=" + DEFAULT_PROPOSED_SST);

        // Get all the materialList where proposedSST is greater than SMALLER_PROPOSED_SST
        defaultMaterialShouldBeFound("proposedSST.greaterThan=" + SMALLER_PROPOSED_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST equals to DEFAULT_DELTA_SST
        defaultMaterialShouldBeFound("deltaSST.equals=" + DEFAULT_DELTA_SST);

        // Get all the materialList where deltaSST equals to UPDATED_DELTA_SST
        defaultMaterialShouldNotBeFound("deltaSST.equals=" + UPDATED_DELTA_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST in DEFAULT_DELTA_SST or UPDATED_DELTA_SST
        defaultMaterialShouldBeFound("deltaSST.in=" + DEFAULT_DELTA_SST + "," + UPDATED_DELTA_SST);

        // Get all the materialList where deltaSST equals to UPDATED_DELTA_SST
        defaultMaterialShouldNotBeFound("deltaSST.in=" + UPDATED_DELTA_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST is not null
        defaultMaterialShouldBeFound("deltaSST.specified=true");

        // Get all the materialList where deltaSST is null
        defaultMaterialShouldNotBeFound("deltaSST.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST is greater than or equal to DEFAULT_DELTA_SST
        defaultMaterialShouldBeFound("deltaSST.greaterThanOrEqual=" + DEFAULT_DELTA_SST);

        // Get all the materialList where deltaSST is greater than or equal to UPDATED_DELTA_SST
        defaultMaterialShouldNotBeFound("deltaSST.greaterThanOrEqual=" + UPDATED_DELTA_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST is less than or equal to DEFAULT_DELTA_SST
        defaultMaterialShouldBeFound("deltaSST.lessThanOrEqual=" + DEFAULT_DELTA_SST);

        // Get all the materialList where deltaSST is less than or equal to SMALLER_DELTA_SST
        defaultMaterialShouldNotBeFound("deltaSST.lessThanOrEqual=" + SMALLER_DELTA_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST is less than DEFAULT_DELTA_SST
        defaultMaterialShouldNotBeFound("deltaSST.lessThan=" + DEFAULT_DELTA_SST);

        // Get all the materialList where deltaSST is less than UPDATED_DELTA_SST
        defaultMaterialShouldBeFound("deltaSST.lessThan=" + UPDATED_DELTA_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSSTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaSST is greater than DEFAULT_DELTA_SST
        defaultMaterialShouldNotBeFound("deltaSST.greaterThan=" + DEFAULT_DELTA_SST);

        // Get all the materialList where deltaSST is greater than SMALLER_DELTA_SST
        defaultMaterialShouldBeFound("deltaSST.greaterThan=" + SMALLER_DELTA_SST);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime equals to DEFAULT_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldBeFound("currentSAPSafeTime.equals=" + DEFAULT_CURRENT_SAP_SAFE_TIME);

        // Get all the materialList where currentSAPSafeTime equals to UPDATED_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.equals=" + UPDATED_CURRENT_SAP_SAFE_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime in DEFAULT_CURRENT_SAP_SAFE_TIME or UPDATED_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldBeFound("currentSAPSafeTime.in=" + DEFAULT_CURRENT_SAP_SAFE_TIME + "," + UPDATED_CURRENT_SAP_SAFE_TIME);

        // Get all the materialList where currentSAPSafeTime equals to UPDATED_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.in=" + UPDATED_CURRENT_SAP_SAFE_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime is not null
        defaultMaterialShouldBeFound("currentSAPSafeTime.specified=true");

        // Get all the materialList where currentSAPSafeTime is null
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime is greater than or equal to DEFAULT_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldBeFound("currentSAPSafeTime.greaterThanOrEqual=" + DEFAULT_CURRENT_SAP_SAFE_TIME);

        // Get all the materialList where currentSAPSafeTime is greater than or equal to UPDATED_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.greaterThanOrEqual=" + UPDATED_CURRENT_SAP_SAFE_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime is less than or equal to DEFAULT_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldBeFound("currentSAPSafeTime.lessThanOrEqual=" + DEFAULT_CURRENT_SAP_SAFE_TIME);

        // Get all the materialList where currentSAPSafeTime is less than or equal to SMALLER_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.lessThanOrEqual=" + SMALLER_CURRENT_SAP_SAFE_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime is less than DEFAULT_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.lessThan=" + DEFAULT_CURRENT_SAP_SAFE_TIME);

        // Get all the materialList where currentSAPSafeTime is less than UPDATED_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldBeFound("currentSAPSafeTime.lessThan=" + UPDATED_CURRENT_SAP_SAFE_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentSAPSafeTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentSAPSafeTime is greater than DEFAULT_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldNotBeFound("currentSAPSafeTime.greaterThan=" + DEFAULT_CURRENT_SAP_SAFE_TIME);

        // Get all the materialList where currentSAPSafeTime is greater than SMALLER_CURRENT_SAP_SAFE_TIME
        defaultMaterialShouldBeFound("currentSAPSafeTime.greaterThan=" + SMALLER_CURRENT_SAP_SAFE_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST equals to DEFAULT_PROPOSED_ST
        defaultMaterialShouldBeFound("proposedST.equals=" + DEFAULT_PROPOSED_ST);

        // Get all the materialList where proposedST equals to UPDATED_PROPOSED_ST
        defaultMaterialShouldNotBeFound("proposedST.equals=" + UPDATED_PROPOSED_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST in DEFAULT_PROPOSED_ST or UPDATED_PROPOSED_ST
        defaultMaterialShouldBeFound("proposedST.in=" + DEFAULT_PROPOSED_ST + "," + UPDATED_PROPOSED_ST);

        // Get all the materialList where proposedST equals to UPDATED_PROPOSED_ST
        defaultMaterialShouldNotBeFound("proposedST.in=" + UPDATED_PROPOSED_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST is not null
        defaultMaterialShouldBeFound("proposedST.specified=true");

        // Get all the materialList where proposedST is null
        defaultMaterialShouldNotBeFound("proposedST.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST is greater than or equal to DEFAULT_PROPOSED_ST
        defaultMaterialShouldBeFound("proposedST.greaterThanOrEqual=" + DEFAULT_PROPOSED_ST);

        // Get all the materialList where proposedST is greater than or equal to UPDATED_PROPOSED_ST
        defaultMaterialShouldNotBeFound("proposedST.greaterThanOrEqual=" + UPDATED_PROPOSED_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST is less than or equal to DEFAULT_PROPOSED_ST
        defaultMaterialShouldBeFound("proposedST.lessThanOrEqual=" + DEFAULT_PROPOSED_ST);

        // Get all the materialList where proposedST is less than or equal to SMALLER_PROPOSED_ST
        defaultMaterialShouldNotBeFound("proposedST.lessThanOrEqual=" + SMALLER_PROPOSED_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST is less than DEFAULT_PROPOSED_ST
        defaultMaterialShouldNotBeFound("proposedST.lessThan=" + DEFAULT_PROPOSED_ST);

        // Get all the materialList where proposedST is less than UPDATED_PROPOSED_ST
        defaultMaterialShouldBeFound("proposedST.lessThan=" + UPDATED_PROPOSED_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByProposedSTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where proposedST is greater than DEFAULT_PROPOSED_ST
        defaultMaterialShouldNotBeFound("proposedST.greaterThan=" + DEFAULT_PROPOSED_ST);

        // Get all the materialList where proposedST is greater than SMALLER_PROPOSED_ST
        defaultMaterialShouldBeFound("proposedST.greaterThan=" + SMALLER_PROPOSED_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST equals to DEFAULT_DELTA_ST
        defaultMaterialShouldBeFound("deltaST.equals=" + DEFAULT_DELTA_ST);

        // Get all the materialList where deltaST equals to UPDATED_DELTA_ST
        defaultMaterialShouldNotBeFound("deltaST.equals=" + UPDATED_DELTA_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST in DEFAULT_DELTA_ST or UPDATED_DELTA_ST
        defaultMaterialShouldBeFound("deltaST.in=" + DEFAULT_DELTA_ST + "," + UPDATED_DELTA_ST);

        // Get all the materialList where deltaST equals to UPDATED_DELTA_ST
        defaultMaterialShouldNotBeFound("deltaST.in=" + UPDATED_DELTA_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST is not null
        defaultMaterialShouldBeFound("deltaST.specified=true");

        // Get all the materialList where deltaST is null
        defaultMaterialShouldNotBeFound("deltaST.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST is greater than or equal to DEFAULT_DELTA_ST
        defaultMaterialShouldBeFound("deltaST.greaterThanOrEqual=" + DEFAULT_DELTA_ST);

        // Get all the materialList where deltaST is greater than or equal to UPDATED_DELTA_ST
        defaultMaterialShouldNotBeFound("deltaST.greaterThanOrEqual=" + UPDATED_DELTA_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST is less than or equal to DEFAULT_DELTA_ST
        defaultMaterialShouldBeFound("deltaST.lessThanOrEqual=" + DEFAULT_DELTA_ST);

        // Get all the materialList where deltaST is less than or equal to SMALLER_DELTA_ST
        defaultMaterialShouldNotBeFound("deltaST.lessThanOrEqual=" + SMALLER_DELTA_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST is less than DEFAULT_DELTA_ST
        defaultMaterialShouldNotBeFound("deltaST.lessThan=" + DEFAULT_DELTA_ST);

        // Get all the materialList where deltaST is less than UPDATED_DELTA_ST
        defaultMaterialShouldBeFound("deltaST.lessThan=" + UPDATED_DELTA_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByDeltaSTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where deltaST is greater than DEFAULT_DELTA_ST
        defaultMaterialShouldNotBeFound("deltaST.greaterThan=" + DEFAULT_DELTA_ST);

        // Get all the materialList where deltaST is greater than SMALLER_DELTA_ST
        defaultMaterialShouldBeFound("deltaST.greaterThan=" + SMALLER_DELTA_ST);
    }

    @Test
    @Transactional
    void getAllMaterialsByOpenSAPmd04IsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where openSAPmd04 equals to DEFAULT_OPEN_SA_PMD_04
        defaultMaterialShouldBeFound("openSAPmd04.equals=" + DEFAULT_OPEN_SA_PMD_04);

        // Get all the materialList where openSAPmd04 equals to UPDATED_OPEN_SA_PMD_04
        defaultMaterialShouldNotBeFound("openSAPmd04.equals=" + UPDATED_OPEN_SA_PMD_04);
    }

    @Test
    @Transactional
    void getAllMaterialsByOpenSAPmd04IsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where openSAPmd04 in DEFAULT_OPEN_SA_PMD_04 or UPDATED_OPEN_SA_PMD_04
        defaultMaterialShouldBeFound("openSAPmd04.in=" + DEFAULT_OPEN_SA_PMD_04 + "," + UPDATED_OPEN_SA_PMD_04);

        // Get all the materialList where openSAPmd04 equals to UPDATED_OPEN_SA_PMD_04
        defaultMaterialShouldNotBeFound("openSAPmd04.in=" + UPDATED_OPEN_SA_PMD_04);
    }

    @Test
    @Transactional
    void getAllMaterialsByOpenSAPmd04IsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where openSAPmd04 is not null
        defaultMaterialShouldBeFound("openSAPmd04.specified=true");

        // Get all the materialList where openSAPmd04 is null
        defaultMaterialShouldNotBeFound("openSAPmd04.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByOpenSAPmd04ContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where openSAPmd04 contains DEFAULT_OPEN_SA_PMD_04
        defaultMaterialShouldBeFound("openSAPmd04.contains=" + DEFAULT_OPEN_SA_PMD_04);

        // Get all the materialList where openSAPmd04 contains UPDATED_OPEN_SA_PMD_04
        defaultMaterialShouldNotBeFound("openSAPmd04.contains=" + UPDATED_OPEN_SA_PMD_04);
    }

    @Test
    @Transactional
    void getAllMaterialsByOpenSAPmd04NotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where openSAPmd04 does not contain DEFAULT_OPEN_SA_PMD_04
        defaultMaterialShouldNotBeFound("openSAPmd04.doesNotContain=" + DEFAULT_OPEN_SA_PMD_04);

        // Get all the materialList where openSAPmd04 does not contain UPDATED_OPEN_SA_PMD_04
        defaultMaterialShouldBeFound("openSAPmd04.doesNotContain=" + UPDATED_OPEN_SA_PMD_04);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue equals to DEFAULT_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldBeFound("currentInventoryValue.equals=" + DEFAULT_CURRENT_INVENTORY_VALUE);

        // Get all the materialList where currentInventoryValue equals to UPDATED_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldNotBeFound("currentInventoryValue.equals=" + UPDATED_CURRENT_INVENTORY_VALUE);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue in DEFAULT_CURRENT_INVENTORY_VALUE or UPDATED_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldBeFound("currentInventoryValue.in=" + DEFAULT_CURRENT_INVENTORY_VALUE + "," + UPDATED_CURRENT_INVENTORY_VALUE);

        // Get all the materialList where currentInventoryValue equals to UPDATED_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldNotBeFound("currentInventoryValue.in=" + UPDATED_CURRENT_INVENTORY_VALUE);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue is not null
        defaultMaterialShouldBeFound("currentInventoryValue.specified=true");

        // Get all the materialList where currentInventoryValue is null
        defaultMaterialShouldNotBeFound("currentInventoryValue.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue is greater than or equal to DEFAULT_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldBeFound("currentInventoryValue.greaterThanOrEqual=" + DEFAULT_CURRENT_INVENTORY_VALUE);

        // Get all the materialList where currentInventoryValue is greater than or equal to UPDATED_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldNotBeFound("currentInventoryValue.greaterThanOrEqual=" + UPDATED_CURRENT_INVENTORY_VALUE);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue is less than or equal to DEFAULT_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldBeFound("currentInventoryValue.lessThanOrEqual=" + DEFAULT_CURRENT_INVENTORY_VALUE);

        // Get all the materialList where currentInventoryValue is less than or equal to SMALLER_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldNotBeFound("currentInventoryValue.lessThanOrEqual=" + SMALLER_CURRENT_INVENTORY_VALUE);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue is less than DEFAULT_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldNotBeFound("currentInventoryValue.lessThan=" + DEFAULT_CURRENT_INVENTORY_VALUE);

        // Get all the materialList where currentInventoryValue is less than UPDATED_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldBeFound("currentInventoryValue.lessThan=" + UPDATED_CURRENT_INVENTORY_VALUE);
    }

    @Test
    @Transactional
    void getAllMaterialsByCurrentInventoryValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where currentInventoryValue is greater than DEFAULT_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldNotBeFound("currentInventoryValue.greaterThan=" + DEFAULT_CURRENT_INVENTORY_VALUE);

        // Get all the materialList where currentInventoryValue is greater than SMALLER_CURRENT_INVENTORY_VALUE
        defaultMaterialShouldBeFound("currentInventoryValue.greaterThan=" + SMALLER_CURRENT_INVENTORY_VALUE);
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost equals to DEFAULT_UNIT_COST
        defaultMaterialShouldBeFound("unitCost.equals=" + DEFAULT_UNIT_COST);

        // Get all the materialList where unitCost equals to UPDATED_UNIT_COST
        defaultMaterialShouldNotBeFound("unitCost.equals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost in DEFAULT_UNIT_COST or UPDATED_UNIT_COST
        defaultMaterialShouldBeFound("unitCost.in=" + DEFAULT_UNIT_COST + "," + UPDATED_UNIT_COST);

        // Get all the materialList where unitCost equals to UPDATED_UNIT_COST
        defaultMaterialShouldNotBeFound("unitCost.in=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost is not null
        defaultMaterialShouldBeFound("unitCost.specified=true");

        // Get all the materialList where unitCost is null
        defaultMaterialShouldNotBeFound("unitCost.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost is greater than or equal to DEFAULT_UNIT_COST
        defaultMaterialShouldBeFound("unitCost.greaterThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the materialList where unitCost is greater than or equal to UPDATED_UNIT_COST
        defaultMaterialShouldNotBeFound("unitCost.greaterThanOrEqual=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost is less than or equal to DEFAULT_UNIT_COST
        defaultMaterialShouldBeFound("unitCost.lessThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the materialList where unitCost is less than or equal to SMALLER_UNIT_COST
        defaultMaterialShouldNotBeFound("unitCost.lessThanOrEqual=" + SMALLER_UNIT_COST);
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost is less than DEFAULT_UNIT_COST
        defaultMaterialShouldNotBeFound("unitCost.lessThan=" + DEFAULT_UNIT_COST);

        // Get all the materialList where unitCost is less than UPDATED_UNIT_COST
        defaultMaterialShouldBeFound("unitCost.lessThan=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    void getAllMaterialsByUnitCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where unitCost is greater than DEFAULT_UNIT_COST
        defaultMaterialShouldNotBeFound("unitCost.greaterThan=" + DEFAULT_UNIT_COST);

        // Get all the materialList where unitCost is greater than SMALLER_UNIT_COST
        defaultMaterialShouldBeFound("unitCost.greaterThan=" + SMALLER_UNIT_COST);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand equals to DEFAULT_AVG_DEMAND
        defaultMaterialShouldBeFound("avgDemand.equals=" + DEFAULT_AVG_DEMAND);

        // Get all the materialList where avgDemand equals to UPDATED_AVG_DEMAND
        defaultMaterialShouldNotBeFound("avgDemand.equals=" + UPDATED_AVG_DEMAND);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand in DEFAULT_AVG_DEMAND or UPDATED_AVG_DEMAND
        defaultMaterialShouldBeFound("avgDemand.in=" + DEFAULT_AVG_DEMAND + "," + UPDATED_AVG_DEMAND);

        // Get all the materialList where avgDemand equals to UPDATED_AVG_DEMAND
        defaultMaterialShouldNotBeFound("avgDemand.in=" + UPDATED_AVG_DEMAND);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand is not null
        defaultMaterialShouldBeFound("avgDemand.specified=true");

        // Get all the materialList where avgDemand is null
        defaultMaterialShouldNotBeFound("avgDemand.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand is greater than or equal to DEFAULT_AVG_DEMAND
        defaultMaterialShouldBeFound("avgDemand.greaterThanOrEqual=" + DEFAULT_AVG_DEMAND);

        // Get all the materialList where avgDemand is greater than or equal to UPDATED_AVG_DEMAND
        defaultMaterialShouldNotBeFound("avgDemand.greaterThanOrEqual=" + UPDATED_AVG_DEMAND);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand is less than or equal to DEFAULT_AVG_DEMAND
        defaultMaterialShouldBeFound("avgDemand.lessThanOrEqual=" + DEFAULT_AVG_DEMAND);

        // Get all the materialList where avgDemand is less than or equal to SMALLER_AVG_DEMAND
        defaultMaterialShouldNotBeFound("avgDemand.lessThanOrEqual=" + SMALLER_AVG_DEMAND);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand is less than DEFAULT_AVG_DEMAND
        defaultMaterialShouldNotBeFound("avgDemand.lessThan=" + DEFAULT_AVG_DEMAND);

        // Get all the materialList where avgDemand is less than UPDATED_AVG_DEMAND
        defaultMaterialShouldBeFound("avgDemand.lessThan=" + UPDATED_AVG_DEMAND);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgDemandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgDemand is greater than DEFAULT_AVG_DEMAND
        defaultMaterialShouldNotBeFound("avgDemand.greaterThan=" + DEFAULT_AVG_DEMAND);

        // Get all the materialList where avgDemand is greater than SMALLER_AVG_DEMAND
        defaultMaterialShouldBeFound("avgDemand.greaterThan=" + SMALLER_AVG_DEMAND);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange equals to DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldBeFound("avgInventoryEffectAfterChange.equals=" + DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);

        // Get all the materialList where avgInventoryEffectAfterChange equals to UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.equals=" + UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange in DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE or UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldBeFound(
            "avgInventoryEffectAfterChange.in=" +
            DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE +
            "," +
            UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        );

        // Get all the materialList where avgInventoryEffectAfterChange equals to UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.in=" + UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange is not null
        defaultMaterialShouldBeFound("avgInventoryEffectAfterChange.specified=true");

        // Get all the materialList where avgInventoryEffectAfterChange is null
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange is greater than or equal to DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldBeFound("avgInventoryEffectAfterChange.greaterThanOrEqual=" + DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);

        // Get all the materialList where avgInventoryEffectAfterChange is greater than or equal to UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.greaterThanOrEqual=" + UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange is less than or equal to DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldBeFound("avgInventoryEffectAfterChange.lessThanOrEqual=" + DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);

        // Get all the materialList where avgInventoryEffectAfterChange is less than or equal to SMALLER_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.lessThanOrEqual=" + SMALLER_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange is less than DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.lessThan=" + DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);

        // Get all the materialList where avgInventoryEffectAfterChange is less than UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldBeFound("avgInventoryEffectAfterChange.lessThan=" + UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
    }

    @Test
    @Transactional
    void getAllMaterialsByAvgInventoryEffectAfterChangeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where avgInventoryEffectAfterChange is greater than DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldNotBeFound("avgInventoryEffectAfterChange.greaterThan=" + DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);

        // Get all the materialList where avgInventoryEffectAfterChange is greater than SMALLER_AVG_INVENTORY_EFFECT_AFTER_CHANGE
        defaultMaterialShouldBeFound("avgInventoryEffectAfterChange.greaterThan=" + SMALLER_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock equals to DEFAULT_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("newSAPSafetyStock.equals=" + DEFAULT_NEW_SAP_SAFETY_STOCK);

        // Get all the materialList where newSAPSafetyStock equals to UPDATED_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.equals=" + UPDATED_NEW_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock in DEFAULT_NEW_SAP_SAFETY_STOCK or UPDATED_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("newSAPSafetyStock.in=" + DEFAULT_NEW_SAP_SAFETY_STOCK + "," + UPDATED_NEW_SAP_SAFETY_STOCK);

        // Get all the materialList where newSAPSafetyStock equals to UPDATED_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.in=" + UPDATED_NEW_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock is not null
        defaultMaterialShouldBeFound("newSAPSafetyStock.specified=true");

        // Get all the materialList where newSAPSafetyStock is null
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock is greater than or equal to DEFAULT_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("newSAPSafetyStock.greaterThanOrEqual=" + DEFAULT_NEW_SAP_SAFETY_STOCK);

        // Get all the materialList where newSAPSafetyStock is greater than or equal to UPDATED_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.greaterThanOrEqual=" + UPDATED_NEW_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock is less than or equal to DEFAULT_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("newSAPSafetyStock.lessThanOrEqual=" + DEFAULT_NEW_SAP_SAFETY_STOCK);

        // Get all the materialList where newSAPSafetyStock is less than or equal to SMALLER_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.lessThanOrEqual=" + SMALLER_NEW_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock is less than DEFAULT_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.lessThan=" + DEFAULT_NEW_SAP_SAFETY_STOCK);

        // Get all the materialList where newSAPSafetyStock is less than UPDATED_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("newSAPSafetyStock.lessThan=" + UPDATED_NEW_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyStock is greater than DEFAULT_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldNotBeFound("newSAPSafetyStock.greaterThan=" + DEFAULT_NEW_SAP_SAFETY_STOCK);

        // Get all the materialList where newSAPSafetyStock is greater than SMALLER_NEW_SAP_SAFETY_STOCK
        defaultMaterialShouldBeFound("newSAPSafetyStock.greaterThan=" + SMALLER_NEW_SAP_SAFETY_STOCK);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime equals to DEFAULT_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldBeFound("newSAPSafetyTime.equals=" + DEFAULT_NEW_SAP_SAFETY_TIME);

        // Get all the materialList where newSAPSafetyTime equals to UPDATED_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.equals=" + UPDATED_NEW_SAP_SAFETY_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime in DEFAULT_NEW_SAP_SAFETY_TIME or UPDATED_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldBeFound("newSAPSafetyTime.in=" + DEFAULT_NEW_SAP_SAFETY_TIME + "," + UPDATED_NEW_SAP_SAFETY_TIME);

        // Get all the materialList where newSAPSafetyTime equals to UPDATED_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.in=" + UPDATED_NEW_SAP_SAFETY_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime is not null
        defaultMaterialShouldBeFound("newSAPSafetyTime.specified=true");

        // Get all the materialList where newSAPSafetyTime is null
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime is greater than or equal to DEFAULT_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldBeFound("newSAPSafetyTime.greaterThanOrEqual=" + DEFAULT_NEW_SAP_SAFETY_TIME);

        // Get all the materialList where newSAPSafetyTime is greater than or equal to UPDATED_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.greaterThanOrEqual=" + UPDATED_NEW_SAP_SAFETY_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime is less than or equal to DEFAULT_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldBeFound("newSAPSafetyTime.lessThanOrEqual=" + DEFAULT_NEW_SAP_SAFETY_TIME);

        // Get all the materialList where newSAPSafetyTime is less than or equal to SMALLER_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.lessThanOrEqual=" + SMALLER_NEW_SAP_SAFETY_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime is less than DEFAULT_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.lessThan=" + DEFAULT_NEW_SAP_SAFETY_TIME);

        // Get all the materialList where newSAPSafetyTime is less than UPDATED_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldBeFound("newSAPSafetyTime.lessThan=" + UPDATED_NEW_SAP_SAFETY_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByNewSAPSafetyTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where newSAPSafetyTime is greater than DEFAULT_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldNotBeFound("newSAPSafetyTime.greaterThan=" + DEFAULT_NEW_SAP_SAFETY_TIME);

        // Get all the materialList where newSAPSafetyTime is greater than SMALLER_NEW_SAP_SAFETY_TIME
        defaultMaterialShouldBeFound("newSAPSafetyTime.greaterThan=" + SMALLER_NEW_SAP_SAFETY_TIME);
    }

    @Test
    @Transactional
    void getAllMaterialsByFlagMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where flagMaterial equals to DEFAULT_FLAG_MATERIAL
        defaultMaterialShouldBeFound("flagMaterial.equals=" + DEFAULT_FLAG_MATERIAL);

        // Get all the materialList where flagMaterial equals to UPDATED_FLAG_MATERIAL
        defaultMaterialShouldNotBeFound("flagMaterial.equals=" + UPDATED_FLAG_MATERIAL);
    }

    @Test
    @Transactional
    void getAllMaterialsByFlagMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where flagMaterial in DEFAULT_FLAG_MATERIAL or UPDATED_FLAG_MATERIAL
        defaultMaterialShouldBeFound("flagMaterial.in=" + DEFAULT_FLAG_MATERIAL + "," + UPDATED_FLAG_MATERIAL);

        // Get all the materialList where flagMaterial equals to UPDATED_FLAG_MATERIAL
        defaultMaterialShouldNotBeFound("flagMaterial.in=" + UPDATED_FLAG_MATERIAL);
    }

    @Test
    @Transactional
    void getAllMaterialsByFlagMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where flagMaterial is not null
        defaultMaterialShouldBeFound("flagMaterial.specified=true");

        // Get all the materialList where flagMaterial is null
        defaultMaterialShouldNotBeFound("flagMaterial.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where comment equals to DEFAULT_COMMENT
        defaultMaterialShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the materialList where comment equals to UPDATED_COMMENT
        defaultMaterialShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllMaterialsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultMaterialShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the materialList where comment equals to UPDATED_COMMENT
        defaultMaterialShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllMaterialsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where comment is not null
        defaultMaterialShouldBeFound("comment.specified=true");

        // Get all the materialList where comment is null
        defaultMaterialShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByCommentContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where comment contains DEFAULT_COMMENT
        defaultMaterialShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the materialList where comment contains UPDATED_COMMENT
        defaultMaterialShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllMaterialsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where comment does not contain DEFAULT_COMMENT
        defaultMaterialShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the materialList where comment does not contain UPDATED_COMMENT
        defaultMaterialShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialShouldBeFound(String filter) throws Exception {
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].abcClassification").value(hasItem(DEFAULT_ABC_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].avgSupplierDelay").value(hasItem(DEFAULT_AVG_SUPPLIER_DELAY.doubleValue())))
            .andExpect(jsonPath("$.[*].maxSupplierDelay").value(hasItem(DEFAULT_MAX_SUPPLIER_DELAY.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceLevel").value(hasItem(DEFAULT_SERVICE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].currSAPSafetyStock").value(hasItem(DEFAULT_CURR_SAP_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].proposedSST").value(hasItem(DEFAULT_PROPOSED_SST)))
            .andExpect(jsonPath("$.[*].deltaSST").value(hasItem(DEFAULT_DELTA_SST)))
            .andExpect(jsonPath("$.[*].currentSAPSafeTime").value(hasItem(DEFAULT_CURRENT_SAP_SAFE_TIME)))
            .andExpect(jsonPath("$.[*].proposedST").value(hasItem(DEFAULT_PROPOSED_ST)))
            .andExpect(jsonPath("$.[*].deltaST").value(hasItem(DEFAULT_DELTA_ST)))
            .andExpect(jsonPath("$.[*].openSAPmd04").value(hasItem(DEFAULT_OPEN_SA_PMD_04)))
            .andExpect(jsonPath("$.[*].currentInventoryValue").value(hasItem(DEFAULT_CURRENT_INVENTORY_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].avgDemand").value(hasItem(DEFAULT_AVG_DEMAND)))
            .andExpect(
                jsonPath("$.[*].avgInventoryEffectAfterChange").value(hasItem(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].newSAPSafetyStock").value(hasItem(DEFAULT_NEW_SAP_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].newSAPSafetyTime").value(hasItem(DEFAULT_NEW_SAP_SAFETY_TIME)))
            .andExpect(jsonPath("$.[*].flagMaterial").value(hasItem(DEFAULT_FLAG_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

        // Check, that the count call also returns 1
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialShouldNotBeFound(String filter) throws Exception {
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaterial() throws Exception {
        // Get the material
        restMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material
        Material updatedMaterial = materialRepository.findById(material.getId()).get();
        // Disconnect from session so that the updates on updatedMaterial are not directly saved in db
        em.detach(updatedMaterial);
        updatedMaterial
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .currSAPSafetyStock(UPDATED_CURR_SAP_SAFETY_STOCK)
            .proposedSST(UPDATED_PROPOSED_SST)
            .deltaSST(UPDATED_DELTA_SST)
            .currentSAPSafeTime(UPDATED_CURRENT_SAP_SAFE_TIME)
            .proposedST(UPDATED_PROPOSED_ST)
            .deltaST(UPDATED_DELTA_ST)
            .openSAPmd04(UPDATED_OPEN_SA_PMD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .unitCost(UPDATED_UNIT_COST)
            .avgDemand(UPDATED_AVG_DEMAND)
            .avgInventoryEffectAfterChange(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSAPSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .newSAPSafetyTime(UPDATED_NEW_SAP_SAFETY_TIME)
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .comment(UPDATED_COMMENT);

        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMaterial.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testMaterial.getAvgSupplierDelay()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY);
        assertThat(testMaterial.getMaxSupplierDelay()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY);
        assertThat(testMaterial.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testMaterial.getCurrSAPSafetyStock()).isEqualTo(UPDATED_CURR_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getProposedSST()).isEqualTo(UPDATED_PROPOSED_SST);
        assertThat(testMaterial.getDeltaSST()).isEqualTo(UPDATED_DELTA_SST);
        assertThat(testMaterial.getCurrentSAPSafeTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFE_TIME);
        assertThat(testMaterial.getProposedST()).isEqualTo(UPDATED_PROPOSED_ST);
        assertThat(testMaterial.getDeltaST()).isEqualTo(UPDATED_DELTA_ST);
        assertThat(testMaterial.getOpenSAPmd04()).isEqualTo(UPDATED_OPEN_SA_PMD_04);
        assertThat(testMaterial.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testMaterial.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
        assertThat(testMaterial.getAvgDemand()).isEqualTo(UPDATED_AVG_DEMAND);
        assertThat(testMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testMaterial.getNewSAPSafetyStock()).isEqualTo(UPDATED_NEW_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getNewSAPSafetyTime()).isEqualTo(UPDATED_NEW_SAP_SAFETY_TIME);
        assertThat(testMaterial.getFlagMaterial()).isEqualTo(UPDATED_FLAG_MATERIAL);
        assertThat(testMaterial.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, material.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .currSAPSafetyStock(UPDATED_CURR_SAP_SAFETY_STOCK)
            .proposedSST(UPDATED_PROPOSED_SST)
            .deltaSST(UPDATED_DELTA_SST)
            .proposedST(UPDATED_PROPOSED_ST)
            .deltaST(UPDATED_DELTA_ST)
            .openSAPmd04(UPDATED_OPEN_SA_PMD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .avgDemand(UPDATED_AVG_DEMAND)
            .newSAPSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .comment(UPDATED_COMMENT);

        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMaterial.getAbcClassification()).isEqualTo(DEFAULT_ABC_CLASSIFICATION);
        assertThat(testMaterial.getAvgSupplierDelay()).isEqualTo(DEFAULT_AVG_SUPPLIER_DELAY);
        assertThat(testMaterial.getMaxSupplierDelay()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY);
        assertThat(testMaterial.getServiceLevel()).isEqualTo(DEFAULT_SERVICE_LEVEL);
        assertThat(testMaterial.getCurrSAPSafetyStock()).isEqualTo(UPDATED_CURR_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getProposedSST()).isEqualTo(UPDATED_PROPOSED_SST);
        assertThat(testMaterial.getDeltaSST()).isEqualTo(UPDATED_DELTA_SST);
        assertThat(testMaterial.getCurrentSAPSafeTime()).isEqualTo(DEFAULT_CURRENT_SAP_SAFE_TIME);
        assertThat(testMaterial.getProposedST()).isEqualTo(UPDATED_PROPOSED_ST);
        assertThat(testMaterial.getDeltaST()).isEqualTo(UPDATED_DELTA_ST);
        assertThat(testMaterial.getOpenSAPmd04()).isEqualTo(UPDATED_OPEN_SA_PMD_04);
        assertThat(testMaterial.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testMaterial.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
        assertThat(testMaterial.getAvgDemand()).isEqualTo(UPDATED_AVG_DEMAND);
        assertThat(testMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testMaterial.getNewSAPSafetyStock()).isEqualTo(UPDATED_NEW_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getNewSAPSafetyTime()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_TIME);
        assertThat(testMaterial.getFlagMaterial()).isEqualTo(DEFAULT_FLAG_MATERIAL);
        assertThat(testMaterial.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .currSAPSafetyStock(UPDATED_CURR_SAP_SAFETY_STOCK)
            .proposedSST(UPDATED_PROPOSED_SST)
            .deltaSST(UPDATED_DELTA_SST)
            .currentSAPSafeTime(UPDATED_CURRENT_SAP_SAFE_TIME)
            .proposedST(UPDATED_PROPOSED_ST)
            .deltaST(UPDATED_DELTA_ST)
            .openSAPmd04(UPDATED_OPEN_SA_PMD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .unitCost(UPDATED_UNIT_COST)
            .avgDemand(UPDATED_AVG_DEMAND)
            .avgInventoryEffectAfterChange(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSAPSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .newSAPSafetyTime(UPDATED_NEW_SAP_SAFETY_TIME)
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .comment(UPDATED_COMMENT);

        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMaterial.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testMaterial.getAvgSupplierDelay()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY);
        assertThat(testMaterial.getMaxSupplierDelay()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY);
        assertThat(testMaterial.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testMaterial.getCurrSAPSafetyStock()).isEqualTo(UPDATED_CURR_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getProposedSST()).isEqualTo(UPDATED_PROPOSED_SST);
        assertThat(testMaterial.getDeltaSST()).isEqualTo(UPDATED_DELTA_SST);
        assertThat(testMaterial.getCurrentSAPSafeTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFE_TIME);
        assertThat(testMaterial.getProposedST()).isEqualTo(UPDATED_PROPOSED_ST);
        assertThat(testMaterial.getDeltaST()).isEqualTo(UPDATED_DELTA_ST);
        assertThat(testMaterial.getOpenSAPmd04()).isEqualTo(UPDATED_OPEN_SA_PMD_04);
        assertThat(testMaterial.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testMaterial.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
        assertThat(testMaterial.getAvgDemand()).isEqualTo(UPDATED_AVG_DEMAND);
        assertThat(testMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testMaterial.getNewSAPSafetyStock()).isEqualTo(UPDATED_NEW_SAP_SAFETY_STOCK);
        assertThat(testMaterial.getNewSAPSafetyTime()).isEqualTo(UPDATED_NEW_SAP_SAFETY_TIME);
        assertThat(testMaterial.getFlagMaterial()).isEqualTo(UPDATED_FLAG_MATERIAL);
        assertThat(testMaterial.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, material.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeDelete = materialRepository.findAll().size();

        // Delete the material
        restMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, material.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
