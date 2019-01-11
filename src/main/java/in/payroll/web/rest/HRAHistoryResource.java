package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.HRAHistoryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.HRAHistoryDTO;
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
 * REST controller for managing HRAHistory.
 */
@RestController
@RequestMapping("/api")
public class HRAHistoryResource {

    private final Logger log = LoggerFactory.getLogger(HRAHistoryResource.class);

    private static final String ENTITY_NAME = "hRAHistory";

    private final HRAHistoryService hRAHistoryService;

    public HRAHistoryResource(HRAHistoryService hRAHistoryService) {
        this.hRAHistoryService = hRAHistoryService;
    }

    /**
     * POST  /hra-histories : Create a new hRAHistory.
     *
     * @param hRAHistoryDTO the hRAHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hRAHistoryDTO, or with status 400 (Bad Request) if the hRAHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hra-histories")
    @Timed
    public ResponseEntity<HRAHistoryDTO> createHRAHistory(@RequestBody HRAHistoryDTO hRAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save HRAHistory : {}", hRAHistoryDTO);
        if (hRAHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new hRAHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HRAHistoryDTO result = hRAHistoryService.save(hRAHistoryDTO);
        return ResponseEntity.created(new URI("/api/hra-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hra-histories : Updates an existing hRAHistory.
     *
     * @param hRAHistoryDTO the hRAHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hRAHistoryDTO,
     * or with status 400 (Bad Request) if the hRAHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the hRAHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hra-histories")
    @Timed
    public ResponseEntity<HRAHistoryDTO> updateHRAHistory(@RequestBody HRAHistoryDTO hRAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update HRAHistory : {}", hRAHistoryDTO);
        if (hRAHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HRAHistoryDTO result = hRAHistoryService.save(hRAHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hRAHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hra-histories : get all the hRAHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hRAHistories in body
     */
    @GetMapping("/hra-histories")
    @Timed
    public ResponseEntity<List<HRAHistoryDTO>> getAllHRAHistories(Pageable pageable) {
        log.debug("REST request to get a page of HRAHistories");
        Page<HRAHistoryDTO> page = hRAHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hra-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /hra-histories/:id : get the "id" hRAHistory.
     *
     * @param id the id of the hRAHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hRAHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hra-histories/{id}")
    @Timed
    public ResponseEntity<HRAHistoryDTO> getHRAHistory(@PathVariable Long id) {
        log.debug("REST request to get HRAHistory : {}", id);
        Optional<HRAHistoryDTO> hRAHistoryDTO = hRAHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hRAHistoryDTO);
    }

    /**
     * DELETE  /hra-histories/:id : delete the "id" hRAHistory.
     *
     * @param id the id of the hRAHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hra-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteHRAHistory(@PathVariable Long id) {
        log.debug("REST request to delete HRAHistory : {}", id);
        hRAHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
