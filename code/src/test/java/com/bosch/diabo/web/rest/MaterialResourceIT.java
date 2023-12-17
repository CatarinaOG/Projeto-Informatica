package com.bosch.diabo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bosch.diabo.IntegrationTest;
import com.bosch.diabo.domain.Material;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.domain.enumeration.Coin;
import com.bosch.diabo.repository.MaterialRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final Float DEFAULT_MAX_SUPPLIER_DELAY = 1F;
    private static final Float UPDATED_MAX_SUPPLIER_DELAY = 2F;

    private static final Float DEFAULT_SERVICE_LEVEL = 1F;
    private static final Float UPDATED_SERVICE_LEVEL = 2F;

    private static final Integer DEFAULT_CURR_SAP_SAFETY_STOCK = 1;
    private static final Integer UPDATED_CURR_SAP_SAFETY_STOCK = 2;

    private static final Integer DEFAULT_PROPOSED_SST = 1;
    private static final Integer UPDATED_PROPOSED_SST = 2;

    private static final Integer DEFAULT_DELTA_SST = 1;
    private static final Integer UPDATED_DELTA_SST = 2;

    private static final Integer DEFAULT_CURRENT_SAP_SAFE_TIME = 1;
    private static final Integer UPDATED_CURRENT_SAP_SAFE_TIME = 2;

    private static final Integer DEFAULT_PROPOSED_ST = 1;
    private static final Integer UPDATED_PROPOSED_ST = 2;

    private static final Integer DEFAULT_DELTA_ST = 1;
    private static final Integer UPDATED_DELTA_ST = 2;

    private static final String DEFAULT_OPEN_SA_PMD_04 = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_SA_PMD_04 = "BBBBBBBBBB";

    private static final Float DEFAULT_CURRENT_INVENTORY_VALUE = 1F;
    private static final Float UPDATED_CURRENT_INVENTORY_VALUE = 2F;

    private static final Float DEFAULT_UNIT_COST = 1F;
    private static final Float UPDATED_UNIT_COST = 2F;

    private static final Integer DEFAULT_AVG_DEMAND = 1;
    private static final Integer UPDATED_AVG_DEMAND = 2;

    private static final Float DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE = 1F;
    private static final Float UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE = 2F;

    private static final Integer DEFAULT_NEW_SAP_SAFETY_STOCK = 1;
    private static final Integer UPDATED_NEW_SAP_SAFETY_STOCK = 2;

    private static final Integer DEFAULT_NEW_SAP_SAFETY_TIME = 1;
    private static final Integer UPDATED_NEW_SAP_SAFETY_TIME = 2;

    private static final Boolean DEFAULT_FLAG_MATERIAL = false;
    private static final Boolean UPDATED_FLAG_MATERIAL = true;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FLAG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FLAG_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLANT = "AAAAAAAAAA";
    private static final String UPDATED_PLANT = "BBBBBBBBBB";

    private static final String DEFAULT_MRP_CONTROLLER = "AAAAAAAAAA";
    private static final String UPDATED_MRP_CONTROLLER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_EDITED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EDITED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_TO_SAVE_LAST_EDITED = false;
    private static final Boolean UPDATED_TO_SAVE_LAST_EDITED = true;

    private static final Coin DEFAULT_CURRENCY_TYPE = Coin.EUR;
    private static final Coin UPDATED_CURRENCY_TYPE = Coin.USD;

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
            .comment(DEFAULT_COMMENT)
            .flagDate(DEFAULT_FLAG_DATE)
            .plant(DEFAULT_PLANT)
            .mrpController(DEFAULT_MRP_CONTROLLER)
            .lastEdited(DEFAULT_LAST_EDITED)
            .toSaveLastEdited(DEFAULT_TO_SAVE_LAST_EDITED)
            .currencyType(DEFAULT_CURRENCY_TYPE);
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
            .comment(UPDATED_COMMENT)
            .flagDate(UPDATED_FLAG_DATE)
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .lastEdited(UPDATED_LAST_EDITED)
            .toSaveLastEdited(UPDATED_TO_SAVE_LAST_EDITED)
            .currencyType(UPDATED_CURRENCY_TYPE);
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
        assertThat(testMaterial.getFlagDate()).isEqualTo(DEFAULT_FLAG_DATE);
        assertThat(testMaterial.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testMaterial.getMrpController()).isEqualTo(DEFAULT_MRP_CONTROLLER);
        assertThat(testMaterial.getLastEdited()).isEqualTo(DEFAULT_LAST_EDITED);
        assertThat(testMaterial.getToSaveLastEdited()).isEqualTo(DEFAULT_TO_SAVE_LAST_EDITED);
        assertThat(testMaterial.getCurrencyType()).isEqualTo(DEFAULT_CURRENCY_TYPE);
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
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].flagDate").value(hasItem(DEFAULT_FLAG_DATE.toString())))
            .andExpect(jsonPath("$.[*].plant").value(hasItem(DEFAULT_PLANT)))
            .andExpect(jsonPath("$.[*].mrpController").value(hasItem(DEFAULT_MRP_CONTROLLER)))
            .andExpect(jsonPath("$.[*].lastEdited").value(hasItem(DEFAULT_LAST_EDITED.toString())))
            .andExpect(jsonPath("$.[*].toSaveLastEdited").value(hasItem(DEFAULT_TO_SAVE_LAST_EDITED.booleanValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())));
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
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.flagDate").value(DEFAULT_FLAG_DATE.toString()))
            .andExpect(jsonPath("$.plant").value(DEFAULT_PLANT))
            .andExpect(jsonPath("$.mrpController").value(DEFAULT_MRP_CONTROLLER))
            .andExpect(jsonPath("$.lastEdited").value(DEFAULT_LAST_EDITED.toString()))
            .andExpect(jsonPath("$.toSaveLastEdited").value(DEFAULT_TO_SAVE_LAST_EDITED.booleanValue()))
            .andExpect(jsonPath("$.currencyType").value(DEFAULT_CURRENCY_TYPE.toString()));
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
            .comment(UPDATED_COMMENT)
            .flagDate(UPDATED_FLAG_DATE)
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .lastEdited(UPDATED_LAST_EDITED)
            .toSaveLastEdited(UPDATED_TO_SAVE_LAST_EDITED)
            .currencyType(UPDATED_CURRENCY_TYPE);

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
        assertThat(testMaterial.getFlagDate()).isEqualTo(UPDATED_FLAG_DATE);
        assertThat(testMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testMaterial.getLastEdited()).isEqualTo(UPDATED_LAST_EDITED);
        assertThat(testMaterial.getToSaveLastEdited()).isEqualTo(UPDATED_TO_SAVE_LAST_EDITED);
        assertThat(testMaterial.getCurrencyType()).isEqualTo(UPDATED_CURRENCY_TYPE);
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
            .comment(UPDATED_COMMENT)
            .flagDate(UPDATED_FLAG_DATE)
            .currencyType(UPDATED_CURRENCY_TYPE);

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
        assertThat(testMaterial.getFlagDate()).isEqualTo(UPDATED_FLAG_DATE);
        assertThat(testMaterial.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testMaterial.getMrpController()).isEqualTo(DEFAULT_MRP_CONTROLLER);
        assertThat(testMaterial.getLastEdited()).isEqualTo(DEFAULT_LAST_EDITED);
        assertThat(testMaterial.getToSaveLastEdited()).isEqualTo(DEFAULT_TO_SAVE_LAST_EDITED);
        assertThat(testMaterial.getCurrencyType()).isEqualTo(UPDATED_CURRENCY_TYPE);
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
            .comment(UPDATED_COMMENT)
            .flagDate(UPDATED_FLAG_DATE)
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .lastEdited(UPDATED_LAST_EDITED)
            .toSaveLastEdited(UPDATED_TO_SAVE_LAST_EDITED)
            .currencyType(UPDATED_CURRENCY_TYPE);

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
        assertThat(testMaterial.getFlagDate()).isEqualTo(UPDATED_FLAG_DATE);
        assertThat(testMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testMaterial.getLastEdited()).isEqualTo(UPDATED_LAST_EDITED);
        assertThat(testMaterial.getToSaveLastEdited()).isEqualTo(UPDATED_TO_SAVE_LAST_EDITED);
        assertThat(testMaterial.getCurrencyType()).isEqualTo(UPDATED_CURRENCY_TYPE);
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
