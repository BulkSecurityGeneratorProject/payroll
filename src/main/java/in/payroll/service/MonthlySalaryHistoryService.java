package in.payroll.service;

import in.payroll.service.dto.MonthlySalaryHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MonthlySalaryHistory.
 */
public interface MonthlySalaryHistoryService {

    /**
     * Save a monthlySalaryHistory.
     *
     * @param monthlySalaryHistoryDTO the entity to save
     * @return the persisted entity
     */
    MonthlySalaryHistoryDTO save(MonthlySalaryHistoryDTO monthlySalaryHistoryDTO);

    /**
     * Get all the monthlySalaryHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MonthlySalaryHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" monthlySalaryHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MonthlySalaryHistoryDTO> findOne(Long id);

    /**
     * Get the "year,month" monthlySalaryHistory.
     *
     * @param year,month the month of the entity
     * @return the entity
     */
    Optional<MonthlySalaryHistoryDTO> findOneByYearAndByMonth(Integer year,Integer month);

    /**
     * Delete the "id" monthlySalaryHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
