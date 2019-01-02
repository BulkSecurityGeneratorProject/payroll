package in.payroll.service.impl;

import in.payroll.service.MonthlySalaryHistoryService;
import in.payroll.domain.MonthlySalaryHistory;
import in.payroll.repository.MonthlySalaryHistoryRepository;
import in.payroll.service.dto.MonthlySalaryHistoryDTO;
import in.payroll.service.mapper.MonthlySalaryHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MonthlySalaryHistory.
 */
@Service
@Transactional
public class MonthlySalaryHistoryServiceImpl implements MonthlySalaryHistoryService {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryHistoryServiceImpl.class);

    private final MonthlySalaryHistoryRepository monthlySalaryHistoryRepository;

    private final MonthlySalaryHistoryMapper monthlySalaryHistoryMapper;

    public MonthlySalaryHistoryServiceImpl(MonthlySalaryHistoryRepository monthlySalaryHistoryRepository, MonthlySalaryHistoryMapper monthlySalaryHistoryMapper) {
        this.monthlySalaryHistoryRepository = monthlySalaryHistoryRepository;
        this.monthlySalaryHistoryMapper = monthlySalaryHistoryMapper;
    }

    /**
     * Save a monthlySalaryHistory.
     *
     * @param monthlySalaryHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MonthlySalaryHistoryDTO save(MonthlySalaryHistoryDTO monthlySalaryHistoryDTO) {
        log.debug("Request to save MonthlySalaryHistory : {}", monthlySalaryHistoryDTO);

        MonthlySalaryHistory monthlySalaryHistory = monthlySalaryHistoryMapper.toEntity(monthlySalaryHistoryDTO);
        monthlySalaryHistory = monthlySalaryHistoryRepository.save(monthlySalaryHistory);
        return monthlySalaryHistoryMapper.toDto(monthlySalaryHistory);
    }

    /**
     * Get all the monthlySalaryHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MonthlySalaryHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MonthlySalaryHistories");
        return monthlySalaryHistoryRepository.findAll(pageable)
            .map(monthlySalaryHistoryMapper::toDto);
    }


    /**
     * Get one monthlySalaryHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MonthlySalaryHistoryDTO> findOne(Long id) {
        log.debug("Request to get MonthlySalaryHistory : {}", id);
        return monthlySalaryHistoryRepository.findById(id)
            .map(monthlySalaryHistoryMapper::toDto);
    }

    /**
     * Get one monthlySalaryHistory by year,month.
     *
     * @param year,month the year,month of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MonthlySalaryHistoryDTO> findOneByYearAndByMonth(Integer year,Integer month) {
        log.debug("Request to get MonthlySalaryHistory : {}", year);
        return monthlySalaryHistoryRepository.findOneByYearAndMonth(year,month)
            .map(monthlySalaryHistoryMapper::toDto);
    }



    /**
     * Delete the monthlySalaryHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MonthlySalaryHistory : {}", id);
        monthlySalaryHistoryRepository.deleteById(id);
    }
}
