package in.payroll.service.impl;

import in.payroll.domain.TAHistory;
import in.payroll.repository.TAHistoryRepository;
import in.payroll.service.TAHistoryService;
import in.payroll.service.dto.TAHistoryDTO;
import in.payroll.service.mapper.TAHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing TAHistory.
 */
@Service
@Transactional
public class TAHistoryServiceImpl implements TAHistoryService {

    private final Logger log = LoggerFactory.getLogger(TAHistoryServiceImpl.class);

    private final TAHistoryRepository tAHistoryRepository;

    private final TAHistoryMapper tAHistoryMapper;

    public TAHistoryServiceImpl(TAHistoryRepository tAHistoryRepository, TAHistoryMapper tAHistoryMapper) {
        this.tAHistoryRepository = tAHistoryRepository;
        this.tAHistoryMapper = tAHistoryMapper;
    }

    /**
     * Save a tAHistory.
     *
     * @param tAHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TAHistoryDTO save(TAHistoryDTO tAHistoryDTO) {
        log.debug("Request to save TAHistory : {}", tAHistoryDTO);

        TAHistory tAHistory = tAHistoryMapper.toEntity(tAHistoryDTO);
        tAHistory = tAHistoryRepository.save(tAHistory);
        return tAHistoryMapper.toDto(tAHistory);
    }

    /**
     * Get all the tAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TAHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TAHistories");
        return tAHistoryRepository.findAll(pageable)
            .map(tAHistoryMapper::toDto);
    }


    /**
     * Get one tAHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TAHistoryDTO> findOne(Long id) {
        log.debug("Request to get TAHistory : {}", id);
        return tAHistoryRepository.findById(id)
            .map(tAHistoryMapper::toDto);
    }

    /**
     * Get one tAHistory by id.
     *
     * @param cityCategory the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TAHistoryDTO> findOneByCityCategory(String cityCategory) {
        log.debug("Request to get TAHistory : {}", cityCategory);
        return tAHistoryRepository.findOneByCityCategoryOrderByDateDesc(cityCategory).map(tAHistoryMapper::toDto);

    }

    /**
     * Delete the tAHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TAHistory : {}", id);
        tAHistoryRepository.deleteById(id);
    }
}
