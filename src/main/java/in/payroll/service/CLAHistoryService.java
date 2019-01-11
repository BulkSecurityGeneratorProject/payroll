package in.payroll.service;

import in.payroll.service.dto.CLAHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CLAHistory.
 */
public interface CLAHistoryService {

    /**
     * Save a cLAHistory.
     *
     * @param cLAHistoryDTO the entity to save
     * @return the persisted entity
     */
    CLAHistoryDTO save(CLAHistoryDTO cLAHistoryDTO);

    /**
     * Get all the cLAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CLAHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cLAHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CLAHistoryDTO> findOne(Long id);

    /**
     * Get the "cityCategory" cLAHistory.
     *
     * @param cityCategory the cityCategory of the entity
     * @return the entity
     */
    Optional<CLAHistoryDTO> findOneByCityCategory(String cityCategory);

    /**
     * Delete the "id" cLAHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
