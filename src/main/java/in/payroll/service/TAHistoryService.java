package in.payroll.service;

import in.payroll.service.dto.TAHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TAHistory.
 */
public interface TAHistoryService {

    /**
     * Save a tAHistory.
     *
     * @param tAHistoryDTO the entity to save
     * @return the persisted entity
     */
    TAHistoryDTO save(TAHistoryDTO tAHistoryDTO);

    /**
     * Get all the tAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TAHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tAHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TAHistoryDTO> findOne(Long id);


    /**
     * Get the "city category" tAHistory.
     *
     * @param cityCategory the cityCategory of the entity
     * @return the entity
     * List<TAHistoryDTO>
     */
    TAHistoryDTO findOneByCityCategory(String cityCategory);

    /**
     * Delete the "id" tAHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
