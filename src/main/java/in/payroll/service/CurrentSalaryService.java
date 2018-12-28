package in.payroll.service;

import in.payroll.service.dto.CurrentSalaryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CurrentSalary.
 */
public interface CurrentSalaryService {

    /**
     * Save a currentSalary.
     *
     * @param currentSalaryDTO the entity to save
     * @return the persisted entity
     */
    CurrentSalaryDTO save(CurrentSalaryDTO currentSalaryDTO);

    /**
     * Get all the currentSalaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CurrentSalaryDTO> findAll(Pageable pageable);
    /**
     * Get all the CurrentSalaryDTO where Faculty is null.
     *
     * @return the list of entities
     */
    List<CurrentSalaryDTO> findAllWhereFacultyIsNull();


    /**
     * Get the "id" currentSalary.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CurrentSalaryDTO> findOne(Long id);

    /**
     * Delete the "id" currentSalary.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
