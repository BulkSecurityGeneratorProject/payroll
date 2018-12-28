package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.CurrentSalaryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.CurrentSalaryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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
        return ResponseUtil.wrapOrNotFound(currentSalaryDTO);
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
