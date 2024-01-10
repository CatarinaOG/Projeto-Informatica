package com.bosch.diabo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bosch.diabo.IntegrationTest;
import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.enumeration.ABCClassification;
import com.bosch.diabo.repository.FlaggedMaterialRepository;
import com.bosch.diabo.service.criteria.FlaggedMaterialCriteria;
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

    private static final Integer DEFAULT_PLANT = 1;
    private static final Integer UPDATED_PLANT = 2;
    private static final Integer SMALLER_PLANT = 1 - 1;

    private static final String DEFAULT_MRP_CONTROLLER = "AAAAAAAAAA";
    private static final String UPDATED_MRP_CONTROLLER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FLAG_MATERIAL = false;
    private static final Boolean UPDATED_FLAG_MATERIAL = true;

    private static final LocalDate DEFAULT_FLAG_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FLAG_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FLAG_EXPIRATION_DATE = LocalDate.ofEpochDay(-1L);

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
            .plant(DEFAULT_PLANT)
            .mrpController(DEFAULT_MRP_CONTROLLER)
            .flagMaterial(DEFAULT_FLAG_MATERIAL)
            .flagExpirationDate(DEFAULT_FLAG_EXPIRATION_DATE);
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
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE);
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
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(DEFAULT_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(DEFAULT_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(DEFAULT_FLAG_EXPIRATION_DATE);
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
            .andExpect(jsonPath("$.[*].plant").value(hasItem(DEFAULT_PLANT)))
            .andExpect(jsonPath("$.[*].mrpController").value(hasItem(DEFAULT_MRP_CONTROLLER)))
            .andExpect(jsonPath("$.[*].flagMaterial").value(hasItem(DEFAULT_FLAG_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].flagExpirationDate").value(hasItem(DEFAULT_FLAG_EXPIRATION_DATE.toString())));
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
            .andExpect(jsonPath("$.plant").value(DEFAULT_PLANT))
            .andExpect(jsonPath("$.mrpController").value(DEFAULT_MRP_CONTROLLER))
            .andExpect(jsonPath("$.flagMaterial").value(DEFAULT_FLAG_MATERIAL.booleanValue()))
            .andExpect(jsonPath("$.flagExpirationDate").value(DEFAULT_FLAG_EXPIRATION_DATE.toString()));
    }

    @Test
    @Transactional
    void getFlaggedMaterialsByIdFiltering() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        Long id = flaggedMaterial.getId();

        defaultFlaggedMaterialShouldBeFound("id.equals=" + id);
        defaultFlaggedMaterialShouldNotBeFound("id.notEquals=" + id);

        defaultFlaggedMaterialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFlaggedMaterialShouldNotBeFound("id.greaterThan=" + id);

        defaultFlaggedMaterialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFlaggedMaterialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where material equals to DEFAULT_MATERIAL
        defaultFlaggedMaterialShouldBeFound("material.equals=" + DEFAULT_MATERIAL);

        // Get all the flaggedMaterialList where material equals to UPDATED_MATERIAL
        defaultFlaggedMaterialShouldNotBeFound("material.equals=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where material in DEFAULT_MATERIAL or UPDATED_MATERIAL
        defaultFlaggedMaterialShouldBeFound("material.in=" + DEFAULT_MATERIAL + "," + UPDATED_MATERIAL);

        // Get all the flaggedMaterialList where material equals to UPDATED_MATERIAL
        defaultFlaggedMaterialShouldNotBeFound("material.in=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where material is not null
        defaultFlaggedMaterialShouldBeFound("material.specified=true");

        // Get all the flaggedMaterialList where material is null
        defaultFlaggedMaterialShouldNotBeFound("material.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMaterialContainsSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where material contains DEFAULT_MATERIAL
        defaultFlaggedMaterialShouldBeFound("material.contains=" + DEFAULT_MATERIAL);

        // Get all the flaggedMaterialList where material contains UPDATED_MATERIAL
        defaultFlaggedMaterialShouldNotBeFound("material.contains=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMaterialNotContainsSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where material does not contain DEFAULT_MATERIAL
        defaultFlaggedMaterialShouldNotBeFound("material.doesNotContain=" + DEFAULT_MATERIAL);

        // Get all the flaggedMaterialList where material does not contain UPDATED_MATERIAL
        defaultFlaggedMaterialShouldBeFound("material.doesNotContain=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where description equals to DEFAULT_DESCRIPTION
        defaultFlaggedMaterialShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the flaggedMaterialList where description equals to UPDATED_DESCRIPTION
        defaultFlaggedMaterialShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFlaggedMaterialShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the flaggedMaterialList where description equals to UPDATED_DESCRIPTION
        defaultFlaggedMaterialShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where description is not null
        defaultFlaggedMaterialShouldBeFound("description.specified=true");

        // Get all the flaggedMaterialList where description is null
        defaultFlaggedMaterialShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where description contains DEFAULT_DESCRIPTION
        defaultFlaggedMaterialShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the flaggedMaterialList where description contains UPDATED_DESCRIPTION
        defaultFlaggedMaterialShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where description does not contain DEFAULT_DESCRIPTION
        defaultFlaggedMaterialShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the flaggedMaterialList where description does not contain UPDATED_DESCRIPTION
        defaultFlaggedMaterialShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByAbcClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where abcClassification equals to DEFAULT_ABC_CLASSIFICATION
        defaultFlaggedMaterialShouldBeFound("abcClassification.equals=" + DEFAULT_ABC_CLASSIFICATION);

        // Get all the flaggedMaterialList where abcClassification equals to UPDATED_ABC_CLASSIFICATION
        defaultFlaggedMaterialShouldNotBeFound("abcClassification.equals=" + UPDATED_ABC_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByAbcClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where abcClassification in DEFAULT_ABC_CLASSIFICATION or UPDATED_ABC_CLASSIFICATION
        defaultFlaggedMaterialShouldBeFound("abcClassification.in=" + DEFAULT_ABC_CLASSIFICATION + "," + UPDATED_ABC_CLASSIFICATION);

        // Get all the flaggedMaterialList where abcClassification equals to UPDATED_ABC_CLASSIFICATION
        defaultFlaggedMaterialShouldNotBeFound("abcClassification.in=" + UPDATED_ABC_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByAbcClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where abcClassification is not null
        defaultFlaggedMaterialShouldBeFound("abcClassification.specified=true");

        // Get all the flaggedMaterialList where abcClassification is null
        defaultFlaggedMaterialShouldNotBeFound("abcClassification.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant equals to DEFAULT_PLANT
        defaultFlaggedMaterialShouldBeFound("plant.equals=" + DEFAULT_PLANT);

        // Get all the flaggedMaterialList where plant equals to UPDATED_PLANT
        defaultFlaggedMaterialShouldNotBeFound("plant.equals=" + UPDATED_PLANT);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant in DEFAULT_PLANT or UPDATED_PLANT
        defaultFlaggedMaterialShouldBeFound("plant.in=" + DEFAULT_PLANT + "," + UPDATED_PLANT);

        // Get all the flaggedMaterialList where plant equals to UPDATED_PLANT
        defaultFlaggedMaterialShouldNotBeFound("plant.in=" + UPDATED_PLANT);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant is not null
        defaultFlaggedMaterialShouldBeFound("plant.specified=true");

        // Get all the flaggedMaterialList where plant is null
        defaultFlaggedMaterialShouldNotBeFound("plant.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant is greater than or equal to DEFAULT_PLANT
        defaultFlaggedMaterialShouldBeFound("plant.greaterThanOrEqual=" + DEFAULT_PLANT);

        // Get all the flaggedMaterialList where plant is greater than or equal to UPDATED_PLANT
        defaultFlaggedMaterialShouldNotBeFound("plant.greaterThanOrEqual=" + UPDATED_PLANT);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant is less than or equal to DEFAULT_PLANT
        defaultFlaggedMaterialShouldBeFound("plant.lessThanOrEqual=" + DEFAULT_PLANT);

        // Get all the flaggedMaterialList where plant is less than or equal to SMALLER_PLANT
        defaultFlaggedMaterialShouldNotBeFound("plant.lessThanOrEqual=" + SMALLER_PLANT);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsLessThanSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant is less than DEFAULT_PLANT
        defaultFlaggedMaterialShouldNotBeFound("plant.lessThan=" + DEFAULT_PLANT);

        // Get all the flaggedMaterialList where plant is less than UPDATED_PLANT
        defaultFlaggedMaterialShouldBeFound("plant.lessThan=" + UPDATED_PLANT);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByPlantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where plant is greater than DEFAULT_PLANT
        defaultFlaggedMaterialShouldNotBeFound("plant.greaterThan=" + DEFAULT_PLANT);

        // Get all the flaggedMaterialList where plant is greater than SMALLER_PLANT
        defaultFlaggedMaterialShouldBeFound("plant.greaterThan=" + SMALLER_PLANT);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMrpControllerIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where mrpController equals to DEFAULT_MRP_CONTROLLER
        defaultFlaggedMaterialShouldBeFound("mrpController.equals=" + DEFAULT_MRP_CONTROLLER);

        // Get all the flaggedMaterialList where mrpController equals to UPDATED_MRP_CONTROLLER
        defaultFlaggedMaterialShouldNotBeFound("mrpController.equals=" + UPDATED_MRP_CONTROLLER);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMrpControllerIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where mrpController in DEFAULT_MRP_CONTROLLER or UPDATED_MRP_CONTROLLER
        defaultFlaggedMaterialShouldBeFound("mrpController.in=" + DEFAULT_MRP_CONTROLLER + "," + UPDATED_MRP_CONTROLLER);

        // Get all the flaggedMaterialList where mrpController equals to UPDATED_MRP_CONTROLLER
        defaultFlaggedMaterialShouldNotBeFound("mrpController.in=" + UPDATED_MRP_CONTROLLER);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMrpControllerIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where mrpController is not null
        defaultFlaggedMaterialShouldBeFound("mrpController.specified=true");

        // Get all the flaggedMaterialList where mrpController is null
        defaultFlaggedMaterialShouldNotBeFound("mrpController.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMrpControllerContainsSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where mrpController contains DEFAULT_MRP_CONTROLLER
        defaultFlaggedMaterialShouldBeFound("mrpController.contains=" + DEFAULT_MRP_CONTROLLER);

        // Get all the flaggedMaterialList where mrpController contains UPDATED_MRP_CONTROLLER
        defaultFlaggedMaterialShouldNotBeFound("mrpController.contains=" + UPDATED_MRP_CONTROLLER);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByMrpControllerNotContainsSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where mrpController does not contain DEFAULT_MRP_CONTROLLER
        defaultFlaggedMaterialShouldNotBeFound("mrpController.doesNotContain=" + DEFAULT_MRP_CONTROLLER);

        // Get all the flaggedMaterialList where mrpController does not contain UPDATED_MRP_CONTROLLER
        defaultFlaggedMaterialShouldBeFound("mrpController.doesNotContain=" + UPDATED_MRP_CONTROLLER);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagMaterial equals to DEFAULT_FLAG_MATERIAL
        defaultFlaggedMaterialShouldBeFound("flagMaterial.equals=" + DEFAULT_FLAG_MATERIAL);

        // Get all the flaggedMaterialList where flagMaterial equals to UPDATED_FLAG_MATERIAL
        defaultFlaggedMaterialShouldNotBeFound("flagMaterial.equals=" + UPDATED_FLAG_MATERIAL);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagMaterial in DEFAULT_FLAG_MATERIAL or UPDATED_FLAG_MATERIAL
        defaultFlaggedMaterialShouldBeFound("flagMaterial.in=" + DEFAULT_FLAG_MATERIAL + "," + UPDATED_FLAG_MATERIAL);

        // Get all the flaggedMaterialList where flagMaterial equals to UPDATED_FLAG_MATERIAL
        defaultFlaggedMaterialShouldNotBeFound("flagMaterial.in=" + UPDATED_FLAG_MATERIAL);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagMaterial is not null
        defaultFlaggedMaterialShouldBeFound("flagMaterial.specified=true");

        // Get all the flaggedMaterialList where flagMaterial is null
        defaultFlaggedMaterialShouldNotBeFound("flagMaterial.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate equals to DEFAULT_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.equals=" + DEFAULT_FLAG_EXPIRATION_DATE);

        // Get all the flaggedMaterialList where flagExpirationDate equals to UPDATED_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.equals=" + UPDATED_FLAG_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate in DEFAULT_FLAG_EXPIRATION_DATE or UPDATED_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.in=" + DEFAULT_FLAG_EXPIRATION_DATE + "," + UPDATED_FLAG_EXPIRATION_DATE);

        // Get all the flaggedMaterialList where flagExpirationDate equals to UPDATED_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.in=" + UPDATED_FLAG_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate is not null
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.specified=true");

        // Get all the flaggedMaterialList where flagExpirationDate is null
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate is greater than or equal to DEFAULT_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.greaterThanOrEqual=" + DEFAULT_FLAG_EXPIRATION_DATE);

        // Get all the flaggedMaterialList where flagExpirationDate is greater than or equal to UPDATED_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.greaterThanOrEqual=" + UPDATED_FLAG_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate is less than or equal to DEFAULT_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.lessThanOrEqual=" + DEFAULT_FLAG_EXPIRATION_DATE);

        // Get all the flaggedMaterialList where flagExpirationDate is less than or equal to SMALLER_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.lessThanOrEqual=" + SMALLER_FLAG_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate is less than DEFAULT_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.lessThan=" + DEFAULT_FLAG_EXPIRATION_DATE);

        // Get all the flaggedMaterialList where flagExpirationDate is less than UPDATED_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.lessThan=" + UPDATED_FLAG_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllFlaggedMaterialsByFlagExpirationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        flaggedMaterialRepository.saveAndFlush(flaggedMaterial);

        // Get all the flaggedMaterialList where flagExpirationDate is greater than DEFAULT_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldNotBeFound("flagExpirationDate.greaterThan=" + DEFAULT_FLAG_EXPIRATION_DATE);

        // Get all the flaggedMaterialList where flagExpirationDate is greater than SMALLER_FLAG_EXPIRATION_DATE
        defaultFlaggedMaterialShouldBeFound("flagExpirationDate.greaterThan=" + SMALLER_FLAG_EXPIRATION_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFlaggedMaterialShouldBeFound(String filter) throws Exception {
        restFlaggedMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flaggedMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].abcClassification").value(hasItem(DEFAULT_ABC_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].plant").value(hasItem(DEFAULT_PLANT)))
            .andExpect(jsonPath("$.[*].mrpController").value(hasItem(DEFAULT_MRP_CONTROLLER)))
            .andExpect(jsonPath("$.[*].flagMaterial").value(hasItem(DEFAULT_FLAG_MATERIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].flagExpirationDate").value(hasItem(DEFAULT_FLAG_EXPIRATION_DATE.toString())));

        // Check, that the count call also returns 1
        restFlaggedMaterialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFlaggedMaterialShouldNotBeFound(String filter) throws Exception {
        restFlaggedMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFlaggedMaterialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE);

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
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(UPDATED_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(UPDATED_FLAG_EXPIRATION_DATE);
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
            .plant(UPDATED_PLANT);

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
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(DEFAULT_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(DEFAULT_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(DEFAULT_FLAG_EXPIRATION_DATE);
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
            .plant(UPDATED_PLANT)
            .mrpController(UPDATED_MRP_CONTROLLER)
            .flagMaterial(UPDATED_FLAG_MATERIAL)
            .flagExpirationDate(UPDATED_FLAG_EXPIRATION_DATE);

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
        assertThat(testFlaggedMaterial.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testFlaggedMaterial.getMrpController()).isEqualTo(UPDATED_MRP_CONTROLLER);
        assertThat(testFlaggedMaterial.getFlagMaterial()).isEqualTo(UPDATED_FLAG_MATERIAL);
        assertThat(testFlaggedMaterial.getFlagExpirationDate()).isEqualTo(UPDATED_FLAG_EXPIRATION_DATE);
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
