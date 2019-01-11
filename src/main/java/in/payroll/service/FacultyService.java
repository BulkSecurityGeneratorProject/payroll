package in.payroll.service;

import in.payroll.service.dto.FacultyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Faculty.
 */
public interface FacultyService {

    /**
     * Save a faculty.
     *
     * @param facultyDTO the entity to save
     * @return the persisted entity
     */
    FacultyDTO save(FacultyDTO facultyDTO);

    /**
     * Get all the faculties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FacultyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" faculty.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FacultyDTO> findOne(Long id);

    /**
     * Delete the "id" faculty.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
