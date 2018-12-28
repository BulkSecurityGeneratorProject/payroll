package in.payroll.service.impl;

import in.payroll.service.HRAHistoryService;
import in.payroll.domain.HRAHistory;
import in.payroll.repository.HRAHistoryRepository;
import in.payroll.service.dto.HRAHistoryDTO;
import in.payroll.service.mapper.HRAHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HRAHistory.
 */
@Service
@Transactional
public class HRAHistoryServiceImpl implements HRAHistoryService {

    private final Logger log = LoggerFactory.getLogger(HRAHistoryServiceImpl.class);

    private final HRAHistoryRepository hRAHistoryRepository;

    private final HRAHistoryMapper hRAHistoryMapper;

    public HRAHistoryServiceImpl(HRAHistoryRepository hRAHistoryRepository, HRAHistoryMapper hRAHistoryMapper) {
        this.hRAHistoryRepository = hRAHistoryRepository;
        this.hRAHistoryMapper = hRAHistoryMapper;
    }

    /**
     * Save a hRAHistory.
     *
     * @param hRAHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HRAHistoryDTO save(HRAHistoryDTO hRAHistoryDTO) {
        log.debug("Request to save HRAHistory : {}", hRAHistoryDTO);

        HRAHistory hRAHistory = hRAHistoryMapper.toEntity(hRAHistoryDTO);
        hRAHistory = hRAHistoryRepository.save(hRAHistory);
        return hRAHistoryMapper.toDto(hRAHistory);
    }

    /**
     * Get all the hRAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HRAHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HRAHistories");
        return hRAHistoryRepository.findAll(pageable)
            .map(hRAHistoryMapper::toDto);
    }


    /**
     * Get one hRAHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HRAHistoryDTO> findOne(Long id) {
        log.debug("Request to get HRAHistory : {}", id);
        return hRAHistoryRepository.findById(id)
            .map(hRAHistoryMapper::toDto);
    }

    /**
     * Delete the hRAHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HRAHistory : {}", id);
        hRAHistoryRepository.deleteById(id);
    }
}
