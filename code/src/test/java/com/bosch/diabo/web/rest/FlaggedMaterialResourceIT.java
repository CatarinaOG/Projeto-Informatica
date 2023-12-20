package com.bosch.diabo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bosch.diabo.IntegrationTest;
import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.domain.enumeration.Coin;
import com.bosch.diabo.repository.FlaggedMaterialRepository;
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
 * Integration tests for the {@link FlaggedMaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlaggedMaterialResourceIT {

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

    private static final String DEFAULT_PLANT = "AAAAAAAAAA";
    private static final String UPDATED_PLANT = "BBBBBBBBBB";

    private static final String DEFAULT_MRP_CONTROLLER = "AAAAAAAAAA";
    private static final String UPDATED_MRP_CONTROLLER = "BBBBBBBBBB";

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

    private static final Boolean DEFAULT_FLAG_MATERIAL = false;
    private static final Boolean UPDATED_FLAG_MATERIAL = true;

    private static final LocalDate DEFAULT_FLAG_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FLAG_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VALUE_OF_UPDATED_SS = 1;
    private static final Integer UPDATED_VALUE_OF_UPDATED_SS = 2;

    private static final Integer DEFAULT_VALUE_OF_UPDATED_ST = 1;
    private static final Integer UPDATED_VALUE_OF_UPDATED_ST = 2;

    private static final LocalDate DEFAULT_DATE_OF_UPDATED_SS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_UPDATED_SS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OF_UPDATED_ST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_UPDATED_ST = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_TO_SAVE_UPDATES = false;
    private static final Boolean UPDATED_TO_SAVE_UPDATES = true;

    private static final Coin DEFAULT_CURRENCY = Coin.EUR;
    private static final Coin UPDATED_CURRENCY = Coin.USD;

    private static final String ENTITY_API_URL = "/api/flagged-materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FlaggedMaterialRepository flaggedMaterialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlaggedMaterialMockMvc;

    private FlaggedMaterial flaggedMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlaggedMaterial createEntity(EntityManager em) {
        FlaggedMaterial flaggedMaterial = new FlaggedMaterial()
            .material(DEFAULT_MATERIAL)
            .description(DEFAULT_DESCRIPTION)
            .abcClassification(DEFAULT_ABC_CLASSIFICATION)
            .avgSupplierDelay(DEFAULT_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(DEFAULT_MAX_SUPPLIER_DELAY)
            .serviceLevel(DEFAULT_SERVICE_LEVEL)
            .plant(DEFAULT_PLANT)
            .mrpController(DEFAULT_MRP_CONTROLLER)
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
            .flagMaterial(DEFAULT_FLAG_MATERIAL)
            .flagExpirationDate(DEFAULT_FLAG_EXPIRATION_DATE)
            .valueOfUpdatedSS(DEFAULT_VALUE_OF_UPDATED_SS)
            .valueOfUpdatedST(DEFAULT_VALUE_OF_UPDATED_ST)
            .dateOfUpdatedSS(DEFAULT_DATE_OF_UPDATED_SS)
            .dateOfUpdatedST(DEFAULT_DATE_OF_UPDATED_ST)
            .toSaveUpdates(DEFAULT_TO_SAVE_UPDATES)
            .currency(DEFAULT_CURRENCY);
        return flaggedMaterial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlaggedMaterial createUpdatedEntity(EntityManager em) {
        FlaggedMaterial flaggedMaterial = new FlaggedMaterial()
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
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
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE)
            .valueOfUpdatedSS(UPDATED_VALUE_OF_UPDATED_SS)
            .valueOfUpdatedST(UPDATED_VALUE_OF_UPDATED_ST)
            .dateOfUpdatedSS(UPDATED_DATE_OF_UPDATED_SS)
            .dateOfUpdatedST(UPDATED_DATE_OF_UPDATED_ST)
            .toSaveUpdates(UPDATED_TO_SAVE_UPDATES)
            .currency(UPDATED_CURRENCY);
        return flaggedMaterial;
    }

    @BeforeEach
    public void initTest() {
        flaggedMaterial = createEntity(em);
    }

    @Test
    @Transactional
    void createFlaggedMaterial() throws Exception {
        int databaseSizeBeforeCreate = flaggedMaterialRepository.findAll().size();
        // Create the FlaggedMaterial
        restFlaggedMaterialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isCreated());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        FlaggedMaterial testFlaggedMaterial = flaggedMaterialList.get(flaggedMaterialList.size() - 1);
        assertThat(testFlaggedMaterial.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testFlaggedMaterial.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFlaggedMaterial.getAbcClassification()).isEqualTo(DEFAULT_ABC_CLASSIFICATION);
        assertThat(testFlaggedMaterial.getAvgSupplierDelay()).isEqualTo(DEFAULT_AVG_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getMaxSupplierDelay()).isEqualTo(DEFAULT_MAX_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getServiceLevel()).isEqualTo(DEFAULT_SERVICE_LEVEL);
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(DEFAULT_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getCurrSAPSafetyStock()).isEqualTo(DEFAULT_CURR_SAP_SAFETY_STOCK);
        assertThat(testFlaggedMaterial.getProposedSST()).isEqualTo(DEFAULT_PROPOSED_SST);
        assertThat(testFlaggedMaterial.getDeltaSST()).isEqualTo(DEFAULT_DELTA_SST);
        assertThat(testFlaggedMaterial.getCurrentSAPSafeTime()).isEqualTo(DEFAULT_CURRENT_SAP_SAFE_TIME);
        assertThat(testFlaggedMaterial.getProposedST()).isEqualTo(DEFAULT_PROPOSED_ST);
        assertThat(testFlaggedMaterial.getDeltaST()).isEqualTo(DEFAULT_DELTA_ST);
        assertThat(testFlaggedMaterial.getOpenSAPmd04()).isEqualTo(DEFAULT_OPEN_SA_PMD_04);
        assertThat(testFlaggedMaterial.getCurrentInventoryValue()).isEqualTo(DEFAULT_CURRENT_INVENTORY_VALUE);
        assertThat(testFlaggedMaterial.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
        assertThat(testFlaggedMaterial.getAvgDemand()).isEqualTo(DEFAULT_AVG_DEMAND);
        assertThat(testFlaggedMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(DEFAULT_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(DEFAULT_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(DEFAULT_FLAG_EXPIRATION_DATE);
        assertThat(testFlaggedMaterial.getValueOfUpdatedSS()).isEqualTo(DEFAULT_VALUE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getValueOfUpdatedST()).isEqualTo(DEFAULT_VALUE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getDateOfUpdatedSS()).isEqualTo(DEFAULT_DATE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getDateOfUpdatedST()).isEqualTo(DEFAULT_DATE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getToSaveUpdates()).isEqualTo(DEFAULT_TO_SAVE_UPDATES);
        assertThat(testFlaggedMaterial.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createFlaggedMaterialWithExistingId() throws Exception {
        // Create the FlaggedMaterial with an existing ID
        flaggedMaterial.setId(1L);

        int databaseSizeBeforeCreate = flaggedMaterialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlaggedMaterialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterials() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList
        restFlaggedMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flaggedMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].abcClassification").value(hasItem(DEFAULT_ABC_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].avgSupplierDelay").value(hasItem(DEFAULT_AVG_SUPPLIER_DELAY.doubleValue())))
            .andExpect(jsonPath("$.[*].maxSupplierDelay").value(hasItem(DEFAULT_MAX_SUPPLIER_DELAY.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceLevel").value(hasItem(DEFAULT_SERVICE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].plant").value(hasItem(DEFAULT_PLANT)))
            .andExpect(jsonPath("$.[*].mrpController").value(hasItem(DEFAULT_MRP_CONTROLLER)))
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
            .andExpect(jsonPath("$.[*].flagMaterial").value(hasItem(DEFAULT_FLAG_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].flagExpirationDate").value(hasItem(DEFAULT_FLAG_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].valueOfUpdatedSS").value(hasItem(DEFAULT_VALUE_OF_UPDATED_SS)))
            .andExpect(jsonPath("$.[*].valueOfUpdatedST").value(hasItem(DEFAULT_VALUE_OF_UPDATED_ST)))
            .andExpect(jsonPath("$.[*].dateOfUpdatedSS").value(hasItem(DEFAULT_DATE_OF_UPDATED_SS.toString())))
            .andExpect(jsonPath("$.[*].dateOfUpdatedST").value(hasItem(DEFAULT_DATE_OF_UPDATED_ST.toString())))
            .andExpect(jsonPath("$.[*].toSaveUpdates").value(hasItem(DEFAULT_TO_SAVE_UPDATES.booleanValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }

    @Test
    @Transactional
    void getFlaggedMaterial() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get the flaggedMaterial
        restFlaggedMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, flaggedMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flaggedMaterial.getId().intValue()))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.abcClassification").value(DEFAULT_ABC_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.avgSupplierDelay").value(DEFAULT_AVG_SUPPLIER_DELAY.doubleValue()))
            .andExpect(jsonPath("$.maxSupplierDelay").value(DEFAULT_MAX_SUPPLIER_DELAY.doubleValue()))
            .andExpect(jsonPath("$.serviceLevel").value(DEFAULT_SERVICE_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.plant").value(DEFAULT_PLANT))
            .andExpect(jsonPath("$.mrpController").value(DEFAULT_MRP_CONTROLLER))
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
            .andExpect(jsonPath("$.flagMaterial").value(DEFAULT_FLAG_MATERIAL.booleanValue()))
            .andExpect(jsonPath("$.flagExpirationDate").value(DEFAULT_FLAG_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.valueOfUpdatedSS").value(DEFAULT_VALUE_OF_UPDATED_SS))
            .andExpect(jsonPath("$.valueOfUpdatedST").value(DEFAULT_VALUE_OF_UPDATED_ST))
            .andExpect(jsonPath("$.dateOfUpdatedSS").value(DEFAULT_DATE_OF_UPDATED_SS.toString()))
            .andExpect(jsonPath("$.dateOfUpdatedST").value(DEFAULT_DATE_OF_UPDATED_ST.toString()))
            .andExpect(jsonPath("$.toSaveUpdates").value(DEFAULT_TO_SAVE_UPDATES.booleanValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFlaggedMaterial() throws Exception {
        // Get the flaggedMaterial
        restFlaggedMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFlaggedMaterial() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();

        // Update the flaggedMaterial
        FlaggedMaterial updatedFlaggedMaterial = flaggedMaterialRepository.findById(flaggedMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedFlaggedMaterial are not directly saved in db
        em.detach(updatedFlaggedMaterial);
        updatedFlaggedMaterial
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
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
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE)
            .valueOfUpdatedSS(UPDATED_VALUE_OF_UPDATED_SS)
            .valueOfUpdatedST(UPDATED_VALUE_OF_UPDATED_ST)
            .dateOfUpdatedSS(UPDATED_DATE_OF_UPDATED_SS)
            .dateOfUpdatedST(UPDATED_DATE_OF_UPDATED_ST)
            .toSaveUpdates(UPDATED_TO_SAVE_UPDATES)
            .currency(UPDATED_CURRENCY);

        restFlaggedMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFlaggedMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFlaggedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
        FlaggedMaterial testFlaggedMaterial = flaggedMaterialList.get(flaggedMaterialList.size() - 1);
        assertThat(testFlaggedMaterial.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testFlaggedMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFlaggedMaterial.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testFlaggedMaterial.getAvgSupplierDelay()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getMaxSupplierDelay()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getCurrSAPSafetyStock()).isEqualTo(UPDATED_CURR_SAP_SAFETY_STOCK);
        assertThat(testFlaggedMaterial.getProposedSST()).isEqualTo(UPDATED_PROPOSED_SST);
        assertThat(testFlaggedMaterial.getDeltaSST()).isEqualTo(UPDATED_DELTA_SST);
        assertThat(testFlaggedMaterial.getCurrentSAPSafeTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFE_TIME);
        assertThat(testFlaggedMaterial.getProposedST()).isEqualTo(UPDATED_PROPOSED_ST);
        assertThat(testFlaggedMaterial.getDeltaST()).isEqualTo(UPDATED_DELTA_ST);
        assertThat(testFlaggedMaterial.getOpenSAPmd04()).isEqualTo(UPDATED_OPEN_SA_PMD_04);
        assertThat(testFlaggedMaterial.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testFlaggedMaterial.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
        assertThat(testFlaggedMaterial.getAvgDemand()).isEqualTo(UPDATED_AVG_DEMAND);
        assertThat(testFlaggedMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(UPDATED_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(UPDATED_FLAG_EXPIRATION_DATE);
        assertThat(testFlaggedMaterial.getValueOfUpdatedSS()).isEqualTo(UPDATED_VALUE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getValueOfUpdatedST()).isEqualTo(UPDATED_VALUE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getDateOfUpdatedSS()).isEqualTo(UPDATED_DATE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getDateOfUpdatedST()).isEqualTo(UPDATED_DATE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getToSaveUpdates()).isEqualTo(UPDATED_TO_SAVE_UPDATES);
        assertThat(testFlaggedMaterial.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingFlaggedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();
        flaggedMaterial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlaggedMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flaggedMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlaggedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();
        flaggedMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlaggedMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlaggedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();
        flaggedMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlaggedMaterialMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlaggedMaterialWithPatch() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();

        // Update the flaggedMaterial using partial update
        FlaggedMaterial partialUpdatedFlaggedMaterial = new FlaggedMaterial();
        partialUpdatedFlaggedMaterial.setId(flaggedMaterial.getId());

        partialUpdatedFlaggedMaterial
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .currSAPSafetyStock(UPDATED_CURR_SAP_SAFETY_STOCK)
            .avgInventoryEffectAfterChange(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE)
            .dateOfUpdatedST(UPDATED_DATE_OF_UPDATED_ST)
            .toSaveUpdates(UPDATED_TO_SAVE_UPDATES);

        restFlaggedMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlaggedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlaggedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
        FlaggedMaterial testFlaggedMaterial = flaggedMaterialList.get(flaggedMaterialList.size() - 1);
        assertThat(testFlaggedMaterial.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testFlaggedMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFlaggedMaterial.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testFlaggedMaterial.getAvgSupplierDelay()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getMaxSupplierDelay()).isEqualTo(DEFAULT_MAX_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getServiceLevel()).isEqualTo(DEFAULT_SERVICE_LEVEL);
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getCurrSAPSafetyStock()).isEqualTo(UPDATED_CURR_SAP_SAFETY_STOCK);
        assertThat(testFlaggedMaterial.getProposedSST()).isEqualTo(DEFAULT_PROPOSED_SST);
        assertThat(testFlaggedMaterial.getDeltaSST()).isEqualTo(DEFAULT_DELTA_SST);
        assertThat(testFlaggedMaterial.getCurrentSAPSafeTime()).isEqualTo(DEFAULT_CURRENT_SAP_SAFE_TIME);
        assertThat(testFlaggedMaterial.getProposedST()).isEqualTo(DEFAULT_PROPOSED_ST);
        assertThat(testFlaggedMaterial.getDeltaST()).isEqualTo(DEFAULT_DELTA_ST);
        assertThat(testFlaggedMaterial.getOpenSAPmd04()).isEqualTo(DEFAULT_OPEN_SA_PMD_04);
        assertThat(testFlaggedMaterial.getCurrentInventoryValue()).isEqualTo(DEFAULT_CURRENT_INVENTORY_VALUE);
        assertThat(testFlaggedMaterial.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
        assertThat(testFlaggedMaterial.getAvgDemand()).isEqualTo(DEFAULT_AVG_DEMAND);
        assertThat(testFlaggedMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(DEFAULT_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(UPDATED_FLAG_EXPIRATION_DATE);
        assertThat(testFlaggedMaterial.getValueOfUpdatedSS()).isEqualTo(DEFAULT_VALUE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getValueOfUpdatedST()).isEqualTo(DEFAULT_VALUE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getDateOfUpdatedSS()).isEqualTo(DEFAULT_DATE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getDateOfUpdatedST()).isEqualTo(UPDATED_DATE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getToSaveUpdates()).isEqualTo(UPDATED_TO_SAVE_UPDATES);
        assertThat(testFlaggedMaterial.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateFlaggedMaterialWithPatch() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();

        // Update the flaggedMaterial using partial update
        FlaggedMaterial partialUpdatedFlaggedMaterial = new FlaggedMaterial();
        partialUpdatedFlaggedMaterial.setId(flaggedMaterial.getId());

        partialUpdatedFlaggedMaterial
            .material(UPDATED_MATERIAL)
            .description(UPDATED_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelay(UPDATED_AVG_SUPPLIER_DELAY)
            .maxSupplierDelay(UPDATED_MAX_SUPPLIER_DELAY)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
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
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE)
            .valueOfUpdatedSS(UPDATED_VALUE_OF_UPDATED_SS)
            .valueOfUpdatedST(UPDATED_VALUE_OF_UPDATED_ST)
            .dateOfUpdatedSS(UPDATED_DATE_OF_UPDATED_SS)
            .dateOfUpdatedST(UPDATED_DATE_OF_UPDATED_ST)
            .toSaveUpdates(UPDATED_TO_SAVE_UPDATES)
            .currency(UPDATED_CURRENCY);

        restFlaggedMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlaggedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlaggedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
        FlaggedMaterial testFlaggedMaterial = flaggedMaterialList.get(flaggedMaterialList.size() - 1);
        assertThat(testFlaggedMaterial.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testFlaggedMaterial.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFlaggedMaterial.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testFlaggedMaterial.getAvgSupplierDelay()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getMaxSupplierDelay()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY);
        assertThat(testFlaggedMaterial.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getCurrSAPSafetyStock()).isEqualTo(UPDATED_CURR_SAP_SAFETY_STOCK);
        assertThat(testFlaggedMaterial.getProposedSST()).isEqualTo(UPDATED_PROPOSED_SST);
        assertThat(testFlaggedMaterial.getDeltaSST()).isEqualTo(UPDATED_DELTA_SST);
        assertThat(testFlaggedMaterial.getCurrentSAPSafeTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFE_TIME);
        assertThat(testFlaggedMaterial.getProposedST()).isEqualTo(UPDATED_PROPOSED_ST);
        assertThat(testFlaggedMaterial.getDeltaST()).isEqualTo(UPDATED_DELTA_ST);
        assertThat(testFlaggedMaterial.getOpenSAPmd04()).isEqualTo(UPDATED_OPEN_SA_PMD_04);
        assertThat(testFlaggedMaterial.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testFlaggedMaterial.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
        assertThat(testFlaggedMaterial.getAvgDemand()).isEqualTo(UPDATED_AVG_DEMAND);
        assertThat(testFlaggedMaterial.getAvgInventoryEffectAfterChange()).isEqualTo(UPDATED_AVG_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(UPDATED_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(UPDATED_FLAG_EXPIRATION_DATE);
        assertThat(testFlaggedMaterial.getValueOfUpdatedSS()).isEqualTo(UPDATED_VALUE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getValueOfUpdatedST()).isEqualTo(UPDATED_VALUE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getDateOfUpdatedSS()).isEqualTo(UPDATED_DATE_OF_UPDATED_SS);
        assertThat(testFlaggedMaterial.getDateOfUpdatedST()).isEqualTo(UPDATED_DATE_OF_UPDATED_ST);
        assertThat(testFlaggedMaterial.getToSaveUpdates()).isEqualTo(UPDATED_TO_SAVE_UPDATES);
        assertThat(testFlaggedMaterial.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingFlaggedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();
        flaggedMaterial.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlaggedMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flaggedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlaggedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();
        flaggedMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlaggedMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlaggedMaterial() throws Exception {
        int databaseSizeBeforeUpdate = flaggedMaterialRepository.findAll().size();
        flaggedMaterial.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlaggedMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flaggedMaterial))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlaggedMaterial in the database
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlaggedMaterial() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        int databaseSizeBeforeDelete = flaggedMaterialRepository.findAll().size();

        // Delete the flaggedMaterial
        restFlaggedMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, flaggedMaterial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FlaggedMaterial> flaggedMaterialList = flaggedMaterialRepository.findAll();
        assertThat(flaggedMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
