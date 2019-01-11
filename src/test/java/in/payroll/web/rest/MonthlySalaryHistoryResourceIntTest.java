package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.MonthlySalaryHistory;
import in.payroll.repository.MonthlySalaryHistoryRepository;
import in.payroll.service.MonthlySalaryHistoryService;
import in.payroll.service.dto.MonthlySalaryHistoryDTO;
import in.payroll.service.mapper.MonthlySalaryHistoryMapper;
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
 * Test class for the MonthlySalaryHistoryResource REST controller.
 *
 * @see MonthlySalaryHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class MonthlySalaryHistoryResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final String DEFAULT_OFFICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_BASIC_PAY = 1L;
    private static final Long UPDATED_BASIC_PAY = 2L;

    private static final Long DEFAULT_GRADE_PAY = 1L;
    private static final Long UPDATED_GRADE_PAY = 2L;

    private static final Long DEFAULT_BASIC_TOTAL = 1L;
    private static final Long UPDATED_BASIC_TOTAL = 2L;

    private static final Long DEFAULT_DA_PERCENT = 1L;
    private static final Long UPDATED_DA_PERCENT = 2L;

    private static final Long DEFAULT_DA_VALUE = 1L;
    private static final Long UPDATED_DA_VALUE = 2L;

    private static final Long DEFAULT_TOTAL_SALARY = 1L;
    private static final Long UPDATED_TOTAL_SALARY = 2L;

    private static final String DEFAULT_CITY_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CATEGORY = "BBBBBBBBBB";

    private static final Long DEFAULT_HRA_PERCENT = 1L;
    private static final Long UPDATED_HRA_PERCENT = 2L;

    private static final Long DEFAULT_HRA_VALUE = 1L;
    private static final Long UPDATED_HRA_VALUE = 2L;

    private static final Long DEFAULT_TRAVEL_ALLOWANCE = 1L;
    private static final Long UPDATED_TRAVEL_ALLOWANCE = 2L;

    private static final Long DEFAULT_CLA = 1L;
    private static final Long UPDATED_CLA = 2L;

    private static final Long DEFAULT_MEDICAL = 1L;
    private static final Long UPDATED_MEDICAL = 2L;

    private static final Long DEFAULT_GROSS_SALARY = 1L;
    private static final Long UPDATED_GROSS_SALARY = 2L;

    private static final Long DEFAULT_PROF_TAX = 1L;
    private static final Long UPDATED_PROF_TAX = 2L;

    private static final Long DEFAULT_INSURANCE = 1L;
    private static final Long UPDATED_INSURANCE = 2L;

    private static final Long DEFAULT_GPF = 1L;
    private static final Long UPDATED_GPF = 2L;

    private static final Long DEFAULT_CPG = 1L;
    private static final Long UPDATED_CPG = 2L;

    private static final Long DEFAULT_INCOMETAX = 1L;
    private static final Long UPDATED_INCOMETAX = 2L;

    private static final Long DEFAULT_TOTAL_DEDUCTION = 1L;
    private static final Long UPDATED_TOTAL_DEDUCTION = 2L;

    private static final Long DEFAULT_NET_SALARY = 1L;
    private static final Long UPDATED_NET_SALARY = 2L;

    @Autowired
    private MonthlySalaryHistoryRepository monthlySalaryHistoryRepository;

    @Autowired
    private MonthlySalaryHistoryMapper monthlySalaryHistoryMapper;

    @Autowired
    private MonthlySalaryHistoryService monthlySalaryHistoryService;

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

    private MockMvc restMonthlySalaryHistoryMockMvc;

    private MonthlySalaryHistory monthlySalaryHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonthlySalaryHistoryResource monthlySalaryHistoryResource = new MonthlySalaryHistoryResource(monthlySalaryHistoryService);
        this.restMonthlySalaryHistoryMockMvc = MockMvcBuilders.standaloneSetup(monthlySalaryHistoryResource)
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
    public static MonthlySalaryHistory createEntity(EntityManager em) {
        MonthlySalaryHistory monthlySalaryHistory = new MonthlySalaryHistory()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .officeName(DEFAULT_OFFICE_NAME)
            .basicPay(DEFAULT_BASIC_PAY)
            .gradePay(DEFAULT_GRADE_PAY)
            .basicTotal(DEFAULT_BASIC_TOTAL)
            .daPercent(DEFAULT_DA_PERCENT)
            .daValue(DEFAULT_DA_VALUE)
            .totalSalary(DEFAULT_TOTAL_SALARY)
            .cityCategory(DEFAULT_CITY_CATEGORY)
            .hraPercent(DEFAULT_HRA_PERCENT)
            .hraValue(DEFAULT_HRA_VALUE)
            .travelAllowance(DEFAULT_TRAVEL_ALLOWANCE)
            .cla(DEFAULT_CLA)
            .medical(DEFAULT_MEDICAL)
            .grossSalary(DEFAULT_GROSS_SALARY)
            .profTax(DEFAULT_PROF_TAX)
            .insurance(DEFAULT_INSURANCE)
            .gpf(DEFAULT_GPF)
            .cpg(DEFAULT_CPG)
            .incometax(DEFAULT_INCOMETAX)
            .totalDeduction(DEFAULT_TOTAL_DEDUCTION)
            .netSalary(DEFAULT_NET_SALARY);
        return monthlySalaryHistory;
    }

    @Before
    public void initTest() {
        monthlySalaryHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlySalaryHistory() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryHistoryRepository.findAll().size();

        // Create the MonthlySalaryHistory
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO = monthlySalaryHistoryMapper.toDto(monthlySalaryHistory);
        restMonthlySalaryHistoryMockMvc.perform(post("/api/monthly-salary-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MonthlySalaryHistory in the database
        List<MonthlySalaryHistory> monthlySalaryHistoryList = monthlySalaryHistoryRepository.findAll();
        assertThat(monthlySalaryHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlySalaryHistory testMonthlySalaryHistory = monthlySalaryHistoryList.get(monthlySalaryHistoryList.size() - 1);
        assertThat(testMonthlySalaryHistory.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMonthlySalaryHistory.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testMonthlySalaryHistory.getOfficeName()).isEqualTo(DEFAULT_OFFICE_NAME);
        assertThat(testMonthlySalaryHistory.getBasicPay()).isEqualTo(DEFAULT_BASIC_PAY);
        assertThat(testMonthlySalaryHistory.getGradePay()).isEqualTo(DEFAULT_GRADE_PAY);
        assertThat(testMonthlySalaryHistory.getBasicTotal()).isEqualTo(DEFAULT_BASIC_TOTAL);
        assertThat(testMonthlySalaryHistory.getDaPercent()).isEqualTo(DEFAULT_DA_PERCENT);
        assertThat(testMonthlySalaryHistory.getDaValue()).isEqualTo(DEFAULT_DA_VALUE);
        assertThat(testMonthlySalaryHistory.getTotalSalary()).isEqualTo(DEFAULT_TOTAL_SALARY);
        assertThat(testMonthlySalaryHistory.getCityCategory()).isEqualTo(DEFAULT_CITY_CATEGORY);
        assertThat(testMonthlySalaryHistory.getHraPercent()).isEqualTo(DEFAULT_HRA_PERCENT);
        assertThat(testMonthlySalaryHistory.getHraValue()).isEqualTo(DEFAULT_HRA_VALUE);
        assertThat(testMonthlySalaryHistory.getTravelAllowance()).isEqualTo(DEFAULT_TRAVEL_ALLOWANCE);
        assertThat(testMonthlySalaryHistory.getCla()).isEqualTo(DEFAULT_CLA);
        assertThat(testMonthlySalaryHistory.getMedical()).isEqualTo(DEFAULT_MEDICAL);
        assertThat(testMonthlySalaryHistory.getGrossSalary()).isEqualTo(DEFAULT_GROSS_SALARY);
        assertThat(testMonthlySalaryHistory.getProfTax()).isEqualTo(DEFAULT_PROF_TAX);
        assertThat(testMonthlySalaryHistory.getInsurance()).isEqualTo(DEFAULT_INSURANCE);
        assertThat(testMonthlySalaryHistory.getGpf()).isEqualTo(DEFAULT_GPF);
        assertThat(testMonthlySalaryHistory.getCpg()).isEqualTo(DEFAULT_CPG);
        assertThat(testMonthlySalaryHistory.getIncometax()).isEqualTo(DEFAULT_INCOMETAX);
        assertThat(testMonthlySalaryHistory.getTotalDeduction()).isEqualTo(DEFAULT_TOTAL_DEDUCTION);
        assertThat(testMonthlySalaryHistory.getNetSalary()).isEqualTo(DEFAULT_NET_SALARY);
    }

    @Test
    @Transactional
    public void createMonthlySalaryHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryHistoryRepository.findAll().size();

        // Create the MonthlySalaryHistory with an existing ID
        monthlySalaryHistory.setId(1L);
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO = monthlySalaryHistoryMapper.toDto(monthlySalaryHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlySalaryHistoryMockMvc.perform(post("/api/monthly-salary-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalaryHistory in the database
        List<MonthlySalaryHistory> monthlySalaryHistoryList = monthlySalaryHistoryRepository.findAll();
        assertThat(monthlySalaryHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaryHistories() throws Exception {
        // Initialize the database
        monthlySalaryHistoryRepository.saveAndFlush(monthlySalaryHistory);

        // Get all the monthlySalaryHistoryList
        restMonthlySalaryHistoryMockMvc.perform(get("/api/monthly-salary-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalaryHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].officeName").value(hasItem(DEFAULT_OFFICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].basicPay").value(hasItem(DEFAULT_BASIC_PAY.intValue())))
            .andExpect(jsonPath("$.[*].gradePay").value(hasItem(DEFAULT_GRADE_PAY.intValue())))
            .andExpect(jsonPath("$.[*].basicTotal").value(hasItem(DEFAULT_BASIC_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].daPercent").value(hasItem(DEFAULT_DA_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].daValue").value(hasItem(DEFAULT_DA_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].totalSalary").value(hasItem(DEFAULT_TOTAL_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].cityCategory").value(hasItem(DEFAULT_CITY_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].hraPercent").value(hasItem(DEFAULT_HRA_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].hraValue").value(hasItem(DEFAULT_HRA_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].travelAllowance").value(hasItem(DEFAULT_TRAVEL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].cla").value(hasItem(DEFAULT_CLA.intValue())))
            .andExpect(jsonPath("$.[*].medical").value(hasItem(DEFAULT_MEDICAL.intValue())))
            .andExpect(jsonPath("$.[*].grossSalary").value(hasItem(DEFAULT_GROSS_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].profTax").value(hasItem(DEFAULT_PROF_TAX.intValue())))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(DEFAULT_INSURANCE.intValue())))
            .andExpect(jsonPath("$.[*].gpf").value(hasItem(DEFAULT_GPF.intValue())))
            .andExpect(jsonPath("$.[*].cpg").value(hasItem(DEFAULT_CPG.intValue())))
            .andExpect(jsonPath("$.[*].incometax").value(hasItem(DEFAULT_INCOMETAX.intValue())))
            .andExpect(jsonPath("$.[*].totalDeduction").value(hasItem(DEFAULT_TOTAL_DEDUCTION.intValue())))
            .andExpect(jsonPath("$.[*].netSalary").value(hasItem(DEFAULT_NET_SALARY.intValue())));
    }
    
    @Test
    @Transactional
    public void getMonthlySalaryHistory() throws Exception {
        // Initialize the database
        monthlySalaryHistoryRepository.saveAndFlush(monthlySalaryHistory);

        // Get the monthlySalaryHistory
        restMonthlySalaryHistoryMockMvc.perform(get("/api/monthly-salary-histories/{id}", monthlySalaryHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monthlySalaryHistory.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.officeName").value(DEFAULT_OFFICE_NAME.toString()))
            .andExpect(jsonPath("$.basicPay").value(DEFAULT_BASIC_PAY.intValue()))
            .andExpect(jsonPath("$.gradePay").value(DEFAULT_GRADE_PAY.intValue()))
            .andExpect(jsonPath("$.basicTotal").value(DEFAULT_BASIC_TOTAL.intValue()))
            .andExpect(jsonPath("$.daPercent").value(DEFAULT_DA_PERCENT.intValue()))
            .andExpect(jsonPath("$.daValue").value(DEFAULT_DA_VALUE.intValue()))
            .andExpect(jsonPath("$.totalSalary").value(DEFAULT_TOTAL_SALARY.intValue()))
            .andExpect(jsonPath("$.cityCategory").value(DEFAULT_CITY_CATEGORY.toString()))
            .andExpect(jsonPath("$.hraPercent").value(DEFAULT_HRA_PERCENT.intValue()))
            .andExpect(jsonPath("$.hraValue").value(DEFAULT_HRA_VALUE.intValue()))
            .andExpect(jsonPath("$.travelAllowance").value(DEFAULT_TRAVEL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.cla").value(DEFAULT_CLA.intValue()))
            .andExpect(jsonPath("$.medical").value(DEFAULT_MEDICAL.intValue()))
            .andExpect(jsonPath("$.grossSalary").value(DEFAULT_GROSS_SALARY.intValue()))
            .andExpect(jsonPath("$.profTax").value(DEFAULT_PROF_TAX.intValue()))
            .andExpect(jsonPath("$.insurance").value(DEFAULT_INSURANCE.intValue()))
            .andExpect(jsonPath("$.gpf").value(DEFAULT_GPF.intValue()))
            .andExpect(jsonPath("$.cpg").value(DEFAULT_CPG.intValue()))
            .andExpect(jsonPath("$.incometax").value(DEFAULT_INCOMETAX.intValue()))
            .andExpect(jsonPath("$.totalDeduction").value(DEFAULT_TOTAL_DEDUCTION.intValue()))
            .andExpect(jsonPath("$.netSalary").value(DEFAULT_NET_SALARY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMonthlySalaryHistory() throws Exception {
        // Get the monthlySalaryHistory
        restMonthlySalaryHistoryMockMvc.perform(get("/api/monthly-salary-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlySalaryHistory() throws Exception {
        // Initialize the database
        monthlySalaryHistoryRepository.saveAndFlush(monthlySalaryHistory);

        int databaseSizeBeforeUpdate = monthlySalaryHistoryRepository.findAll().size();

        // Update the monthlySalaryHistory
        MonthlySalaryHistory updatedMonthlySalaryHistory = monthlySalaryHistoryRepository.findById(monthlySalaryHistory.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlySalaryHistory are not directly saved in db
        em.detach(updatedMonthlySalaryHistory);
        updatedMonthlySalaryHistory
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .officeName(UPDATED_OFFICE_NAME)
            .basicPay(UPDATED_BASIC_PAY)
            .gradePay(UPDATED_GRADE_PAY)
            .basicTotal(UPDATED_BASIC_TOTAL)
            .daPercent(UPDATED_DA_PERCENT)
            .daValue(UPDATED_DA_VALUE)
            .totalSalary(UPDATED_TOTAL_SALARY)
            .cityCategory(UPDATED_CITY_CATEGORY)
            .hraPercent(UPDATED_HRA_PERCENT)
            .hraValue(UPDATED_HRA_VALUE)
            .travelAllowance(UPDATED_TRAVEL_ALLOWANCE)
            .cla(UPDATED_CLA)
            .medical(UPDATED_MEDICAL)
            .grossSalary(UPDATED_GROSS_SALARY)
            .profTax(UPDATED_PROF_TAX)
            .insurance(UPDATED_INSURANCE)
            .gpf(UPDATED_GPF)
            .cpg(UPDATED_CPG)
            .incometax(UPDATED_INCOMETAX)
            .totalDeduction(UPDATED_TOTAL_DEDUCTION)
            .netSalary(UPDATED_NET_SALARY);
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO = monthlySalaryHistoryMapper.toDto(updatedMonthlySalaryHistory);

        restMonthlySalaryHistoryMockMvc.perform(put("/api/monthly-salary-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the MonthlySalaryHistory in the database
        List<MonthlySalaryHistory> monthlySalaryHistoryList = monthlySalaryHistoryRepository.findAll();
        assertThat(monthlySalaryHistoryList).hasSize(databaseSizeBeforeUpdate);
        MonthlySalaryHistory testMonthlySalaryHistory = monthlySalaryHistoryList.get(monthlySalaryHistoryList.size() - 1);
        assertThat(testMonthlySalaryHistory.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMonthlySalaryHistory.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testMonthlySalaryHistory.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testMonthlySalaryHistory.getBasicPay()).isEqualTo(UPDATED_BASIC_PAY);
        assertThat(testMonthlySalaryHistory.getGradePay()).isEqualTo(UPDATED_GRADE_PAY);
        assertThat(testMonthlySalaryHistory.getBasicTotal()).isEqualTo(UPDATED_BASIC_TOTAL);
        assertThat(testMonthlySalaryHistory.getDaPercent()).isEqualTo(UPDATED_DA_PERCENT);
        assertThat(testMonthlySalaryHistory.getDaValue()).isEqualTo(UPDATED_DA_VALUE);
        assertThat(testMonthlySalaryHistory.getTotalSalary()).isEqualTo(UPDATED_TOTAL_SALARY);
        assertThat(testMonthlySalaryHistory.getCityCategory()).isEqualTo(UPDATED_CITY_CATEGORY);
        assertThat(testMonthlySalaryHistory.getHraPercent()).isEqualTo(UPDATED_HRA_PERCENT);
        assertThat(testMonthlySalaryHistory.getHraValue()).isEqualTo(UPDATED_HRA_VALUE);
        assertThat(testMonthlySalaryHistory.getTravelAllowance()).isEqualTo(UPDATED_TRAVEL_ALLOWANCE);
        assertThat(testMonthlySalaryHistory.getCla()).isEqualTo(UPDATED_CLA);
        assertThat(testMonthlySalaryHistory.getMedical()).isEqualTo(UPDATED_MEDICAL);
        assertThat(testMonthlySalaryHistory.getGrossSalary()).isEqualTo(UPDATED_GROSS_SALARY);
        assertThat(testMonthlySalaryHistory.getProfTax()).isEqualTo(UPDATED_PROF_TAX);
        assertThat(testMonthlySalaryHistory.getInsurance()).isEqualTo(UPDATED_INSURANCE);
        assertThat(testMonthlySalaryHistory.getGpf()).isEqualTo(UPDATED_GPF);
        assertThat(testMonthlySalaryHistory.getCpg()).isEqualTo(UPDATED_CPG);
        assertThat(testMonthlySalaryHistory.getIncometax()).isEqualTo(UPDATED_INCOMETAX);
        assertThat(testMonthlySalaryHistory.getTotalDeduction()).isEqualTo(UPDATED_TOTAL_DEDUCTION);
        assertThat(testMonthlySalaryHistory.getNetSalary()).isEqualTo(UPDATED_NET_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlySalaryHistory() throws Exception {
        int databaseSizeBeforeUpdate = monthlySalaryHistoryRepository.findAll().size();

        // Create the MonthlySalaryHistory
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO = monthlySalaryHistoryMapper.toDto(monthlySalaryHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlySalaryHistoryMockMvc.perform(put("/api/monthly-salary-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalaryHistory in the database
        List<MonthlySalaryHistory> monthlySalaryHistoryList = monthlySalaryHistoryRepository.findAll();
        assertThat(monthlySalaryHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonthlySalaryHistory() throws Exception {
        // Initialize the database
        monthlySalaryHistoryRepository.saveAndFlush(monthlySalaryHistory);

        int databaseSizeBeforeDelete = monthlySalaryHistoryRepository.findAll().size();

        // Get the monthlySalaryHistory
        restMonthlySalaryHistoryMockMvc.perform(delete("/api/monthly-salary-histories/{id}", monthlySalaryHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MonthlySalaryHistory> monthlySalaryHistoryList = monthlySalaryHistoryRepository.findAll();
        assertThat(monthlySalaryHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlySalaryHistory.class);
        MonthlySalaryHistory monthlySalaryHistory1 = new MonthlySalaryHistory();
        monthlySalaryHistory1.setId(1L);
        MonthlySalaryHistory monthlySalaryHistory2 = new MonthlySalaryHistory();
        monthlySalaryHistory2.setId(monthlySalaryHistory1.getId());
        assertThat(monthlySalaryHistory1).isEqualTo(monthlySalaryHistory2);
        monthlySalaryHistory2.setId(2L);
        assertThat(monthlySalaryHistory1).isNotEqualTo(monthlySalaryHistory2);
        monthlySalaryHistory1.setId(null);
        assertThat(monthlySalaryHistory1).isNotEqualTo(monthlySalaryHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlySalaryHistoryDTO.class);
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO1 = new MonthlySalaryHistoryDTO();
        monthlySalaryHistoryDTO1.setId(1L);
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO2 = new MonthlySalaryHistoryDTO();
        assertThat(monthlySalaryHistoryDTO1).isNotEqualTo(monthlySalaryHistoryDTO2);
        monthlySalaryHistoryDTO2.setId(monthlySalaryHistoryDTO1.getId());
        assertThat(monthlySalaryHistoryDTO1).isEqualTo(monthlySalaryHistoryDTO2);
        monthlySalaryHistoryDTO2.setId(2L);
        assertThat(monthlySalaryHistoryDTO1).isNotEqualTo(monthlySalaryHistoryDTO2);
        monthlySalaryHistoryDTO1.setId(null);
        assertThat(monthlySalaryHistoryDTO1).isNotEqualTo(monthlySalaryHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(monthlySalaryHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(monthlySalaryHistoryMapper.fromId(null)).isNull();
    }
}
