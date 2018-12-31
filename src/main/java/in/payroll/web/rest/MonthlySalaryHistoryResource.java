package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.MonthlySalaryHistoryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.MonthlySalaryHistoryDTO;
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

/**
 * REST controller for managing MonthlySalaryHistory.
 */
@RestController
@RequestMapping("/api")
public class MonthlySalaryHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryHistoryResource.class);

    private static final String ENTITY_NAME = "monthlySalaryHistory";

    private final MonthlySalaryHistoryService monthlySalaryHistoryService;

    public MonthlySalaryHistoryResource(MonthlySalaryHistoryService monthlySalaryHistoryService) {
        this.monthlySalaryHistoryService = monthlySalaryHistoryService;
    }

    /**
     * POST  /monthly-salary-histories : Create a new monthlySalaryHistory.
     *
     * @param monthlySalaryHistoryDTO the monthlySalaryHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monthlySalaryHistoryDTO, or with status 400 (Bad Request) if the monthlySalaryHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monthly-salary-histories")
    @Timed
    public ResponseEntity<MonthlySalaryHistoryDTO> createMonthlySalaryHistory(@RequestBody MonthlySalaryHistoryDTO monthlySalaryHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save MonthlySalaryHistory : {}", monthlySalaryHistoryDTO);
        if (monthlySalaryHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlySalaryHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlySalaryHistoryDTO result = monthlySalaryHistoryService.save(monthlySalaryHistoryDTO);
        return ResponseEntity.created(new URI("/api/monthly-salary-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monthly-salary-histories : Updates an existing monthlySalaryHistory.
     *
     * @param monthlySalaryHistoryDTO the monthlySalaryHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monthlySalaryHistoryDTO,
     * or with status 400 (Bad Request) if the monthlySalaryHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the monthlySalaryHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monthly-salary-histories")
    @Timed
    public ResponseEntity<MonthlySalaryHistoryDTO> updateMonthlySalaryHistory(@RequestBody MonthlySalaryHistoryDTO monthlySalaryHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update MonthlySalaryHistory : {}", monthlySalaryHistoryDTO);
        if (monthlySalaryHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonthlySalaryHistoryDTO result = monthlySalaryHistoryService.save(monthlySalaryHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monthlySalaryHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monthly-salary-histories : get all the monthlySalaryHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of monthlySalaryHistories in body
     */
    @GetMapping("/monthly-salary-histories")
    @Timed
    public ResponseEntity<List<MonthlySalaryHistoryDTO>> getAllMonthlySalaryHistories(Pageable pageable) {
        log.debug("REST request to get a page of MonthlySalaryHistories");
        Page<MonthlySalaryHistoryDTO> page = monthlySalaryHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/monthly-salary-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /monthly-salary-histories/:id : get the "id" monthlySalaryHistory.
     *
     * @param id the id of the monthlySalaryHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monthlySalaryHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monthly-salary-histories/{id}")
    @Timed
    public ResponseEntity<MonthlySalaryHistoryDTO> getMonthlySalaryHistory(@PathVariable Long id) {
        log.debug("REST request to get MonthlySalaryHistory : {}", id);
        Optional<MonthlySalaryHistoryDTO> monthlySalaryHistoryDTO = monthlySalaryHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlySalaryHistoryDTO);
    }

    /**
     * DELETE  /monthly-salary-histories/:id : delete the "id" monthlySalaryHistory.
     *
     * @param id the id of the monthlySalaryHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monthly-salary-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonthlySalaryHistory(@PathVariable Long id) {
        log.debug("REST request to delete MonthlySalaryHistory : {}", id);
        monthlySalaryHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
