package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.DAHistory;
import in.payroll.repository.DAHistoryRepository;
import in.payroll.service.DAHistoryService;
import in.payroll.service.dto.DAHistoryDTO;
import in.payroll.service.mapper.DAHistoryMapper;
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
 * Test class for the DAHistoryResource REST controller.
 *
 * @see DAHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class DAHistoryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_INCREASE = 1L;
    private static final Long UPDATED_INCREASE = 2L;

    private static final Long DEFAULT_CURRENT_VALUE = 1L;
    private static final Long UPDATED_CURRENT_VALUE = 2L;

    @Autowired
    private DAHistoryRepository dAHistoryRepository;

    @Autowired
    private DAHistoryMapper dAHistoryMapper;

    @Autowired
    private DAHistoryService dAHistoryService;

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

    private MockMvc restDAHistoryMockMvc;

    private DAHistory dAHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DAHistoryResource dAHistoryResource = new DAHistoryResource(dAHistoryService);
        this.restDAHistoryMockMvc = MockMvcBuilders.standaloneSetup(dAHistoryResource)
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
    public static DAHistory createEntity(EntityManager em) {
        DAHistory dAHistory = new DAHistory()
            .date(DEFAULT_DATE)
            .increase(DEFAULT_INCREASE)
            .currentValue(DEFAULT_CURRENT_VALUE);
        return dAHistory;
    }

    @Before
    public void initTest() {
        dAHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDAHistory() throws Exception {
        int databaseSizeBeforeCreate = dAHistoryRepository.findAll().size();

        // Create the DAHistory
        DAHistoryDTO dAHistoryDTO = dAHistoryMapper.toDto(dAHistory);
        restDAHistoryMockMvc.perform(post("/api/da-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dAHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the DAHistory in the database
        List<DAHistory> dAHistoryList = dAHistoryRepository.findAll();
        assertThat(dAHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        DAHistory testDAHistory = dAHistoryList.get(dAHistoryList.size() - 1);
        assertThat(testDAHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDAHistory.getIncrease()).isEqualTo(DEFAULT_INCREASE);
        assertThat(testDAHistory.getCurrentValue()).isEqualTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void createDAHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dAHistoryRepository.findAll().size();

        // Create the DAHistory with an existing ID
        dAHistory.setId(1L);
        DAHistoryDTO dAHistoryDTO = dAHistoryMapper.toDto(dAHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDAHistoryMockMvc.perform(post("/api/da-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DAHistory in the database
        List<DAHistory> dAHistoryList = dAHistoryRepository.findAll();
        assertThat(dAHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDAHistories() throws Exception {
        // Initialize the database
        dAHistoryRepository.saveAndFlush(dAHistory);

        // Get all the dAHistoryList
        restDAHistoryMockMvc.perform(get("/api/da-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dAHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].increase").value(hasItem(DEFAULT_INCREASE.intValue())))
            .andExpect(jsonPath("$.[*].currentValue").value(hasItem(DEFAULT_CURRENT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getDAHistory() throws Exception {
        // Initialize the database
        dAHistoryRepository.saveAndFlush(dAHistory);

        // Get the dAHistory
        restDAHistoryMockMvc.perform(get("/api/da-histories/{id}", dAHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dAHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.increase").value(DEFAULT_INCREASE.intValue()))
            .andExpect(jsonPath("$.currentValue").value(DEFAULT_CURRENT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDAHistory() throws Exception {
        // Get the dAHistory
        restDAHistoryMockMvc.perform(get("/api/da-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDAHistory() throws Exception {
        // Initialize the database
        dAHistoryRepository.saveAndFlush(dAHistory);

        int databaseSizeBeforeUpdate = dAHistoryRepository.findAll().size();

        // Update the dAHistory
        DAHistory updatedDAHistory = dAHistoryRepository.findById(dAHistory.getId()).get();
        // Disconnect from session so that the updates on updatedDAHistory are not directly saved in db
        em.detach(updatedDAHistory);
        updatedDAHistory
            .date(UPDATED_DATE)
            .increase(UPDATED_INCREASE)
            .currentValue(UPDATED_CURRENT_VALUE);
        DAHistoryDTO dAHistoryDTO = dAHistoryMapper.toDto(updatedDAHistory);

        restDAHistoryMockMvc.perform(put("/api/da-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dAHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the DAHistory in the database
        List<DAHistory> dAHistoryList = dAHistoryRepository.findAll();
        assertThat(dAHistoryList).hasSize(databaseSizeBeforeUpdate);
        DAHistory testDAHistory = dAHistoryList.get(dAHistoryList.size() - 1);
        assertThat(testDAHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDAHistory.getIncrease()).isEqualTo(UPDATED_INCREASE);
        assertThat(testDAHistory.getCurrentValue()).isEqualTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingDAHistory() throws Exception {
        int databaseSizeBeforeUpdate = dAHistoryRepository.findAll().size();

        // Create the DAHistory
        DAHistoryDTO dAHistoryDTO = dAHistoryMapper.toDto(dAHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDAHistoryMockMvc.perform(put("/api/da-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DAHistory in the database
        List<DAHistory> dAHistoryList = dAHistoryRepository.findAll();
        assertThat(dAHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDAHistory() throws Exception {
        // Initialize the database
        dAHistoryRepository.saveAndFlush(dAHistory);

        int databaseSizeBeforeDelete = dAHistoryRepository.findAll().size();

        // Get the dAHistory
        restDAHistoryMockMvc.perform(delete("/api/da-histories/{id}", dAHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DAHistory> dAHistoryList = dAHistoryRepository.findAll();
        assertThat(dAHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DAHistory.class);
        DAHistory dAHistory1 = new DAHistory();
        dAHistory1.setId(1L);
        DAHistory dAHistory2 = new DAHistory();
        dAHistory2.setId(dAHistory1.getId());
        assertThat(dAHistory1).isEqualTo(dAHistory2);
        dAHistory2.setId(2L);
        assertThat(dAHistory1).isNotEqualTo(dAHistory2);
        dAHistory1.setId(null);
        assertThat(dAHistory1).isNotEqualTo(dAHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DAHistoryDTO.class);
        DAHistoryDTO dAHistoryDTO1 = new DAHistoryDTO();
        dAHistoryDTO1.setId(1L);
        DAHistoryDTO dAHistoryDTO2 = new DAHistoryDTO();
        assertThat(dAHistoryDTO1).isNotEqualTo(dAHistoryDTO2);
        dAHistoryDTO2.setId(dAHistoryDTO1.getId());
        assertThat(dAHistoryDTO1).isEqualTo(dAHistoryDTO2);
        dAHistoryDTO2.setId(2L);
        assertThat(dAHistoryDTO1).isNotEqualTo(dAHistoryDTO2);
        dAHistoryDTO1.setId(null);
        assertThat(dAHistoryDTO1).isNotEqualTo(dAHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dAHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dAHistoryMapper.fromId(null)).isNull();
    }
}
