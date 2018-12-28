package in.payroll.service.impl;

import in.payroll.service.DAHistoryService;
import in.payroll.domain.DAHistory;
import in.payroll.repository.DAHistoryRepository;
import in.payroll.service.dto.DAHistoryDTO;
import in.payroll.service.mapper.DAHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DAHistory.
 */
@Service
@Transactional
public class DAHistoryServiceImpl implements DAHistoryService {

    private final Logger log = LoggerFactory.getLogger(DAHistoryServiceImpl.class);

    private final DAHistoryRepository dAHistoryRepository;

    private final DAHistoryMapper dAHistoryMapper;

    public DAHistoryServiceImpl(DAHistoryRepository dAHistoryRepository, DAHistoryMapper dAHistoryMapper) {
        this.dAHistoryRepository = dAHistoryRepository;
        this.dAHistoryMapper = dAHistoryMapper;
    }

    /**
     * Save a dAHistory.
     *
     * @param dAHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DAHistoryDTO save(DAHistoryDTO dAHistoryDTO) {
        log.debug("Request to save DAHistory : {}", dAHistoryDTO);

        DAHistory dAHistory = dAHistoryMapper.toEntity(dAHistoryDTO);
        dAHistory = dAHistoryRepository.save(dAHistory);
        return dAHistoryMapper.toDto(dAHistory);
    }

    /**
     * Get all the dAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DAHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DAHistories");
        return dAHistoryRepository.findAll(pageable)
            .map(dAHistoryMapper::toDto);
    }


    /**
     * Get one dAHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DAHistoryDTO> findOne(Long id) {
        log.debug("Request to get DAHistory : {}", id);
        return dAHistoryRepository.findById(id)
            .map(dAHistoryMapper::toDto);
    }

    /**
     * Delete the dAHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DAHistory : {}", id);
        dAHistoryRepository.deleteById(id);
    }
}
