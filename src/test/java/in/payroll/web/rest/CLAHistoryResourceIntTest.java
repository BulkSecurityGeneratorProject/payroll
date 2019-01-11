package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.CLAHistory;
import in.payroll.repository.CLAHistoryRepository;
import in.payroll.service.CLAHistoryService;
import in.payroll.service.dto.CLAHistoryDTO;
import in.payroll.service.mapper.CLAHistoryMapper;
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
 * Test class for the CLAHistoryResource REST controller.
 *
 * @see CLAHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class CLAHistoryResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CITY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CATEGORY = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENT_VALUE = 1L;
    private static final Long UPDATED_CURRENT_VALUE = 2L;

    @Autowired
    private CLAHistoryRepository cLAHistoryRepository;

    @Autowired
    private CLAHistoryMapper cLAHistoryMapper;

    @Autowired
    private CLAHistoryService cLAHistoryService;

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

    private MockMvc restCLAHistoryMockMvc;

    private CLAHistory cLAHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CLAHistoryResource cLAHistoryResource = new CLAHistoryResource(cLAHistoryService);
        this.restCLAHistoryMockMvc = MockMvcBuilders.standaloneSetup(cLAHistoryResource)
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
    public static CLAHistory createEntity(EntityManager em) {
        CLAHistory cLAHistory = new CLAHistory()
            .date(DEFAULT_DATE)
            .cityCategory(DEFAULT_CITY_CATEGORY)
            .currentValue(DEFAULT_CURRENT_VALUE);
        return cLAHistory;
    }

    @Before
    public void initTest() {
        cLAHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCLAHistory() throws Exception {
        int databaseSizeBeforeCreate = cLAHistoryRepository.findAll().size();

        // Create the CLAHistory
        CLAHistoryDTO cLAHistoryDTO = cLAHistoryMapper.toDto(cLAHistory);
        restCLAHistoryMockMvc.perform(post("/api/cla-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cLAHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CLAHistory in the database
        List<CLAHistory> cLAHistoryList = cLAHistoryRepository.findAll();
        assertThat(cLAHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        CLAHistory testCLAHistory = cLAHistoryList.get(cLAHistoryList.size() - 1);
        assertThat(testCLAHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCLAHistory.getCityCategory()).isEqualTo(DEFAULT_CITY_CATEGORY);
        assertThat(testCLAHistory.getCurrentValue()).isEqualTo(DEFAULT_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void createCLAHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cLAHistoryRepository.findAll().size();

        // Create the CLAHistory with an existing ID
        cLAHistory.setId(1L);
        CLAHistoryDTO cLAHistoryDTO = cLAHistoryMapper.toDto(cLAHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCLAHistoryMockMvc.perform(post("/api/cla-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cLAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLAHistory in the database
        List<CLAHistory> cLAHistoryList = cLAHistoryRepository.findAll();
        assertThat(cLAHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCLAHistories() throws Exception {
        // Initialize the database
        cLAHistoryRepository.saveAndFlush(cLAHistory);

        // Get all the cLAHistoryList
        restCLAHistoryMockMvc.perform(get("/api/cla-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLAHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cityCategory").value(hasItem(DEFAULT_CITY_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].currentValue").value(hasItem(DEFAULT_CURRENT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getCLAHistory() throws Exception {
        // Initialize the database
        cLAHistoryRepository.saveAndFlush(cLAHistory);

        // Get the cLAHistory
        restCLAHistoryMockMvc.perform(get("/api/cla-histories/{id}", cLAHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cLAHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.cityCategory").value(DEFAULT_CITY_CATEGORY.toString()))
            .andExpect(jsonPath("$.currentValue").value(DEFAULT_CURRENT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCLAHistory() throws Exception {
        // Get the cLAHistory
        restCLAHistoryMockMvc.perform(get("/api/cla-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCLAHistory() throws Exception {
        // Initialize the database
        cLAHistoryRepository.saveAndFlush(cLAHistory);

        int databaseSizeBeforeUpdate = cLAHistoryRepository.findAll().size();

        // Update the cLAHistory
        CLAHistory updatedCLAHistory = cLAHistoryRepository.findById(cLAHistory.getId()).get();
        // Disconnect from session so that the updates on updatedCLAHistory are not directly saved in db
        em.detach(updatedCLAHistory);
        updatedCLAHistory
            .date(UPDATED_DATE)
            .cityCategory(UPDATED_CITY_CATEGORY)
            .currentValue(UPDATED_CURRENT_VALUE);
        CLAHistoryDTO cLAHistoryDTO = cLAHistoryMapper.toDto(updatedCLAHistory);

        restCLAHistoryMockMvc.perform(put("/api/cla-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cLAHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the CLAHistory in the database
        List<CLAHistory> cLAHistoryList = cLAHistoryRepository.findAll();
        assertThat(cLAHistoryList).hasSize(databaseSizeBeforeUpdate);
        CLAHistory testCLAHistory = cLAHistoryList.get(cLAHistoryList.size() - 1);
        assertThat(testCLAHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCLAHistory.getCityCategory()).isEqualTo(UPDATED_CITY_CATEGORY);
        assertThat(testCLAHistory.getCurrentValue()).isEqualTo(UPDATED_CURRENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCLAHistory() throws Exception {
        int databaseSizeBeforeUpdate = cLAHistoryRepository.findAll().size();

        // Create the CLAHistory
        CLAHistoryDTO cLAHistoryDTO = cLAHistoryMapper.toDto(cLAHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLAHistoryMockMvc.perform(put("/api/cla-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cLAHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLAHistory in the database
        List<CLAHistory> cLAHistoryList = cLAHistoryRepository.findAll();
        assertThat(cLAHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCLAHistory() throws Exception {
        // Initialize the database
        cLAHistoryRepository.saveAndFlush(cLAHistory);

        int databaseSizeBeforeDelete = cLAHistoryRepository.findAll().size();

        // Get the cLAHistory
        restCLAHistoryMockMvc.perform(delete("/api/cla-histories/{id}", cLAHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CLAHistory> cLAHistoryList = cLAHistoryRepository.findAll();
        assertThat(cLAHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLAHistory.class);
        CLAHistory cLAHistory1 = new CLAHistory();
        cLAHistory1.setId(1L);
        CLAHistory cLAHistory2 = new CLAHistory();
        cLAHistory2.setId(cLAHistory1.getId());
        assertThat(cLAHistory1).isEqualTo(cLAHistory2);
        cLAHistory2.setId(2L);
        assertThat(cLAHistory1).isNotEqualTo(cLAHistory2);
        cLAHistory1.setId(null);
        assertThat(cLAHistory1).isNotEqualTo(cLAHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLAHistoryDTO.class);
        CLAHistoryDTO cLAHistoryDTO1 = new CLAHistoryDTO();
        cLAHistoryDTO1.setId(1L);
        CLAHistoryDTO cLAHistoryDTO2 = new CLAHistoryDTO();
        assertThat(cLAHistoryDTO1).isNotEqualTo(cLAHistoryDTO2);
        cLAHistoryDTO2.setId(cLAHistoryDTO1.getId());
        assertThat(cLAHistoryDTO1).isEqualTo(cLAHistoryDTO2);
        cLAHistoryDTO2.setId(2L);
        assertThat(cLAHistoryDTO1).isNotEqualTo(cLAHistoryDTO2);
        cLAHistoryDTO1.setId(null);
        assertThat(cLAHistoryDTO1).isNotEqualTo(cLAHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cLAHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cLAHistoryMapper.fromId(null)).isNull();
    }
}
