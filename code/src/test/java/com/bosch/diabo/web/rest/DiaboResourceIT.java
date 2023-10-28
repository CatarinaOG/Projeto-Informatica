package com.bosch.diabo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bosch.diabo.IntegrationTest;
import com.bosch.diabo.domain.Diabo;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.repository.DiaboRepository;
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
 * Integration tests for the {@link DiaboResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiaboResourceIT {

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_DESCRIPTION = "BBBBBBBBBB";

    private static final ABCClassification DEFAULT_ABC_CLASSIFICATION = ABCClassification.A;
    private static final ABCClassification UPDATED_ABC_CLASSIFICATION = ABCClassification.B;

    private static final Integer DEFAULT_AVG_SUPPLIER_DELAY_LAST_90_DAYS = 1;
    private static final Integer UPDATED_AVG_SUPPLIER_DELAY_LAST_90_DAYS = 2;

    private static final Integer DEFAULT_MAX_SUPPLIER_DELAY_LAST_90_DAYS = 1;
    private static final Integer UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS = 2;

    private static final Float DEFAULT_SERVICE_LEVEL = 1F;
    private static final Float UPDATED_SERVICE_LEVEL = 2F;

    private static final Integer DEFAULT_CURRENT_SAP_SAFETY_STOCK = 1;
    private static final Integer UPDATED_CURRENT_SAP_SAFETY_STOCK = 2;

    private static final Integer DEFAULT_PROPOSED_SAFETY_STOCK = 1;
    private static final Integer UPDATED_PROPOSED_SAFETY_STOCK = 2;

    private static final Integer DEFAULT_DELTA_SAFETY_STOCK = 1;
    private static final Integer UPDATED_DELTA_SAFETY_STOCK = 2;

    private static final Integer DEFAULT_CURRENT_SAP_SAFETY_TIME = 1;
    private static final Integer UPDATED_CURRENT_SAP_SAFETY_TIME = 2;

    private static final Integer DEFAULT_PROPOSED_SAFETY_TIME = 1;
    private static final Integer UPDATED_PROPOSED_SAFETY_TIME = 2;

    private static final Integer DEFAULT_DELTA_SAFETY_TIME = 1;
    private static final Integer UPDATED_DELTA_SAFETY_TIME = 2;

    private static final String DEFAULT_OPEN_SAP_MD_04 = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_SAP_MD_04 = "BBBBBBBBBB";

    private static final Float DEFAULT_CURRENT_INVENTORY_VALUE = 1F;
    private static final Float UPDATED_CURRENT_INVENTORY_VALUE = 2F;

    private static final Float DEFAULT_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE = 1F;
    private static final Float UPDATED_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE = 2F;

    private static final Integer DEFAULT_NEW_SAP_SAFETY_STOCK = 1;
    private static final Integer UPDATED_NEW_SAP_SAFETY_STOCK = 2;

    private static final Integer DEFAULT_NEW_SAP_SAFETY_TIME = 1;
    private static final Integer UPDATED_NEW_SAP_SAFETY_TIME = 2;

    private static final Boolean DEFAULT_SELECT_ENTRIES_FOR_CHANGE_IN_SAP = false;
    private static final Boolean UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP = true;

    private static final Boolean DEFAULT_FLAG_MATERIAL_AS_SPECIAL_CASE = false;
    private static final Boolean UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE = true;

    private static final String DEFAULT_COMMENTARY = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTARY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/diabos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiaboRepository diaboRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaboMockMvc;

    private Diabo diabo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diabo createEntity(EntityManager em) {
        Diabo diabo = new Diabo()
            .material(DEFAULT_MATERIAL)
            .materialDescription(DEFAULT_MATERIAL_DESCRIPTION)
            .abcClassification(DEFAULT_ABC_CLASSIFICATION)
            .avgSupplierDelayLast90Days(DEFAULT_AVG_SUPPLIER_DELAY_LAST_90_DAYS)
            .maxSupplierDelayLast90Days(DEFAULT_MAX_SUPPLIER_DELAY_LAST_90_DAYS)
            .serviceLevel(DEFAULT_SERVICE_LEVEL)
            .currentSapSafetyStock(DEFAULT_CURRENT_SAP_SAFETY_STOCK)
            .proposedSafetyStock(DEFAULT_PROPOSED_SAFETY_STOCK)
            .deltaSafetyStock(DEFAULT_DELTA_SAFETY_STOCK)
            .currentSapSafetyTime(DEFAULT_CURRENT_SAP_SAFETY_TIME)
            .proposedSafetyTime(DEFAULT_PROPOSED_SAFETY_TIME)
            .deltaSafetyTime(DEFAULT_DELTA_SAFETY_TIME)
            .openSapMd04(DEFAULT_OPEN_SAP_MD_04)
            .currentInventoryValue(DEFAULT_CURRENT_INVENTORY_VALUE)
            .averageInventoryEffectAfterChange(DEFAULT_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSapSafetyStock(DEFAULT_NEW_SAP_SAFETY_STOCK)
            .newSapSafetyTime(DEFAULT_NEW_SAP_SAFETY_TIME)
            .selectEntriesForChangeInSap(DEFAULT_SELECT_ENTRIES_FOR_CHANGE_IN_SAP)
            .flagMaterialAsSpecialCase(DEFAULT_FLAG_MATERIAL_AS_SPECIAL_CASE)
            .commentary(DEFAULT_COMMENTARY);
        return diabo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diabo createUpdatedEntity(EntityManager em) {
        Diabo diabo = new Diabo()
            .material(UPDATED_MATERIAL)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelayLast90Days(UPDATED_AVG_SUPPLIER_DELAY_LAST_90_DAYS)
            .maxSupplierDelayLast90Days(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .currentSapSafetyStock(UPDATED_CURRENT_SAP_SAFETY_STOCK)
            .proposedSafetyStock(UPDATED_PROPOSED_SAFETY_STOCK)
            .deltaSafetyStock(UPDATED_DELTA_SAFETY_STOCK)
            .currentSapSafetyTime(UPDATED_CURRENT_SAP_SAFETY_TIME)
            .proposedSafetyTime(UPDATED_PROPOSED_SAFETY_TIME)
            .deltaSafetyTime(UPDATED_DELTA_SAFETY_TIME)
            .openSapMd04(UPDATED_OPEN_SAP_MD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .averageInventoryEffectAfterChange(UPDATED_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSapSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .newSapSafetyTime(UPDATED_NEW_SAP_SAFETY_TIME)
            .selectEntriesForChangeInSap(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP)
            .flagMaterialAsSpecialCase(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE)
            .commentary(UPDATED_COMMENTARY);
        return diabo;
    }

    @BeforeEach
    public void initTest() {
        diabo = createEntity(em);
    }

    @Test
    @Transactional
    void createDiabo() throws Exception {
        int databaseSizeBeforeCreate = diaboRepository.findAll().size();
        // Create the Diabo
        restDiaboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diabo)))
            .andExpect(status().isCreated());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeCreate + 1);
        Diabo testDiabo = diaboList.get(diaboList.size() - 1);
        assertThat(testDiabo.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testDiabo.getMaterialDescription()).isEqualTo(DEFAULT_MATERIAL_DESCRIPTION);
        assertThat(testDiabo.getAbcClassification()).isEqualTo(DEFAULT_ABC_CLASSIFICATION);
        assertThat(testDiabo.getAvgSupplierDelayLast90Days()).isEqualTo(DEFAULT_AVG_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getMaxSupplierDelayLast90Days()).isEqualTo(DEFAULT_MAX_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getServiceLevel()).isEqualTo(DEFAULT_SERVICE_LEVEL);
        assertThat(testDiabo.getCurrentSapSafetyStock()).isEqualTo(DEFAULT_CURRENT_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getProposedSafetyStock()).isEqualTo(DEFAULT_PROPOSED_SAFETY_STOCK);
        assertThat(testDiabo.getDeltaSafetyStock()).isEqualTo(DEFAULT_DELTA_SAFETY_STOCK);
        assertThat(testDiabo.getCurrentSapSafetyTime()).isEqualTo(DEFAULT_CURRENT_SAP_SAFETY_TIME);
        assertThat(testDiabo.getProposedSafetyTime()).isEqualTo(DEFAULT_PROPOSED_SAFETY_TIME);
        assertThat(testDiabo.getDeltaSafetyTime()).isEqualTo(DEFAULT_DELTA_SAFETY_TIME);
        assertThat(testDiabo.getOpenSapMd04()).isEqualTo(DEFAULT_OPEN_SAP_MD_04);
        assertThat(testDiabo.getCurrentInventoryValue()).isEqualTo(DEFAULT_CURRENT_INVENTORY_VALUE);
        assertThat(testDiabo.getAverageInventoryEffectAfterChange()).isEqualTo(DEFAULT_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testDiabo.getNewSapSafetyStock()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getNewSapSafetyTime()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_TIME);
        assertThat(testDiabo.getSelectEntriesForChangeInSap()).isEqualTo(DEFAULT_SELECT_ENTRIES_FOR_CHANGE_IN_SAP);
        assertThat(testDiabo.getFlagMaterialAsSpecialCase()).isEqualTo(DEFAULT_FLAG_MATERIAL_AS_SPECIAL_CASE);
        assertThat(testDiabo.getCommentary()).isEqualTo(DEFAULT_COMMENTARY);
    }

    @Test
    @Transactional
    void createDiaboWithExistingId() throws Exception {
        // Create the Diabo with an existing ID
        diabo.setId(1L);

        int databaseSizeBeforeCreate = diaboRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diabo)))
            .andExpect(status().isBadRequest());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiabos() throws Exception {
        // Initialize the database
        diaboRepository.saveAndFlush(diabo);

        // Get all the diaboList
        restDiaboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diabo.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].abcClassification").value(hasItem(DEFAULT_ABC_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].avgSupplierDelayLast90Days").value(hasItem(DEFAULT_AVG_SUPPLIER_DELAY_LAST_90_DAYS)))
            .andExpect(jsonPath("$.[*].maxSupplierDelayLast90Days").value(hasItem(DEFAULT_MAX_SUPPLIER_DELAY_LAST_90_DAYS)))
            .andExpect(jsonPath("$.[*].serviceLevel").value(hasItem(DEFAULT_SERVICE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].currentSapSafetyStock").value(hasItem(DEFAULT_CURRENT_SAP_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].proposedSafetyStock").value(hasItem(DEFAULT_PROPOSED_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].deltaSafetyStock").value(hasItem(DEFAULT_DELTA_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].currentSapSafetyTime").value(hasItem(DEFAULT_CURRENT_SAP_SAFETY_TIME)))
            .andExpect(jsonPath("$.[*].proposedSafetyTime").value(hasItem(DEFAULT_PROPOSED_SAFETY_TIME)))
            .andExpect(jsonPath("$.[*].deltaSafetyTime").value(hasItem(DEFAULT_DELTA_SAFETY_TIME)))
            .andExpect(jsonPath("$.[*].openSapMd04").value(hasItem(DEFAULT_OPEN_SAP_MD_04)))
            .andExpect(jsonPath("$.[*].currentInventoryValue").value(hasItem(DEFAULT_CURRENT_INVENTORY_VALUE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].averageInventoryEffectAfterChange")
                    .value(hasItem(DEFAULT_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].newSapSafetyStock").value(hasItem(DEFAULT_NEW_SAP_SAFETY_STOCK)))
            .andExpect(jsonPath("$.[*].newSapSafetyTime").value(hasItem(DEFAULT_NEW_SAP_SAFETY_TIME)))
            .andExpect(
                jsonPath("$.[*].selectEntriesForChangeInSap").value(hasItem(DEFAULT_SELECT_ENTRIES_FOR_CHANGE_IN_SAP.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].flagMaterialAsSpecialCase").value(hasItem(DEFAULT_FLAG_MATERIAL_AS_SPECIAL_CASE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentary").value(hasItem(DEFAULT_COMMENTARY)));
    }

    @Test
    @Transactional
    void getDiabo() throws Exception {
        // Initialize the database
        diaboRepository.saveAndFlush(diabo);

        // Get the diabo
        restDiaboMockMvc
            .perform(get(ENTITY_API_URL_ID, diabo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diabo.getId().intValue()))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.materialDescription").value(DEFAULT_MATERIAL_DESCRIPTION))
            .andExpect(jsonPath("$.abcClassification").value(DEFAULT_ABC_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.avgSupplierDelayLast90Days").value(DEFAULT_AVG_SUPPLIER_DELAY_LAST_90_DAYS))
            .andExpect(jsonPath("$.maxSupplierDelayLast90Days").value(DEFAULT_MAX_SUPPLIER_DELAY_LAST_90_DAYS))
            .andExpect(jsonPath("$.serviceLevel").value(DEFAULT_SERVICE_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.currentSapSafetyStock").value(DEFAULT_CURRENT_SAP_SAFETY_STOCK))
            .andExpect(jsonPath("$.proposedSafetyStock").value(DEFAULT_PROPOSED_SAFETY_STOCK))
            .andExpect(jsonPath("$.deltaSafetyStock").value(DEFAULT_DELTA_SAFETY_STOCK))
            .andExpect(jsonPath("$.currentSapSafetyTime").value(DEFAULT_CURRENT_SAP_SAFETY_TIME))
            .andExpect(jsonPath("$.proposedSafetyTime").value(DEFAULT_PROPOSED_SAFETY_TIME))
            .andExpect(jsonPath("$.deltaSafetyTime").value(DEFAULT_DELTA_SAFETY_TIME))
            .andExpect(jsonPath("$.openSapMd04").value(DEFAULT_OPEN_SAP_MD_04))
            .andExpect(jsonPath("$.currentInventoryValue").value(DEFAULT_CURRENT_INVENTORY_VALUE.doubleValue()))
            .andExpect(jsonPath("$.averageInventoryEffectAfterChange").value(DEFAULT_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE.doubleValue()))
            .andExpect(jsonPath("$.newSapSafetyStock").value(DEFAULT_NEW_SAP_SAFETY_STOCK))
            .andExpect(jsonPath("$.newSapSafetyTime").value(DEFAULT_NEW_SAP_SAFETY_TIME))
            .andExpect(jsonPath("$.selectEntriesForChangeInSap").value(DEFAULT_SELECT_ENTRIES_FOR_CHANGE_IN_SAP.booleanValue()))
            .andExpect(jsonPath("$.flagMaterialAsSpecialCase").value(DEFAULT_FLAG_MATERIAL_AS_SPECIAL_CASE.booleanValue()))
            .andExpect(jsonPath("$.commentary").value(DEFAULT_COMMENTARY));
    }

    @Test
    @Transactional
    void getNonExistingDiabo() throws Exception {
        // Get the diabo
        restDiaboMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiabo() throws Exception {
        // Initialize the database
        diaboRepository.saveAndFlush(diabo);

        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();

        // Update the diabo
        Diabo updatedDiabo = diaboRepository.findById(diabo.getId()).get();
        // Disconnect from session so that the updates on updatedDiabo are not directly saved in db
        em.detach(updatedDiabo);
        updatedDiabo
            .material(UPDATED_MATERIAL)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelayLast90Days(UPDATED_AVG_SUPPLIER_DELAY_LAST_90_DAYS)
            .maxSupplierDelayLast90Days(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .currentSapSafetyStock(UPDATED_CURRENT_SAP_SAFETY_STOCK)
            .proposedSafetyStock(UPDATED_PROPOSED_SAFETY_STOCK)
            .deltaSafetyStock(UPDATED_DELTA_SAFETY_STOCK)
            .currentSapSafetyTime(UPDATED_CURRENT_SAP_SAFETY_TIME)
            .proposedSafetyTime(UPDATED_PROPOSED_SAFETY_TIME)
            .deltaSafetyTime(UPDATED_DELTA_SAFETY_TIME)
            .openSapMd04(UPDATED_OPEN_SAP_MD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .averageInventoryEffectAfterChange(UPDATED_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSapSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .newSapSafetyTime(UPDATED_NEW_SAP_SAFETY_TIME)
            .selectEntriesForChangeInSap(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP)
            .flagMaterialAsSpecialCase(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE)
            .commentary(UPDATED_COMMENTARY);

        restDiaboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiabo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiabo))
            )
            .andExpect(status().isOk());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
        Diabo testDiabo = diaboList.get(diaboList.size() - 1);
        assertThat(testDiabo.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testDiabo.getMaterialDescription()).isEqualTo(UPDATED_MATERIAL_DESCRIPTION);
        assertThat(testDiabo.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testDiabo.getAvgSupplierDelayLast90Days()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getMaxSupplierDelayLast90Days()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testDiabo.getCurrentSapSafetyStock()).isEqualTo(UPDATED_CURRENT_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getProposedSafetyStock()).isEqualTo(UPDATED_PROPOSED_SAFETY_STOCK);
        assertThat(testDiabo.getDeltaSafetyStock()).isEqualTo(UPDATED_DELTA_SAFETY_STOCK);
        assertThat(testDiabo.getCurrentSapSafetyTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFETY_TIME);
        assertThat(testDiabo.getProposedSafetyTime()).isEqualTo(UPDATED_PROPOSED_SAFETY_TIME);
        assertThat(testDiabo.getDeltaSafetyTime()).isEqualTo(UPDATED_DELTA_SAFETY_TIME);
        assertThat(testDiabo.getOpenSapMd04()).isEqualTo(UPDATED_OPEN_SAP_MD_04);
        assertThat(testDiabo.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testDiabo.getAverageInventoryEffectAfterChange()).isEqualTo(UPDATED_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testDiabo.getNewSapSafetyStock()).isEqualTo(UPDATED_NEW_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getNewSapSafetyTime()).isEqualTo(UPDATED_NEW_SAP_SAFETY_TIME);
        assertThat(testDiabo.getSelectEntriesForChangeInSap()).isEqualTo(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP);
        assertThat(testDiabo.getFlagMaterialAsSpecialCase()).isEqualTo(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE);
        assertThat(testDiabo.getCommentary()).isEqualTo(UPDATED_COMMENTARY);
    }

    @Test
    @Transactional
    void putNonExistingDiabo() throws Exception {
        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();
        diabo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diabo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diabo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiabo() throws Exception {
        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();
        diabo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diabo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiabo() throws Exception {
        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();
        diabo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaboMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diabo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiaboWithPatch() throws Exception {
        // Initialize the database
        diaboRepository.saveAndFlush(diabo);

        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();

        // Update the diabo using partial update
        Diabo partialUpdatedDiabo = new Diabo();
        partialUpdatedDiabo.setId(diabo.getId());

        partialUpdatedDiabo
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .maxSupplierDelayLast90Days(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .proposedSafetyStock(UPDATED_PROPOSED_SAFETY_STOCK)
            .deltaSafetyStock(UPDATED_DELTA_SAFETY_STOCK)
            .currentSapSafetyTime(UPDATED_CURRENT_SAP_SAFETY_TIME)
            .openSapMd04(UPDATED_OPEN_SAP_MD_04)
            .selectEntriesForChangeInSap(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP)
            .flagMaterialAsSpecialCase(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE);

        restDiaboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiabo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiabo))
            )
            .andExpect(status().isOk());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
        Diabo testDiabo = diaboList.get(diaboList.size() - 1);
        assertThat(testDiabo.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testDiabo.getMaterialDescription()).isEqualTo(UPDATED_MATERIAL_DESCRIPTION);
        assertThat(testDiabo.getAbcClassification()).isEqualTo(DEFAULT_ABC_CLASSIFICATION);
        assertThat(testDiabo.getAvgSupplierDelayLast90Days()).isEqualTo(DEFAULT_AVG_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getMaxSupplierDelayLast90Days()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testDiabo.getCurrentSapSafetyStock()).isEqualTo(DEFAULT_CURRENT_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getProposedSafetyStock()).isEqualTo(UPDATED_PROPOSED_SAFETY_STOCK);
        assertThat(testDiabo.getDeltaSafetyStock()).isEqualTo(UPDATED_DELTA_SAFETY_STOCK);
        assertThat(testDiabo.getCurrentSapSafetyTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFETY_TIME);
        assertThat(testDiabo.getProposedSafetyTime()).isEqualTo(DEFAULT_PROPOSED_SAFETY_TIME);
        assertThat(testDiabo.getDeltaSafetyTime()).isEqualTo(DEFAULT_DELTA_SAFETY_TIME);
        assertThat(testDiabo.getOpenSapMd04()).isEqualTo(UPDATED_OPEN_SAP_MD_04);
        assertThat(testDiabo.getCurrentInventoryValue()).isEqualTo(DEFAULT_CURRENT_INVENTORY_VALUE);
        assertThat(testDiabo.getAverageInventoryEffectAfterChange()).isEqualTo(DEFAULT_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testDiabo.getNewSapSafetyStock()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getNewSapSafetyTime()).isEqualTo(DEFAULT_NEW_SAP_SAFETY_TIME);
        assertThat(testDiabo.getSelectEntriesForChangeInSap()).isEqualTo(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP);
        assertThat(testDiabo.getFlagMaterialAsSpecialCase()).isEqualTo(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE);
        assertThat(testDiabo.getCommentary()).isEqualTo(DEFAULT_COMMENTARY);
    }

    @Test
    @Transactional
    void fullUpdateDiaboWithPatch() throws Exception {
        // Initialize the database
        diaboRepository.saveAndFlush(diabo);

        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();

        // Update the diabo using partial update
        Diabo partialUpdatedDiabo = new Diabo();
        partialUpdatedDiabo.setId(diabo.getId());

        partialUpdatedDiabo
            .material(UPDATED_MATERIAL)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .abcClassification(UPDATED_ABC_CLASSIFICATION)
            .avgSupplierDelayLast90Days(UPDATED_AVG_SUPPLIER_DELAY_LAST_90_DAYS)
            .maxSupplierDelayLast90Days(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS)
            .serviceLevel(UPDATED_SERVICE_LEVEL)
            .currentSapSafetyStock(UPDATED_CURRENT_SAP_SAFETY_STOCK)
            .proposedSafetyStock(UPDATED_PROPOSED_SAFETY_STOCK)
            .deltaSafetyStock(UPDATED_DELTA_SAFETY_STOCK)
            .currentSapSafetyTime(UPDATED_CURRENT_SAP_SAFETY_TIME)
            .proposedSafetyTime(UPDATED_PROPOSED_SAFETY_TIME)
            .deltaSafetyTime(UPDATED_DELTA_SAFETY_TIME)
            .openSapMd04(UPDATED_OPEN_SAP_MD_04)
            .currentInventoryValue(UPDATED_CURRENT_INVENTORY_VALUE)
            .averageInventoryEffectAfterChange(UPDATED_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE)
            .newSapSafetyStock(UPDATED_NEW_SAP_SAFETY_STOCK)
            .newSapSafetyTime(UPDATED_NEW_SAP_SAFETY_TIME)
            .selectEntriesForChangeInSap(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP)
            .flagMaterialAsSpecialCase(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE)
            .commentary(UPDATED_COMMENTARY);

        restDiaboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiabo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiabo))
            )
            .andExpect(status().isOk());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
        Diabo testDiabo = diaboList.get(diaboList.size() - 1);
        assertThat(testDiabo.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testDiabo.getMaterialDescription()).isEqualTo(UPDATED_MATERIAL_DESCRIPTION);
        assertThat(testDiabo.getAbcClassification()).isEqualTo(UPDATED_ABC_CLASSIFICATION);
        assertThat(testDiabo.getAvgSupplierDelayLast90Days()).isEqualTo(UPDATED_AVG_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getMaxSupplierDelayLast90Days()).isEqualTo(UPDATED_MAX_SUPPLIER_DELAY_LAST_90_DAYS);
        assertThat(testDiabo.getServiceLevel()).isEqualTo(UPDATED_SERVICE_LEVEL);
        assertThat(testDiabo.getCurrentSapSafetyStock()).isEqualTo(UPDATED_CURRENT_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getProposedSafetyStock()).isEqualTo(UPDATED_PROPOSED_SAFETY_STOCK);
        assertThat(testDiabo.getDeltaSafetyStock()).isEqualTo(UPDATED_DELTA_SAFETY_STOCK);
        assertThat(testDiabo.getCurrentSapSafetyTime()).isEqualTo(UPDATED_CURRENT_SAP_SAFETY_TIME);
        assertThat(testDiabo.getProposedSafetyTime()).isEqualTo(UPDATED_PROPOSED_SAFETY_TIME);
        assertThat(testDiabo.getDeltaSafetyTime()).isEqualTo(UPDATED_DELTA_SAFETY_TIME);
        assertThat(testDiabo.getOpenSapMd04()).isEqualTo(UPDATED_OPEN_SAP_MD_04);
        assertThat(testDiabo.getCurrentInventoryValue()).isEqualTo(UPDATED_CURRENT_INVENTORY_VALUE);
        assertThat(testDiabo.getAverageInventoryEffectAfterChange()).isEqualTo(UPDATED_AVERAGE_INVENTORY_EFFECT_AFTER_CHANGE);
        assertThat(testDiabo.getNewSapSafetyStock()).isEqualTo(UPDATED_NEW_SAP_SAFETY_STOCK);
        assertThat(testDiabo.getNewSapSafetyTime()).isEqualTo(UPDATED_NEW_SAP_SAFETY_TIME);
        assertThat(testDiabo.getSelectEntriesForChangeInSap()).isEqualTo(UPDATED_SELECT_ENTRIES_FOR_CHANGE_IN_SAP);
        assertThat(testDiabo.getFlagMaterialAsSpecialCase()).isEqualTo(UPDATED_FLAG_MATERIAL_AS_SPECIAL_CASE);
        assertThat(testDiabo.getCommentary()).isEqualTo(UPDATED_COMMENTARY);
    }

    @Test
    @Transactional
    void patchNonExistingDiabo() throws Exception {
        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();
        diabo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diabo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diabo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiabo() throws Exception {
        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();
        diabo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diabo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiabo() throws Exception {
        int databaseSizeBeforeUpdate = diaboRepository.findAll().size();
        diabo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaboMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diabo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diabo in the database
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiabo() throws Exception {
        // Initialize the database
        diaboRepository.saveAndFlush(diabo);

        int databaseSizeBeforeDelete = diaboRepository.findAll().size();

        // Delete the diabo
        restDiaboMockMvc
            .perform(delete(ENTITY_API_URL_ID, diabo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diabo> diaboList = diaboRepository.findAll();
        assertThat(diaboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
