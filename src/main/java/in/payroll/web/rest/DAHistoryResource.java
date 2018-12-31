package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.DAHistoryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.DAHistoryDTO;
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
 * REST controller for managing DAHistory.
 */
@RestController
@RequestMapping("/api")
public class DAHistoryResource {

    private final Logger log = LoggerFactory.getLogger(DAHistoryResource.class);

    private static final String ENTITY_NAME = "dAHistory";

    private final DAHistoryService dAHistoryService;

    public DAHistoryResource(DAHistoryService dAHistoryService) {
        this.dAHistoryService = dAHistoryService;
    }

    /**
     * POST  /da-histories : Create a new dAHistory.
     *
     * @param dAHistoryDTO the dAHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dAHistoryDTO, or with status 400 (Bad Request) if the dAHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/da-histories")
    @Timed
    public ResponseEntity<DAHistoryDTO> createDAHistory(@RequestBody DAHistoryDTO dAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save DAHistory : {}", dAHistoryDTO);
        if (dAHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new dAHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DAHistoryDTO result = dAHistoryService.save(dAHistoryDTO);
        return ResponseEntity.created(new URI("/api/da-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /da-histories : Updates an existing dAHistory.
     *
     * @param dAHistoryDTO the dAHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dAHistoryDTO,
     * or with status 400 (Bad Request) if the dAHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the dAHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/da-histories")
    @Timed
    public ResponseEntity<DAHistoryDTO> updateDAHistory(@RequestBody DAHistoryDTO dAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update DAHistory : {}", dAHistoryDTO);
        if (dAHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DAHistoryDTO result = dAHistoryService.save(dAHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dAHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /da-histories : get all the dAHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dAHistories in body
     */
    @GetMapping("/da-histories")
    @Timed
    public ResponseEntity<List<DAHistoryDTO>> getAllDAHistories(Pageable pageable) {
        log.debug("REST request to get a page of DAHistories");
        Page<DAHistoryDTO> page = dAHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/da-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /da-histories/:id : get the "id" dAHistory.
     *
     * @param id the id of the dAHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dAHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/da-histories/{id}")
    @Timed
    public ResponseEntity<DAHistoryDTO> getDAHistory(@PathVariable Long id) {
        log.debug("REST request to get DAHistory : {}", id);
        Optional<DAHistoryDTO> dAHistoryDTO = dAHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dAHistoryDTO);
    }

    /**
     * DELETE  /da-histories/:id : delete the "id" dAHistory.
     *
     * @param id the id of the dAHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/da-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteDAHistory(@PathVariable Long id) {
        log.debug("REST request to delete DAHistory : {}", id);
        dAHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
