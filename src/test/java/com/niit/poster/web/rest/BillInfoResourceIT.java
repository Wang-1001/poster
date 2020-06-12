package com.niit.poster.web.rest;

import com.niit.poster.PosterServerApp;
import com.niit.poster.domain.BillInfo;
import com.niit.poster.repository.BillInfoRepository;
import com.niit.poster.service.BillInfoService;

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
 * Integration tests for the {@link BillInfoResource} REST controller.
 */
@SpringBootTest(classes = PosterServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillInfoResourceIT {

    private static final String DEFAULT_BILL_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BILL_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_PIC = "AAAAAAAAAA";
    private static final String UPDATED_BILL_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_WORD = "AAAAAAAAAA";
    private static final String UPDATED_BILL_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_BILL_AUTHOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BILL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BILL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_BILL_LAYOUT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_BILL_LAYOUT_MODE = "BBBBBBBBBB";

    @Autowired
    private BillInfoRepository billInfoRepository;

    @Autowired
    private BillInfoService billInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillInfoMockMvc;

    private BillInfo billInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillInfo createEntity(EntityManager em) {
        BillInfo billInfo = new BillInfo()
            .billUserName(DEFAULT_BILL_USER_NAME)
            .billPic(DEFAULT_BILL_PIC)
            .billWord(DEFAULT_BILL_WORD)
            .billAuthor(DEFAULT_BILL_AUTHOR)
            .billTime(DEFAULT_BILL_TIME)
            .billLayoutMode(DEFAULT_BILL_LAYOUT_MODE);
        return billInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillInfo createUpdatedEntity(EntityManager em) {
        BillInfo billInfo = new BillInfo()
            .billUserName(UPDATED_BILL_USER_NAME)
            .billPic(UPDATED_BILL_PIC)
            .billWord(UPDATED_BILL_WORD)
            .billAuthor(UPDATED_BILL_AUTHOR)
            .billTime(UPDATED_BILL_TIME)
            .billLayoutMode(UPDATED_BILL_LAYOUT_MODE);
        return billInfo;
    }

    @BeforeEach
    public void initTest() {
        billInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillInfo() throws Exception {
        int databaseSizeBeforeCreate = billInfoRepository.findAll().size();
        // Create the BillInfo
        restBillInfoMockMvc.perform(post("/api/bill-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billInfo)))
            .andExpect(status().isCreated());

        // Validate the BillInfo in the database
        List<BillInfo> billInfoList = billInfoRepository.findAll();
        assertThat(billInfoList).hasSize(databaseSizeBeforeCreate + 1);
        BillInfo testBillInfo = billInfoList.get(billInfoList.size() - 1);
        assertThat(testBillInfo.getBillUserName()).isEqualTo(DEFAULT_BILL_USER_NAME);
        assertThat(testBillInfo.getBillPic()).isEqualTo(DEFAULT_BILL_PIC);
        assertThat(testBillInfo.getBillWord()).isEqualTo(DEFAULT_BILL_WORD);
        assertThat(testBillInfo.getBillAuthor()).isEqualTo(DEFAULT_BILL_AUTHOR);
        assertThat(testBillInfo.getBillTime()).isEqualTo(DEFAULT_BILL_TIME);
        assertThat(testBillInfo.getBillLayoutMode()).isEqualTo(DEFAULT_BILL_LAYOUT_MODE);
    }

    @Test
    @Transactional
    public void createBillInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billInfoRepository.findAll().size();

        // Create the BillInfo with an existing ID
        billInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillInfoMockMvc.perform(post("/api/bill-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billInfo)))
            .andExpect(status().isBadRequest());

        // Validate the BillInfo in the database
        List<BillInfo> billInfoList = billInfoRepository.findAll();
        assertThat(billInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBillInfos() throws Exception {
        // Initialize the database
        billInfoRepository.saveAndFlush(billInfo);

        // Get all the billInfoList
        restBillInfoMockMvc.perform(get("/api/bill-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].billUserName").value(hasItem(DEFAULT_BILL_USER_NAME)))
            .andExpect(jsonPath("$.[*].billPic").value(hasItem(DEFAULT_BILL_PIC)))
            .andExpect(jsonPath("$.[*].billWord").value(hasItem(DEFAULT_BILL_WORD)))
            .andExpect(jsonPath("$.[*].billAuthor").value(hasItem(DEFAULT_BILL_AUTHOR)))
            .andExpect(jsonPath("$.[*].billTime").value(hasItem(sameInstant(DEFAULT_BILL_TIME))))
            .andExpect(jsonPath("$.[*].billLayoutMode").value(hasItem(DEFAULT_BILL_LAYOUT_MODE)));
    }
    
    @Test
    @Transactional
    public void getBillInfo() throws Exception {
        // Initialize the database
        billInfoRepository.saveAndFlush(billInfo);

        // Get the billInfo
        restBillInfoMockMvc.perform(get("/api/bill-infos/{id}", billInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billInfo.getId().intValue()))
            .andExpect(jsonPath("$.billUserName").value(DEFAULT_BILL_USER_NAME))
            .andExpect(jsonPath("$.billPic").value(DEFAULT_BILL_PIC))
            .andExpect(jsonPath("$.billWord").value(DEFAULT_BILL_WORD))
            .andExpect(jsonPath("$.billAuthor").value(DEFAULT_BILL_AUTHOR))
            .andExpect(jsonPath("$.billTime").value(sameInstant(DEFAULT_BILL_TIME)))
            .andExpect(jsonPath("$.billLayoutMode").value(DEFAULT_BILL_LAYOUT_MODE));
    }
    @Test
    @Transactional
    public void getNonExistingBillInfo() throws Exception {
        // Get the billInfo
        restBillInfoMockMvc.perform(get("/api/bill-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillInfo() throws Exception {
        // Initialize the database
        billInfoService.save(billInfo);

        int databaseSizeBeforeUpdate = billInfoRepository.findAll().size();

        // Update the billInfo
        BillInfo updatedBillInfo = billInfoRepository.findById(billInfo.getId()).get();
        // Disconnect from session so that the updates on updatedBillInfo are not directly saved in db
        em.detach(updatedBillInfo);
        updatedBillInfo
            .billUserName(UPDATED_BILL_USER_NAME)
            .billPic(UPDATED_BILL_PIC)
            .billWord(UPDATED_BILL_WORD)
            .billAuthor(UPDATED_BILL_AUTHOR)
            .billTime(UPDATED_BILL_TIME)
            .billLayoutMode(UPDATED_BILL_LAYOUT_MODE);

        restBillInfoMockMvc.perform(put("/api/bill-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillInfo)))
            .andExpect(status().isOk());

        // Validate the BillInfo in the database
        List<BillInfo> billInfoList = billInfoRepository.findAll();
        assertThat(billInfoList).hasSize(databaseSizeBeforeUpdate);
        BillInfo testBillInfo = billInfoList.get(billInfoList.size() - 1);
        assertThat(testBillInfo.getBillUserName()).isEqualTo(UPDATED_BILL_USER_NAME);
        assertThat(testBillInfo.getBillPic()).isEqualTo(UPDATED_BILL_PIC);
        assertThat(testBillInfo.getBillWord()).isEqualTo(UPDATED_BILL_WORD);
        assertThat(testBillInfo.getBillAuthor()).isEqualTo(UPDATED_BILL_AUTHOR);
        assertThat(testBillInfo.getBillTime()).isEqualTo(UPDATED_BILL_TIME);
        assertThat(testBillInfo.getBillLayoutMode()).isEqualTo(UPDATED_BILL_LAYOUT_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBillInfo() throws Exception {
        int databaseSizeBeforeUpdate = billInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillInfoMockMvc.perform(put("/api/bill-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billInfo)))
            .andExpect(status().isBadRequest());

        // Validate the BillInfo in the database
        List<BillInfo> billInfoList = billInfoRepository.findAll();
        assertThat(billInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillInfo() throws Exception {
        // Initialize the database
        billInfoService.save(billInfo);

        int databaseSizeBeforeDelete = billInfoRepository.findAll().size();

        // Delete the billInfo
        restBillInfoMockMvc.perform(delete("/api/bill-infos/{id}", billInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillInfo> billInfoList = billInfoRepository.findAll();
        assertThat(billInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
