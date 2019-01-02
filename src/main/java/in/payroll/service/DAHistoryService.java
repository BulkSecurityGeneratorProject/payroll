package in.payroll.service;

import in.payroll.service.dto.DAHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DAHistory.
 */
public interface DAHistoryService {

    /**
     * Save a dAHistory.
     *
     * @param dAHistoryDTO the entity to save
     * @return the persisted entity
     */
    DAHistoryDTO save(DAHistoryDTO dAHistoryDTO);

    /**
     * Get all the dAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DAHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dAHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DAHistoryDTO> findOne(Long id);

    /**
     * Get the "" dAHistory.
     *
     * @param  the  of the entity
     * @return the entity
     */
    Optional<DAHistoryDTO> findOneByDate();

    /**
     * Delete the "id" dAHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
