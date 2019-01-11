package in.payroll.service.impl;

import in.payroll.service.MedicalHistoryService;
import in.payroll.domain.MedicalHistory;
import in.payroll.repository.MedicalHistoryRepository;
import in.payroll.service.dto.MedicalHistoryDTO;
import in.payroll.service.mapper.MedicalHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MedicalHistory.
 */
@Service
@Transactional
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryServiceImpl.class);

    private final MedicalHistoryRepository medicalHistoryRepository;

    private final MedicalHistoryMapper medicalHistoryMapper;

    public MedicalHistoryServiceImpl(MedicalHistoryRepository medicalHistoryRepository, MedicalHistoryMapper medicalHistoryMapper) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicalHistoryMapper = medicalHistoryMapper;
    }

    /**
     * Save a medicalHistory.
     *
     * @param medicalHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicalHistoryDTO save(MedicalHistoryDTO medicalHistoryDTO) {
        log.debug("Request to save MedicalHistory : {}", medicalHistoryDTO);

        MedicalHistory medicalHistory = medicalHistoryMapper.toEntity(medicalHistoryDTO);
        medicalHistory = medicalHistoryRepository.save(medicalHistory);
        return medicalHistoryMapper.toDto(medicalHistory);
    }

    /**
     * Get all the medicalHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalHistories");
        return medicalHistoryRepository.findAll(pageable)
            .map(medicalHistoryMapper::toDto);
    }


    /**
     * Get one medicalHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalHistoryDTO> findOne(Long id) {
        log.debug("Request to get MedicalHistory : {}", id);
        return medicalHistoryRepository.findById(id)
            .map(medicalHistoryMapper::toDto);
    }

    /**
     * Get the medicalHistory.
     *
     * @return the entity
     */
    @Override
    public Optional<MedicalHistoryDTO> findOneByDate() {
        log.debug("Request to get MedicalHistory : {}");
        return medicalHistoryRepository.findTopByOrderByDateDesc()
            .map(medicalHistoryMapper::toDto);
    }

    /**
     * Delete the medicalHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalHistory : {}", id);
        medicalHistoryRepository.deleteById(id);
    }
}
