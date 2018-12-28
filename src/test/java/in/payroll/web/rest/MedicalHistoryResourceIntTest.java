package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.MedicalHistory;
import in.payroll.repository.MedicalHistoryRepository;
import in.payroll.service.MedicalHistoryService;
import in.payroll.service.dto.MedicalHistoryDTO;
import in.payroll.service.mapper.MedicalHistoryMapper;
import in.payroll.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static in.payroll.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedicalHistoryResource REST controller.
 *
 * @see MedicalHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class MedicalHistoryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CURRENT_VALUE = 1L;
    private static final Long UPDATED_CURRENT_VALUE = 2L;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private MedicalHistoryMapper medicalHistoryMapper;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMedicalHistoryMockMvc;

    private MedicalHistory medicalHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalHistoryResource medicalHistoryResource = new MedicalHistoryResource(medicalHistoryService);
        this.restMedicalHistoryMockMvc = MockMvcBuilders.standaloneSetup(medicalHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalHistory createEntity(EntityManager em) {
        MedicalHistory medicalHistory = new MedicalHistory()
            .date(DEFAULT_DATE)
            .currentValue(DEFAULT_CURRENT_VALUE);
        return medicalHistory;
    }

    @Before
    public void initTest() {
        medicalHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalHistory() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(medicalHistory);
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMedicalHistory.getCurrentValue()).isEqualTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void createMedicalHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory with an existing ID
        medicalHistory.setId(1L);
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(medicalHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicalHistories() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get all the medicalHistoryList
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].currentValue").value(hasItem(DEFAULT_CURRENT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", medicalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.currentValue").value(DEFAULT_CURRENT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalHistory() throws Exception {
        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Update the medicalHistory
        MedicalHistory updatedMedicalHistory = medicalHistoryRepository.findById(medicalHistory.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalHistory are not directly saved in db
        em.detach(updatedMedicalHistory);
        updatedMedicalHistory
            .date(UPDATED_DATE)
            .currentValue(UPDATED_CURRENT_VALUE);
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(updatedMedicalHistory);

        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMedicalHistory.getCurrentValue()).isEqualTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalHistory() throws Exception {
        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(medicalHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        int databaseSizeBeforeDelete = medicalHistoryRepository.findAll().size();

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(delete("/api/medical-histories/{id}", medicalHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistory.class);
        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1L);
        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setId(medicalHistory1.getId());
        assertThat(medicalHistory1).isEqualTo(medicalHistory2);
        medicalHistory2.setId(2L);
        assertThat(medicalHistory1).isNotEqualTo(medicalHistory2);
        medicalHistory1.setId(null);
        assertThat(medicalHistory1).isNotEqualTo(medicalHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistoryDTO.class);
        MedicalHistoryDTO medicalHistoryDTO1 = new MedicalHistoryDTO();
        medicalHistoryDTO1.setId(1L);
        MedicalHistoryDTO medicalHistoryDTO2 = new MedicalHistoryDTO();
        assertThat(medicalHistoryDTO1).isNotEqualTo(medicalHistoryDTO2);
        medicalHistoryDTO2.setId(medicalHistoryDTO1.getId());
        assertThat(medicalHistoryDTO1).isEqualTo(medicalHistoryDTO2);
        medicalHistoryDTO2.setId(2L);
        assertThat(medicalHistoryDTO1).isNotEqualTo(medicalHistoryDTO2);
        medicalHistoryDTO1.setId(null);
        assertThat(medicalHistoryDTO1).isNotEqualTo(medicalHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicalHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicalHistoryMapper.fromId(null)).isNull();
    }
}
