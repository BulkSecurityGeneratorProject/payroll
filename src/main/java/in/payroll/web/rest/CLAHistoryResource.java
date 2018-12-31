package in.payroll.web.rest;

import com.codahale.metrics.annotation.Timed;
import in.payroll.service.CLAHistoryService;
import in.payroll.web.rest.errors.BadRequestAlertException;
import in.payroll.web.rest.util.HeaderUtil;
import in.payroll.web.rest.util.PaginationUtil;
import in.payroll.service.dto.CLAHistoryDTO;
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
 * REST controller for managing CLAHistory.
 */
@RestController
@RequestMapping("/api")
public class CLAHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CLAHistoryResource.class);

    private static final String ENTITY_NAME = "cLAHistory";

    private final CLAHistoryService cLAHistoryService;

    public CLAHistoryResource(CLAHistoryService cLAHistoryService) {
        this.cLAHistoryService = cLAHistoryService;
    }

    /**
     * POST  /cla-histories : Create a new cLAHistory.
     *
     * @param cLAHistoryDTO the cLAHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cLAHistoryDTO, or with status 400 (Bad Request) if the cLAHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cla-histories")
    @Timed
    public ResponseEntity<CLAHistoryDTO> createCLAHistory(@RequestBody CLAHistoryDTO cLAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save CLAHistory : {}", cLAHistoryDTO);
        if (cLAHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cLAHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CLAHistoryDTO result = cLAHistoryService.save(cLAHistoryDTO);
        return ResponseEntity.created(new URI("/api/cla-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cla-histories : Updates an existing cLAHistory.
     *
     * @param cLAHistoryDTO the cLAHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cLAHistoryDTO,
     * or with status 400 (Bad Request) if the cLAHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the cLAHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cla-histories")
    @Timed
    public ResponseEntity<CLAHistoryDTO> updateCLAHistory(@RequestBody CLAHistoryDTO cLAHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update CLAHistory : {}", cLAHistoryDTO);
        if (cLAHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CLAHistoryDTO result = cLAHistoryService.save(cLAHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cLAHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cla-histories : get all the cLAHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cLAHistories in body
     */
    @GetMapping("/cla-histories")
    @Timed
    public ResponseEntity<List<CLAHistoryDTO>> getAllCLAHistories(Pageable pageable) {
        log.debug("REST request to get a page of CLAHistories");
        Page<CLAHistoryDTO> page = cLAHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cla-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cla-histories/:id : get the "id" cLAHistory.
     *
     * @param id the id of the cLAHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cLAHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cla-histories/{id}")
    @Timed
    public ResponseEntity<CLAHistoryDTO> getCLAHistory(@PathVariable Long id) {
        log.debug("REST request to get CLAHistory : {}", id);
        Optional<CLAHistoryDTO> cLAHistoryDTO = cLAHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cLAHistoryDTO);
    }

    /**
     * DELETE  /cla-histories/:id : delete the "id" cLAHistory.
     *
     * @param id the id of the cLAHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cla-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCLAHistory(@PathVariable Long id) {
        log.debug("REST request to delete CLAHistory : {}", id);
        cLAHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
