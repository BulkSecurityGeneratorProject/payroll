package in.payroll.service;

import in.payroll.service.dto.HRAHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HRAHistory.
 */
public interface HRAHistoryService {

    /**
     * Save a hRAHistory.
     *
     * @param hRAHistoryDTO the entity to save
     * @return the persisted entity
     */
    HRAHistoryDTO save(HRAHistoryDTO hRAHistoryDTO);

    /**
     * Get all the hRAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HRAHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" hRAHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HRAHistoryDTO> findOne(Long id);

    /**
     * Get the "cityCategory" hRAHistory.
     *
     * @param cityCategory the cityCategory of the entity
     * @return the entity
     */
    Optional<HRAHistoryDTO> findOneByCityCategory(String cityCategory);

    /**
     * Delete the "id" hRAHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
