package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.MedicalHistoryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.MedicalHistoryDTO;
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
 * REST controller for managing MedicalHistory.
 */
@RestController
@RequestMapping("/api")
public class MedicalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryResource.class);

    private static final String ENTITY_NAME = "medicalHistory";

    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryResource(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /**
     * POST  /medical-histories : Create a new medicalHistory.
     *
     * @param medicalHistoryDTO the medicalHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalHistoryDTO, or with status 400 (Bad Request) if the medicalHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-histories")
    @Timed
    public ResponseEntity<MedicalHistoryDTO> createMedicalHistory(@RequestBody MedicalHistoryDTO medicalHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalHistory : {}", medicalHistoryDTO);
        if (medicalHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalHistoryDTO result = medicalHistoryService.save(medicalHistoryDTO);
        return ResponseEntity.created(new URI("/api/medical-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-histories : Updates an existing medicalHistory.
     *
     * @param medicalHistoryDTO the medicalHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalHistoryDTO,
     * or with status 400 (Bad Request) if the medicalHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-histories")
    @Timed
    public ResponseEntity<MedicalHistoryDTO> updateMedicalHistory(@RequestBody MedicalHistoryDTO medicalHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalHistory : {}", medicalHistoryDTO);
        if (medicalHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalHistoryDTO result = medicalHistoryService.save(medicalHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-histories : get all the medicalHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalHistories in body
     */
    @GetMapping("/medical-histories")
    @Timed
    public ResponseEntity<List<MedicalHistoryDTO>> getAllMedicalHistories(Pageable pageable) {
        log.debug("REST request to get a page of MedicalHistories");
        Page<MedicalHistoryDTO> page = medicalHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medical-histories/:id : get the "id" medicalHistory.
     *
     * @param id the id of the medicalHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-histories/{id}")
    @Timed
    public ResponseEntity<MedicalHistoryDTO> getMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistory : {}", id);
        Optional<MedicalHistoryDTO> medicalHistoryDTO = medicalHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalHistoryDTO);
    }

    /**
     * DELETE  /medical-histories/:id : delete the "id" medicalHistory.
     *
     * @param id the id of the medicalHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to delete MedicalHistory : {}", id);
        medicalHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
