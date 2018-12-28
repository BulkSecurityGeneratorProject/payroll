package in.payroll.service;

import in.payroll.service.dto.OfficeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Office.
 */
public interface OfficeService {

    /**
     * Save a office.
     *
     * @param officeDTO the entity to save
     * @return the persisted entity
     */
    OfficeDTO save(OfficeDTO officeDTO);

    /**
     * Get all the offices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfficeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" office.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OfficeDTO> findOne(Long id);

    /**
     * Delete the "id" office.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
