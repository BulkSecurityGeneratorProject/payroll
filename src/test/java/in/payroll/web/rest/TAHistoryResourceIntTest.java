package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.TAHistory;
import in.payroll.repository.TAHistoryRepository;
import in.payroll.service.TAHistoryService;
import in.payroll.service.dto.TAHistoryDTO;
import in.payroll.service.mapper.TAHistoryMapper;
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
 * Test class for the TAHistoryResource REST controller.
 *
 * @see TAHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class TAHistoryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CITY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CATEGORY = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENT_VALUE = 1L;
    private static final Long UPDATED_CURRENT_VALUE = 2L;

    @Autowired
    private TAHistoryRepository tAHistoryRepository;

    @Autowired
    private TAHistoryMapper tAHistoryMapper;

    @Autowired
    private TAHistoryService tAHistoryService;

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

    private MockMvc restTAHistoryMockMvc;

    private TAHistory tAHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TAHistoryResource tAHistoryResource = new TAHistoryResource(tAHistoryService);
        this.restTAHistoryMockMvc = MockMvcBuilders.standaloneSetup(tAHistoryResource)
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
    public static TAHistory createEntity(EntityManager em) {
        TAHistory tAHistory = new TAHistory()
            .date(DEFAULT_DATE)
            .cityCategory(DEFAULT_CITY_CATEGORY)
            .currentValue(DEFAULT_CURRENT_VALUE);
        return tAHistory;
    }

    @Before
    public void initTest() {
        tAHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTAHistory() throws Exception {
        int databaseSizeBeforeCreate = tAHistoryRepository.findAll().size();

        // Create the TAHistory
        TAHistoryDTO tAHistoryDTO = tAHistoryMapper.toDto(tAHistory);
        restTAHistoryMockMvc.perform(post("/api/ta-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tAHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TAHistory in the database
        List<TAHistory> tAHistoryList = tAHistoryRepository.findAll();
        assertThat(tAHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TAHistory testTAHistory = tAHistoryList.get(tAHistoryList.size() - 1);
        assertThat(testTAHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTAHistory.getCityCategory()).isEqualTo(DEFAULT_CITY_CATEGORY);
        assertThat(testTAHistory.getCurrentValue()).isEqualTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void createTAHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tAHistoryRepository.findAll().size();

        // Create the TAHistory with an existing ID
        tAHistory.setId(1L);
        TAHistoryDTO tAHistoryDTO = tAHistoryMapper.toDto(tAHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTAHistoryMockMvc.perform(post("/api/ta-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TAHistory in the database
        List<TAHistory> tAHistoryList = tAHistoryRepository.findAll();
        assertThat(tAHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTAHistories() throws Exception {
        // Initialize the database
        tAHistoryRepository.saveAndFlush(tAHistory);

        // Get all the tAHistoryList
        restTAHistoryMockMvc.perform(get("/api/ta-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tAHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cityCategory").value(hasItem(DEFAULT_CITY_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].currentValue").value(hasItem(DEFAULT_CURRENT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getTAHistory() throws Exception {
        // Initialize the database
        tAHistoryRepository.saveAndFlush(tAHistory);

        // Get the tAHistory
        restTAHistoryMockMvc.perform(get("/api/ta-histories/{id}", tAHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tAHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.cityCategory").value(DEFAULT_CITY_CATEGORY.toString()))
            .andExpect(jsonPath("$.currentValue").value(DEFAULT_CURRENT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTAHistory() throws Exception {
        // Get the tAHistory
        restTAHistoryMockMvc.perform(get("/api/ta-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTAHistory() throws Exception {
        // Initialize the database
        tAHistoryRepository.saveAndFlush(tAHistory);

        int databaseSizeBeforeUpdate = tAHistoryRepository.findAll().size();

        // Update the tAHistory
        TAHistory updatedTAHistory = tAHistoryRepository.findById(tAHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTAHistory are not directly saved in db
        em.detach(updatedTAHistory);
        updatedTAHistory
            .date(UPDATED_DATE)
            .cityCategory(UPDATED_CITY_CATEGORY)
            .currentValue(UPDATED_CURRENT_VALUE);
        TAHistoryDTO tAHistoryDTO = tAHistoryMapper.toDto(updatedTAHistory);

        restTAHistoryMockMvc.perform(put("/api/ta-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tAHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the TAHistory in the database
        List<TAHistory> tAHistoryList = tAHistoryRepository.findAll();
        assertThat(tAHistoryList).hasSize(databaseSizeBeforeUpdate);
        TAHistory testTAHistory = tAHistoryList.get(tAHistoryList.size() - 1);
        assertThat(testTAHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTAHistory.getCityCategory()).isEqualTo(UPDATED_CITY_CATEGORY);
        assertThat(testTAHistory.getCurrentValue()).isEqualTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTAHistory() throws Exception {
        int databaseSizeBeforeUpdate = tAHistoryRepository.findAll().size();

        // Create the TAHistory
        TAHistoryDTO tAHistoryDTO = tAHistoryMapper.toDto(tAHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTAHistoryMockMvc.perform(put("/api/ta-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TAHistory in the database
        List<TAHistory> tAHistoryList = tAHistoryRepository.findAll();
        assertThat(tAHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTAHistory() throws Exception {
        // Initialize the database
        tAHistoryRepository.saveAndFlush(tAHistory);

        int databaseSizeBeforeDelete = tAHistoryRepository.findAll().size();

        // Get the tAHistory
        restTAHistoryMockMvc.perform(delete("/api/ta-histories/{id}", tAHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TAHistory> tAHistoryList = tAHistoryRepository.findAll();
        assertThat(tAHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TAHistory.class);
        TAHistory tAHistory1 = new TAHistory();
        tAHistory1.setId(1L);
        TAHistory tAHistory2 = new TAHistory();
        tAHistory2.setId(tAHistory1.getId());
        assertThat(tAHistory1).isEqualTo(tAHistory2);
        tAHistory2.setId(2L);
        assertThat(tAHistory1).isNotEqualTo(tAHistory2);
        tAHistory1.setId(null);
        assertThat(tAHistory1).isNotEqualTo(tAHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TAHistoryDTO.class);
        TAHistoryDTO tAHistoryDTO1 = new TAHistoryDTO();
        tAHistoryDTO1.setId(1L);
        TAHistoryDTO tAHistoryDTO2 = new TAHistoryDTO();
        assertThat(tAHistoryDTO1).isNotEqualTo(tAHistoryDTO2);
        tAHistoryDTO2.setId(tAHistoryDTO1.getId());
        assertThat(tAHistoryDTO1).isEqualTo(tAHistoryDTO2);
        tAHistoryDTO2.setId(2L);
        assertThat(tAHistoryDTO1).isNotEqualTo(tAHistoryDTO2);
        tAHistoryDTO1.setId(null);
        assertThat(tAHistoryDTO1).isNotEqualTo(tAHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tAHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tAHistoryMapper.fromId(null)).isNull();
    }
}
