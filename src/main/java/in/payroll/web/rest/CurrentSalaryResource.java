package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.*;
import in.payroll.service.dto.*;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CurrentSalary.
 */
@RestController
@RequestMapping("/api")
public class CurrentSalaryResource {

    private final Logger log = LoggerFactory.getLogger(CurrentSalaryResource.class);

    private static final String ENTITY_NAME = "currentSalary";

    private final CurrentSalaryService currentSalaryService;

    public CurrentSalaryResource(CurrentSalaryService currentSalaryService) {
        this.currentSalaryService = currentSalaryService;
    }

    @Autowired
    private MonthlySalaryHistoryService monthlySalaryHistoryService;

    @Autowired
    private TAHistoryService tAHistoryService;

    @Autowired
    private CLAHistoryService claHistoryService;

    @Autowired
    private DAHistoryService daHistoryService;

    @Autowired
    private HRAHistoryService hraHistoryService;

    @Autowired
    private MedicalHistoryService medicalHistoryService;


    /**
     * POST  /current-salaries : Create a new currentSalary.
     *
     * @param currentSalaryDTO the currentSalaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new currentSalaryDTO, or with status 400 (Bad Request) if the currentSalary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/current-salaries")
    @Timed
    public ResponseEntity<CurrentSalaryDTO> createCurrentSalary(@RequestBody CurrentSalaryDTO currentSalaryDTO) throws URISyntaxException {
        log.debug("REST request to save CurrentSalary : {}", currentSalaryDTO);
        if (currentSalaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new currentSalary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentSalaryDTO result = currentSalaryService.save(currentSalaryDTO);

        return ResponseEntity.created(new URI("/api/current-salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /current-salaries : Updates an existing currentSalary.
     *
     * @param currentSalaryDTO the currentSalaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated currentSalaryDTO,
     * or with status 400 (Bad Request) if the currentSalaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the currentSalaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/current-salaries")
    @Timed
    public ResponseEntity<CurrentSalaryDTO> updateCurrentSalary(@RequestBody CurrentSalaryDTO currentSalaryDTO) throws URISyntaxException {
        log.debug("REST request to update CurrentSalary : {}", currentSalaryDTO);
        if (currentSalaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrentSalaryDTO result = currentSalaryService.save(currentSalaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, currentSalaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /current-salaries : get all the currentSalaries.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of currentSalaries in body
     */
    @GetMapping("/current-salaries")
    @Timed
    public ResponseEntity<List<CurrentSalaryDTO>> getAllCurrentSalaries(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("faculty-is-null".equals(filter)) {
            log.debug("REST request to get all CurrentSalarys where faculty is null");
            return new ResponseEntity<>(currentSalaryService.findAllWhereFacultyIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of CurrentSalaries");
        Page<CurrentSalaryDTO> page = currentSalaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/current-salaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /current-salaries/:id : get the "id" currentSalary.
     *
     * @param id the id of the currentSalaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the currentSalaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/current-salaries/{id}")
    @Timed
    public ResponseEntity<CurrentSalaryDTO> getCurrentSalary(@PathVariable Long id) {
        log.debug("REST request to get CurrentSalary : {}", id);
        Optional<CurrentSalaryDTO> currentSalaryDTO = currentSalaryService.findOne(id);
        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO =  monthlySalary(currentSalaryDTO.get());

        log.debug("?????????????????????????????????????????????????");
        log.info(monthlySalaryHistoryDTO.toString());
        monthlySalaryHistoryService.save(monthlySalaryHistoryDTO);

        return ResponseUtil.wrapOrNotFound(currentSalaryDTO);
    }

    private MonthlySalaryHistoryDTO monthlySalary(CurrentSalaryDTO currentSalaryDTO) {
        LocalDate ld = LocalDate.now();
        Optional<MonthlySalaryHistoryDTO> monthlySalary =
            monthlySalaryHistoryService.findOneByYearAndByMonth(ld.getYear(),ld.getMonthValue());

        MonthlySalaryHistoryDTO monthlySalaryHistoryDTO;
        if(monthlySalary.isPresent())
            monthlySalaryHistoryDTO = monthlySalary.get();
        else
            monthlySalaryHistoryDTO = new MonthlySalaryHistoryDTO();

        TAHistoryDTO ta = tAHistoryService.findOneByCityCategory(currentSalaryDTO.getCityCategory()).get();
        HRAHistoryDTO hra = hraHistoryService.findOneByCityCategory(currentSalaryDTO.getCityCategory()).get();
        CLAHistoryDTO cla = claHistoryService.findOneByCityCategory(currentSalaryDTO.getCityCategory()).get();
        DAHistoryDTO da = daHistoryService.findOneByDate().get();
        MedicalHistoryDTO medical = medicalHistoryService.findOneByDate().get();

        monthlySalaryHistoryDTO.setYear(ld.getYear());
        monthlySalaryHistoryDTO.setMonth(ld.getMonthValue());
        monthlySalaryHistoryDTO.setBasicPay(currentSalaryDTO.getBasicPay());
        monthlySalaryHistoryDTO.setGradePay(currentSalaryDTO.getGradePay());
        monthlySalaryHistoryDTO.setBasicTotal(monthlySalaryHistoryDTO.getBasicPay() + monthlySalaryHistoryDTO.getGradePay());
        monthlySalaryHistoryDTO.setOfficeName(currentSalaryDTO.getOfficeOfficeName());
        monthlySalaryHistoryDTO.setDaPercent(da.getCurrentValue());
        monthlySalaryHistoryDTO.setDaValue(( monthlySalaryHistoryDTO.getBasicTotal() * da.getCurrentValue() ) / 100);
        monthlySalaryHistoryDTO.setTotalSalary(monthlySalaryHistoryDTO.getBasicTotal() + monthlySalaryHistoryDTO.getDaValue());
        monthlySalaryHistoryDTO.setCityCategory(currentSalaryDTO.getCityCategory());
        monthlySalaryHistoryDTO.setHraPercent(hra.getCurrentValue());
        monthlySalaryHistoryDTO.setHraValue(( monthlySalaryHistoryDTO.getBasicTotal() * hra.getCurrentValue() ) / 100);
        monthlySalaryHistoryDTO.setTravelAllowance(ta.getCurrentValue());
        monthlySalaryHistoryDTO.setCla(cla.getCurrentValue());
        monthlySalaryHistoryDTO.setMedical(medical.getCurrentValue());
        monthlySalaryHistoryDTO.setGrossSalary(monthlySalaryHistoryDTO.getTotalSalary()
            + monthlySalaryHistoryDTO.getHraValue()
            + monthlySalaryHistoryDTO.getTravelAllowance()
            + monthlySalaryHistoryDTO.getCla()
            + monthlySalaryHistoryDTO.getMedical() );
        monthlySalaryHistoryDTO.setGpf(0L);
        monthlySalaryHistoryDTO.setCpg(( monthlySalaryHistoryDTO.getTotalSalary() * 10 ) / 100 ) ;
        monthlySalaryHistoryDTO.setProfTax(200L);
        monthlySalaryHistoryDTO.setInsurance(300L);
        monthlySalaryHistoryDTO.setIncometax(1000L);
        monthlySalaryHistoryDTO.setTotalDeduction( monthlySalaryHistoryDTO.getCpg()
            + monthlySalaryHistoryDTO.getGpf()
            + monthlySalaryHistoryDTO.getProfTax()
            + monthlySalaryHistoryDTO.getInsurance()
            + monthlySalaryHistoryDTO.getIncometax());
        monthlySalaryHistoryDTO.setNetSalary(monthlySalaryHistoryDTO.getGrossSalary() - monthlySalaryHistoryDTO.getTotalDeduction());


        return monthlySalaryHistoryDTO;
    }


    /**
     * DELETE  /current-salaries/:id : delete the "id" currentSalary.
     *
     * @param id the id of the currentSalaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/current-salaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurrentSalary(@PathVariable Long id) {
        log.debug("REST request to delete CurrentSalary : {}", id);
        currentSalaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
