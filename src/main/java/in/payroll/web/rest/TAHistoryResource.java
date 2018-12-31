package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.TAHistoryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.TAHistoryDTO;
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
 * REST controller for managing TAHistory.
 */
@RestController
@RequestMapping("/api")
public class TAHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TAHistoryResource.class);

    private static final String ENTITY_NAME = "tAHistory";

    private final TAHistoryService tAHistoryService;

    public TAHistoryResource(TAHistoryService tAHistoryService) {
        this.tAHistoryService = tAHistoryService;
    }

    /**
     * POST  /ta-histories : Create a new tAHistory.
     *
     * @param tAHistoryDTO the tAHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tAHistoryDTO, or with status 400 (Bad Request) if the tAHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ta-histories")
    @Timed
    public ResponseEntity<TAHistoryDTO> createTAHistory(@RequestBody TAHistoryDTO tAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save TAHistory : {}", tAHistoryDTO);
        if (tAHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new tAHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TAHistoryDTO result = tAHistoryService.save(tAHistoryDTO);
        return ResponseEntity.created(new URI("/api/ta-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ta-histories : Updates an existing tAHistory.
     *
     * @param tAHistoryDTO the tAHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tAHistoryDTO,
     * or with status 400 (Bad Request) if the tAHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the tAHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ta-histories")
    @Timed
    public ResponseEntity<TAHistoryDTO> updateTAHistory(@RequestBody TAHistoryDTO tAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update TAHistory : {}", tAHistoryDTO);
        if (tAHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TAHistoryDTO result = tAHistoryService.save(tAHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tAHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ta-histories : get all the tAHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tAHistories in body
     */
    @GetMapping("/ta-histories")
    @Timed
    public ResponseEntity<List<TAHistoryDTO>> getAllTAHistories(Pageable pageable) {
        log.debug("REST request to get a page of TAHistories");
        Page<TAHistoryDTO> page = tAHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ta-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ta-histories/:id : get the "id" tAHistory.
     *
     * @param id the id of the tAHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tAHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ta-histories/{id}")
    @Timed
    public ResponseEntity<TAHistoryDTO> getTAHistory(@PathVariable Long id) {
        log.debug("REST request to get TAHistory : {}", id);
        Optional<TAHistoryDTO> tAHistoryDTO = tAHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tAHistoryDTO);
    }

    /**
     * DELETE  /ta-histories/:id : delete the "id" tAHistory.
     *
     * @param id the id of the tAHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ta-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteTAHistory(@PathVariable Long id) {
        log.debug("REST request to delete TAHistory : {}", id);
        tAHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
