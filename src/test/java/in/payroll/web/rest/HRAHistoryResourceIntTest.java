package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.HRAHistory;
import in.payroll.repository.HRAHistoryRepository;
import in.payroll.service.HRAHistoryService;
import in.payroll.service.dto.HRAHistoryDTO;
import in.payroll.service.mapper.HRAHistoryMapper;
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
 * Test class for the HRAHistoryResource REST controller.
 *
 * @see HRAHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class HRAHistoryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CITY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CATEGORY = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENT_VALUE = 1L;
    private static final Long UPDATED_CURRENT_VALUE = 2L;

    @Autowired
    private HRAHistoryRepository hRAHistoryRepository;

    @Autowired
    private HRAHistoryMapper hRAHistoryMapper;

    @Autowired
    private HRAHistoryService hRAHistoryService;

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

    private MockMvc restHRAHistoryMockMvc;

    private HRAHistory hRAHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HRAHistoryResource hRAHistoryResource = new HRAHistoryResource(hRAHistoryService);
        this.restHRAHistoryMockMvc = MockMvcBuilders.standaloneSetup(hRAHistoryResource)
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
    public static HRAHistory createEntity(EntityManager em) {
        HRAHistory hRAHistory = new HRAHistory()
            .date(DEFAULT_DATE)
            .cityCategory(DEFAULT_CITY_CATEGORY)
            .currentValue(DEFAULT_CURRENT_VALUE);
        return hRAHistory;
    }

    @Before
    public void initTest() {
        hRAHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createHRAHistory() throws Exception {
        int databaseSizeBeforeCreate = hRAHistoryRepository.findAll().size();

        // Create the HRAHistory
        HRAHistoryDTO hRAHistoryDTO = hRAHistoryMapper.toDto(hRAHistory);
        restHRAHistoryMockMvc.perform(post("/api/hra-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRAHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the HRAHistory in the database
        List<HRAHistory> hRAHistoryList = hRAHistoryRepository.findAll();
        assertThat(hRAHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        HRAHistory testHRAHistory = hRAHistoryList.get(hRAHistoryList.size() - 1);
        assertThat(testHRAHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHRAHistory.getCityCategory()).isEqualTo(DEFAULT_CITY_CATEGORY);
        assertThat(testHRAHistory.getCurrentValue()).isEqualTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void createHRAHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hRAHistoryRepository.findAll().size();

        // Create the HRAHistory with an existing ID
        hRAHistory.setId(1L);
        HRAHistoryDTO hRAHistoryDTO = hRAHistoryMapper.toDto(hRAHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHRAHistoryMockMvc.perform(post("/api/hra-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HRAHistory in the database
        List<HRAHistory> hRAHistoryList = hRAHistoryRepository.findAll();
        assertThat(hRAHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHRAHistories() throws Exception {
        // Initialize the database
        hRAHistoryRepository.saveAndFlush(hRAHistory);

        // Get all the hRAHistoryList
        restHRAHistoryMockMvc.perform(get("/api/hra-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hRAHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cityCategory").value(hasItem(DEFAULT_CITY_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].currentValue").value(hasItem(DEFAULT_CURRENT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getHRAHistory() throws Exception {
        // Initialize the database
        hRAHistoryRepository.saveAndFlush(hRAHistory);

        // Get the hRAHistory
        restHRAHistoryMockMvc.perform(get("/api/hra-histories/{id}", hRAHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hRAHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.cityCategory").value(DEFAULT_CITY_CATEGORY.toString()))
            .andExpect(jsonPath("$.currentValue").value(DEFAULT_CURRENT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHRAHistory() throws Exception {
        // Get the hRAHistory
        restHRAHistoryMockMvc.perform(get("/api/hra-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHRAHistory() throws Exception {
        // Initialize the database
        hRAHistoryRepository.saveAndFlush(hRAHistory);

        int databaseSizeBeforeUpdate = hRAHistoryRepository.findAll().size();

        // Update the hRAHistory
        HRAHistory updatedHRAHistory = hRAHistoryRepository.findById(hRAHistory.getId()).get();
        // Disconnect from session so that the updates on updatedHRAHistory are not directly saved in db
        em.detach(updatedHRAHistory);
        updatedHRAHistory
            .date(UPDATED_DATE)
            .cityCategory(UPDATED_CITY_CATEGORY)
            .currentValue(UPDATED_CURRENT_VALUE);
        HRAHistoryDTO hRAHistoryDTO = hRAHistoryMapper.toDto(updatedHRAHistory);

        restHRAHistoryMockMvc.perform(put("/api/hra-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRAHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the HRAHistory in the database
        List<HRAHistory> hRAHistoryList = hRAHistoryRepository.findAll();
        assertThat(hRAHistoryList).hasSize(databaseSizeBeforeUpdate);
        HRAHistory testHRAHistory = hRAHistoryList.get(hRAHistoryList.size() - 1);
        assertThat(testHRAHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHRAHistory.getCityCategory()).isEqualTo(UPDATED_CITY_CATEGORY);
        assertThat(testHRAHistory.getCurrentValue()).isEqualTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingHRAHistory() throws Exception {
        int databaseSizeBeforeUpdate = hRAHistoryRepository.findAll().size();

        // Create the HRAHistory
        HRAHistoryDTO hRAHistoryDTO = hRAHistoryMapper.toDto(hRAHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHRAHistoryMockMvc.perform(put("/api/hra-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hRAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HRAHistory in the database
        List<HRAHistory> hRAHistoryList = hRAHistoryRepository.findAll();
        assertThat(hRAHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHRAHistory() throws Exception {
        // Initialize the database
        hRAHistoryRepository.saveAndFlush(hRAHistory);

        int databaseSizeBeforeDelete = hRAHistoryRepository.findAll().size();

        // Get the hRAHistory
        restHRAHistoryMockMvc.perform(delete("/api/hra-histories/{id}", hRAHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HRAHistory> hRAHistoryList = hRAHistoryRepository.findAll();
        assertThat(hRAHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HRAHistory.class);
        HRAHistory hRAHistory1 = new HRAHistory();
        hRAHistory1.setId(1L);
        HRAHistory hRAHistory2 = new HRAHistory();
        hRAHistory2.setId(hRAHistory1.getId());
        assertThat(hRAHistory1).isEqualTo(hRAHistory2);
        hRAHistory2.setId(2L);
        assertThat(hRAHistory1).isNotEqualTo(hRAHistory2);
        hRAHistory1.setId(null);
        assertThat(hRAHistory1).isNotEqualTo(hRAHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HRAHistoryDTO.class);
        HRAHistoryDTO hRAHistoryDTO1 = new HRAHistoryDTO();
        hRAHistoryDTO1.setId(1L);
        HRAHistoryDTO hRAHistoryDTO2 = new HRAHistoryDTO();
        assertThat(hRAHistoryDTO1).isNotEqualTo(hRAHistoryDTO2);
        hRAHistoryDTO2.setId(hRAHistoryDTO1.getId());
        assertThat(hRAHistoryDTO1).isEqualTo(hRAHistoryDTO2);
        hRAHistoryDTO2.setId(2L);
        assertThat(hRAHistoryDTO1).isNotEqualTo(hRAHistoryDTO2);
        hRAHistoryDTO1.setId(null);
        assertThat(hRAHistoryDTO1).isNotEqualTo(hRAHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hRAHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hRAHistoryMapper.fromId(null)).isNull();
    }
}
