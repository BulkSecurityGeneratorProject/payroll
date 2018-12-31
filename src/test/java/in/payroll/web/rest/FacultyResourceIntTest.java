package in.payroll.web.rest;

import in.payroll.PayrollApp;

import in.payroll.domain.Faculty;
import in.payroll.repository.FacultyRepository;
import in.payroll.service.FacultyService;
import in.payroll.service.dto.FacultyDTO;
import in.payroll.service.mapper.FacultyMapper;
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
 * Test class for the FacultyResource REST controller.
 *
 * @see FacultyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayrollApp.class)
public class FacultyResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyMapper facultyMapper;

    @Autowired
    private FacultyService facultyService;

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

    private MockMvc restFacultyMockMvc;

    private Faculty faculty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacultyResource facultyResource = new FacultyResource(facultyService);
        this.restFacultyMockMvc = MockMvcBuilders.standaloneSetup(facultyResource)
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
    public static Faculty createEntity(EntityManager em) {
        Faculty faculty = new Faculty()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .designation(DEFAULT_DESIGNATION);
        return faculty;
    }

    @Before
    public void initTest() {
        faculty = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaculty() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();

        // Create the Faculty
        FacultyDTO facultyDTO = facultyMapper.toDto(faculty);
        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultyDTO)))
            .andExpect(status().isCreated());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeCreate + 1);
        Faculty testFaculty = facultyList.get(facultyList.size() - 1);
        assertThat(testFaculty.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testFaculty.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFaculty.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    public void createFacultyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();

        // Create the Faculty with an existing ID
        faculty.setId(1L);
        FacultyDTO facultyDTO = facultyMapper.toDto(faculty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facultyRepository.findAll().size();
        // set the field null
        faculty.setFirstName(null);

        // Create the Faculty, which fails.
        FacultyDTO facultyDTO = facultyMapper.toDto(faculty);

        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultyDTO)))
            .andExpect(status().isBadRequest());

        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaculties() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get all the facultyList
        restFacultyMockMvc.perform(get("/api/faculties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faculty.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }
    
    @Test
    @Transactional
    public void getFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        // Get the faculty
        restFacultyMockMvc.perform(get("/api/faculties/{id}", faculty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faculty.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaculty() throws Exception {
        // Get the faculty
        restFacultyMockMvc.perform(get("/api/faculties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // Update the faculty
        Faculty updatedFaculty = facultyRepository.findById(faculty.getId()).get();
        // Disconnect from session so that the updates on updatedFaculty are not directly saved in db
        em.detach(updatedFaculty);
        updatedFaculty
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION);
        FacultyDTO facultyDTO = facultyMapper.toDto(updatedFaculty);

        restFacultyMockMvc.perform(put("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultyDTO)))
            .andExpect(status().isOk());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeUpdate);
        Faculty testFaculty = facultyList.get(facultyList.size() - 1);
        assertThat(testFaculty.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testFaculty.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFaculty.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingFaculty() throws Exception {
        int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // Create the Faculty
        FacultyDTO facultyDTO = facultyMapper.toDto(faculty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultyMockMvc.perform(put("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaculty() throws Exception {
        // Initialize the database
        facultyRepository.saveAndFlush(faculty);

        int databaseSizeBeforeDelete = facultyRepository.findAll().size();

        // Get the faculty
        restFacultyMockMvc.perform(delete("/api/faculties/{id}", faculty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Faculty.class);
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        Faculty faculty2 = new Faculty();
        faculty2.setId(faculty1.getId());
        assertThat(faculty1).isEqualTo(faculty2);
        faculty2.setId(2L);
        assertThat(faculty1).isNotEqualTo(faculty2);
        faculty1.setId(null);
        assertThat(faculty1).isNotEqualTo(faculty2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacultyDTO.class);
        FacultyDTO facultyDTO1 = new FacultyDTO();
        facultyDTO1.setId(1L);
        FacultyDTO facultyDTO2 = new FacultyDTO();
        assertThat(facultyDTO1).isNotEqualTo(facultyDTO2);
        facultyDTO2.setId(facultyDTO1.getId());
        assertThat(facultyDTO1).isEqualTo(facultyDTO2);
        facultyDTO2.setId(2L);
        assertThat(facultyDTO1).isNotEqualTo(facultyDTO2);
        facultyDTO1.setId(null);
        assertThat(facultyDTO1).isNotEqualTo(facultyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facultyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facultyMapper.fromId(null)).isNull();
    }
}
