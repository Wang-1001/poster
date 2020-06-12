package com.niit.poster.web.rest;

import com.niit.poster.PosterServerApp;
import com.niit.poster.domain.BillType;
import com.niit.poster.repository.BillTypeRepository;
import com.niit.poster.service.BillTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.niit.poster.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BillTypeResource} REST controller.
 */
@SpringBootTest(classes = PosterServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillTypeResourceIT {

    private static final String DEFAULT_BILL_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BILL_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BILL_TYPE_SORT = 1;
    private static final Integer UPDATED_BILL_TYPE_SORT = 2;

    private static final ZonedDateTime DEFAULT_DATA_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BillTypeRepository billTypeRepository;

    @Autowired
    private BillTypeService billTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillTypeMockMvc;

    private BillType billType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillType createEntity(EntityManager em) {
        BillType billType = new BillType()
            .billTypeName(DEFAULT_BILL_TYPE_NAME)
            .billTypeSort(DEFAULT_BILL_TYPE_SORT)
            .dataTime(DEFAULT_DATA_TIME);
        return billType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillType createUpdatedEntity(EntityManager em) {
        BillType billType = new BillType()
            .billTypeName(UPDATED_BILL_TYPE_NAME)
            .billTypeSort(UPDATED_BILL_TYPE_SORT)
            .dataTime(UPDATED_DATA_TIME);
        return billType;
    }

    @BeforeEach
    public void initTest() {
        billType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillType() throws Exception {
        int databaseSizeBeforeCreate = billTypeRepository.findAll().size();
        // Create the BillType
        restBillTypeMockMvc.perform(post("/api/bill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billType)))
            .andExpect(status().isCreated());

        // Validate the BillType in the database
        List<BillType> billTypeList = billTypeRepository.findAll();
        assertThat(billTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BillType testBillType = billTypeList.get(billTypeList.size() - 1);
        assertThat(testBillType.getBillTypeName()).isEqualTo(DEFAULT_BILL_TYPE_NAME);
        assertThat(testBillType.getBillTypeSort()).isEqualTo(DEFAULT_BILL_TYPE_SORT);
        assertThat(testBillType.getDataTime()).isEqualTo(DEFAULT_DATA_TIME);
    }

    @Test
    @Transactional
    public void createBillTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billTypeRepository.findAll().size();

        // Create the BillType with an existing ID
        billType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillTypeMockMvc.perform(post("/api/bill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billType)))
            .andExpect(status().isBadRequest());

        // Validate the BillType in the database
        List<BillType> billTypeList = billTypeRepository.findAll();
        assertThat(billTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBillTypes() throws Exception {
        // Initialize the database
        billTypeRepository.saveAndFlush(billType);

        // Get all the billTypeList
        restBillTypeMockMvc.perform(get("/api/bill-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billType.getId().intValue())))
            .andExpect(jsonPath("$.[*].billTypeName").value(hasItem(DEFAULT_BILL_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].billTypeSort").value(hasItem(DEFAULT_BILL_TYPE_SORT)))
            .andExpect(jsonPath("$.[*].dataTime").value(hasItem(sameInstant(DEFAULT_DATA_TIME))));
    }
    
    @Test
    @Transactional
    public void getBillType() throws Exception {
        // Initialize the database
        billTypeRepository.saveAndFlush(billType);

        // Get the billType
        restBillTypeMockMvc.perform(get("/api/bill-types/{id}", billType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billType.getId().intValue()))
            .andExpect(jsonPath("$.billTypeName").value(DEFAULT_BILL_TYPE_NAME))
            .andExpect(jsonPath("$.billTypeSort").value(DEFAULT_BILL_TYPE_SORT))
            .andExpect(jsonPath("$.dataTime").value(sameInstant(DEFAULT_DATA_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingBillType() throws Exception {
        // Get the billType
        restBillTypeMockMvc.perform(get("/api/bill-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillType() throws Exception {
        // Initialize the database
        billTypeService.save(billType);

        int databaseSizeBeforeUpdate = billTypeRepository.findAll().size();

        // Update the billType
        BillType updatedBillType = billTypeRepository.findById(billType.getId()).get();
        // Disconnect from session so that the updates on updatedBillType are not directly saved in db
        em.detach(updatedBillType);
        updatedBillType
            .billTypeName(UPDATED_BILL_TYPE_NAME)
            .billTypeSort(UPDATED_BILL_TYPE_SORT)
            .dataTime(UPDATED_DATA_TIME);

        restBillTypeMockMvc.perform(put("/api/bill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillType)))
            .andExpect(status().isOk());

        // Validate the BillType in the database
        List<BillType> billTypeList = billTypeRepository.findAll();
        assertThat(billTypeList).hasSize(databaseSizeBeforeUpdate);
        BillType testBillType = billTypeList.get(billTypeList.size() - 1);
        assertThat(testBillType.getBillTypeName()).isEqualTo(UPDATED_BILL_TYPE_NAME);
        assertThat(testBillType.getBillTypeSort()).isEqualTo(UPDATED_BILL_TYPE_SORT);
        assertThat(testBillType.getDataTime()).isEqualTo(UPDATED_DATA_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBillType() throws Exception {
        int databaseSizeBeforeUpdate = billTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillTypeMockMvc.perform(put("/api/bill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billType)))
            .andExpect(status().isBadRequest());

        // Validate the BillType in the database
        List<BillType> billTypeList = billTypeRepository.findAll();
        assertThat(billTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillType() throws Exception {
        // Initialize the database
        billTypeService.save(billType);

        int databaseSizeBeforeDelete = billTypeRepository.findAll().size();

        // Delete the billType
        restBillTypeMockMvc.perform(delete("/api/bill-types/{id}", billType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillType> billTypeList = billTypeRepository.findAll();
        assertThat(billTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
