package in.payroll.service.impl;

import in.payroll.domain.CurrentSalary;
import in.payroll.repository.CurrentSalaryRepository;
import in.payroll.service.CurrentSalaryService;
import in.payroll.service.dto.CurrentSalaryDTO;
import in.payroll.service.mapper.CurrentSalaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing CurrentSalary.
 */
@Service
@Transactional
public class CurrentSalaryServiceImpl implements CurrentSalaryService {

    private final Logger log = LoggerFactory.getLogger(CurrentSalaryServiceImpl.class);

    private final CurrentSalaryRepository currentSalaryRepository;

    private final CurrentSalaryMapper currentSalaryMapper;

    public CurrentSalaryServiceImpl(CurrentSalaryRepository currentSalaryRepository, CurrentSalaryMapper currentSalaryMapper) {
        this.currentSalaryRepository = currentSalaryRepository;
        this.currentSalaryMapper = currentSalaryMapper;
    }

    /**
     * Save a currentSalary.
     *
     * @param currentSalaryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CurrentSalaryDTO save(CurrentSalaryDTO currentSalaryDTO) {
        log.debug("Request to save CurrentSalary : {}", currentSalaryDTO);

        CurrentSalary currentSalary = currentSalaryMapper.toEntity(currentSalaryDTO);
        currentSalary = currentSalaryRepository.save(currentSalary);

     // SAVE SALARY HISTORY




        return currentSalaryMapper.toDto(currentSalary);
    }

    /**
     * Get all the currentSalaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurrentSalaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurrentSalaries");
        return currentSalaryRepository.findAll(pageable)
            .map(currentSalaryMapper::toDto);
    }



    /**
     *  get all the currentSalaries where Faculty is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CurrentSalaryDTO> findAllWhereFacultyIsNull() {
        log.debug("Request to get all currentSalaries where Faculty is null");
        return StreamSupport
            .stream(currentSalaryRepository.findAll().spliterator(), false)
            .filter(currentSalary -> currentSalary.getFaculty() == null)
            .map(currentSalaryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one currentSalary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurrentSalaryDTO> findOne(Long id) {
        log.debug("Request to get CurrentSalary : {}", id);
        return currentSalaryRepository.findById(id)
            .map(currentSalaryMapper::toDto);
    }

    /**
     * Delete the currentSalary by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrentSalary : {}", id);
        currentSalaryRepository.deleteById(id);
    }
}
