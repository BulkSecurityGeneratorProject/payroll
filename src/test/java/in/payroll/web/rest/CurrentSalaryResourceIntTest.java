package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.CurrentSalary;
import in.payroll.repository.CurrentSalaryRepository;
import in.payroll.service.CurrentSalaryService;
import in.payroll.service.dto.CurrentSalaryDTO;
import in.payroll.service.mapper.CurrentSalaryMapper;
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
import java.util.List;


import static in.payroll.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CurrentSalaryResource REST controller.
 *
 * @see CurrentSalaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class CurrentSalaryResourceIntTest {

    private static final Long DEFAULT_BASIC_PAY = 1L;
    private static final Long UPDATED_BASIC_PAY = 2L;

    private static final Long DEFAULT_GRADE_PAY = 1L;
    private static final Long UPDATED_GRADE_PAY = 2L;

    private static final String DEFAULT_CITY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private CurrentSalaryRepository currentSalaryRepository;

    @Autowired
    private CurrentSalaryMapper currentSalaryMapper;

    @Autowired
    private CurrentSalaryService currentSalaryService;

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

    private MockMvc restCurrentSalaryMockMvc;

    private CurrentSalary currentSalary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrentSalaryResource currentSalaryResource = new CurrentSalaryResource(currentSalaryService);
        this.restCurrentSalaryMockMvc = MockMvcBuilders.standaloneSetup(currentSalaryResource)
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
    public static CurrentSalary createEntity(EntityManager em) {
        CurrentSalary currentSalary = new CurrentSalary()
            .basicPay(DEFAULT_BASIC_PAY)
            .gradePay(DEFAULT_GRADE_PAY)
            .cityCategory(DEFAULT_CITY_CATEGORY);
        return currentSalary;
    }

    @Before
    public void initTest() {
        currentSalary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrentSalary() throws Exception {
        int databaseSizeBeforeCreate = currentSalaryRepository.findAll().size();

        // Create the CurrentSalary
        CurrentSalaryDTO currentSalaryDTO = currentSalaryMapper.toDto(currentSalary);
        restCurrentSalaryMockMvc.perform(post("/api/current-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentSalaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CurrentSalary in the database
        List<CurrentSalary> currentSalaryList = currentSalaryRepository.findAll();
        assertThat(currentSalaryList).hasSize(databaseSizeBeforeCreate + 1);
        CurrentSalary testCurrentSalary = currentSalaryList.get(currentSalaryList.size() - 1);
        assertThat(testCurrentSalary.getBasicPay()).isEqualTo(DEFAULT_BASIC_PAY);
        assertThat(testCurrentSalary.getGradePay()).isEqualTo(DEFAULT_GRADE_PAY);
        assertThat(testCurrentSalary.getCityCategory()).isEqualTo(DEFAULT_CITY_CATEGORY);
    }

    @Test
    @Transactional
    public void createCurrentSalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currentSalaryRepository.findAll().size();

        // Create the CurrentSalary with an existing ID
        currentSalary.setId(1L);
        CurrentSalaryDTO currentSalaryDTO = currentSalaryMapper.toDto(currentSalary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentSalaryMockMvc.perform(post("/api/current-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentSalaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentSalary in the database
        List<CurrentSalary> currentSalaryList = currentSalaryRepository.findAll();
        assertThat(currentSalaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCurrentSalaries() throws Exception {
        // Initialize the database
        currentSalaryRepository.saveAndFlush(currentSalary);

        // Get all the currentSalaryList
        restCurrentSalaryMockMvc.perform(get("/api/current-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentSalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].basicPay").value(hasItem(DEFAULT_BASIC_PAY.intValue())))
            .andExpect(jsonPath("$.[*].gradePay").value(hasItem(DEFAULT_GRADE_PAY.intValue())))
            .andExpect(jsonPath("$.[*].cityCategory").value(hasItem(DEFAULT_CITY_CATEGORY.toString())));
    }
    
    @Test
    @Transactional
    public void getCurrentSalary() throws Exception {
        // Initialize the database
        currentSalaryRepository.saveAndFlush(currentSalary);

        // Get the currentSalary
        restCurrentSalaryMockMvc.perform(get("/api/current-salaries/{id}", currentSalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currentSalary.getId().intValue()))
            .andExpect(jsonPath("$.basicPay").value(DEFAULT_BASIC_PAY.intValue()))
            .andExpect(jsonPath("$.gradePay").value(DEFAULT_GRADE_PAY.intValue()))
            .andExpect(jsonPath("$.cityCategory").value(DEFAULT_CITY_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrentSalary() throws Exception {
        // Get the currentSalary
        restCurrentSalaryMockMvc.perform(get("/api/current-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrentSalary() throws Exception {
        // Initialize the database
        currentSalaryRepository.saveAndFlush(currentSalary);

        int databaseSizeBeforeUpdate = currentSalaryRepository.findAll().size();

        // Update the currentSalary
        CurrentSalary updatedCurrentSalary = currentSalaryRepository.findById(currentSalary.getId()).get();
        // Disconnect from session so that the updates on updatedCurrentSalary are not directly saved in db
        em.detach(updatedCurrentSalary);
        updatedCurrentSalary
            .basicPay(UPDATED_BASIC_PAY)
            .gradePay(UPDATED_GRADE_PAY)
            .cityCategory(UPDATED_CITY_CATEGORY);
        CurrentSalaryDTO currentSalaryDTO = currentSalaryMapper.toDto(updatedCurrentSalary);

        restCurrentSalaryMockMvc.perform(put("/api/current-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentSalaryDTO)))
            .andExpect(status().isOk());

        // Validate the CurrentSalary in the database
        List<CurrentSalary> currentSalaryList = currentSalaryRepository.findAll();
        assertThat(currentSalaryList).hasSize(databaseSizeBeforeUpdate);
        CurrentSalary testCurrentSalary = currentSalaryList.get(currentSalaryList.size() - 1);
        assertThat(testCurrentSalary.getBasicPay()).isEqualTo(UPDATED_BASIC_PAY);
        assertThat(testCurrentSalary.getGradePay()).isEqualTo(UPDATED_GRADE_PAY);
        assertThat(testCurrentSalary.getCityCategory()).isEqualTo(UPDATED_CITY_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrentSalary() throws Exception {
        int databaseSizeBeforeUpdate = currentSalaryRepository.findAll().size();

        // Create the CurrentSalary
        CurrentSalaryDTO currentSalaryDTO = currentSalaryMapper.toDto(currentSalary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentSalaryMockMvc.perform(put("/api/current-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentSalaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentSalary in the database
        List<CurrentSalary> currentSalaryList = currentSalaryRepository.findAll();
        assertThat(currentSalaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrentSalary() throws Exception {
        // Initialize the database
        currentSalaryRepository.saveAndFlush(currentSalary);

        int databaseSizeBeforeDelete = currentSalaryRepository.findAll().size();

        // Get the currentSalary
        restCurrentSalaryMockMvc.perform(delete("/api/current-salaries/{id}", currentSalary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrentSalary> currentSalaryList = currentSalaryRepository.findAll();
        assertThat(currentSalaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentSalary.class);
        CurrentSalary currentSalary1 = new CurrentSalary();
        currentSalary1.setId(1L);
        CurrentSalary currentSalary2 = new CurrentSalary();
        currentSalary2.setId(currentSalary1.getId());
        assertThat(currentSalary1).isEqualTo(currentSalary2);
        currentSalary2.setId(2L);
        assertThat(currentSalary1).isNotEqualTo(currentSalary2);
        currentSalary1.setId(null);
        assertThat(currentSalary1).isNotEqualTo(currentSalary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentSalaryDTO.class);
        CurrentSalaryDTO currentSalaryDTO1 = new CurrentSalaryDTO();
        currentSalaryDTO1.setId(1L);
        CurrentSalaryDTO currentSalaryDTO2 = new CurrentSalaryDTO();
        assertThat(currentSalaryDTO1).isNotEqualTo(currentSalaryDTO2);
        currentSalaryDTO2.setId(currentSalaryDTO1.getId());
        assertThat(currentSalaryDTO1).isEqualTo(currentSalaryDTO2);
        currentSalaryDTO2.setId(2L);
        assertThat(currentSalaryDTO1).isNotEqualTo(currentSalaryDTO2);
        currentSalaryDTO1.setId(null);
        assertThat(currentSalaryDTO1).isNotEqualTo(currentSalaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currentSalaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currentSalaryMapper.fromId(null)).isNull();
    }
}
