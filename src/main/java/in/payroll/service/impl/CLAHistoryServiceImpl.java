package in.payroll.service.impl;

import in.payroll.domain.CLAHistory;
import in.payroll.repository.CLAHistoryRepository;
import in.payroll.service.CLAHistoryService;
import in.payroll.service.dto.CLAHistoryDTO;
import in.payroll.service.mapper.CLAHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CLAHistory.
 */
@Service
@Transactional
public class CLAHistoryServiceImpl implements CLAHistoryService {

    private final Logger log = LoggerFactory.getLogger(CLAHistoryServiceImpl.class);

    private final CLAHistoryRepository cLAHistoryRepository;

    private final CLAHistoryMapper cLAHistoryMapper;

    public CLAHistoryServiceImpl(CLAHistoryRepository cLAHistoryRepository, CLAHistoryMapper cLAHistoryMapper) {
        this.cLAHistoryRepository = cLAHistoryRepository;
        this.cLAHistoryMapper = cLAHistoryMapper;
    }

    /**
     * Save a cLAHistory.
     *
     * @param cLAHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CLAHistoryDTO save(CLAHistoryDTO cLAHistoryDTO) {
        log.debug("Request to save CLAHistory : {}", cLAHistoryDTO);

        CLAHistory cLAHistory = cLAHistoryMapper.toEntity(cLAHistoryDTO);
        cLAHistory = cLAHistoryRepository.save(cLAHistory);
        return cLAHistoryMapper.toDto(cLAHistory);
    }

    /**
     * Get all the cLAHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CLAHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CLAHistories");
        return cLAHistoryRepository.findAll(pageable)
            .map(cLAHistoryMapper::toDto);
    }


    /**
     * Get one cLAHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CLAHistoryDTO> findOne(Long id) {
        log.debug("Request to get CLAHistory : {}", id);
        return cLAHistoryRepository.findById(id)
            .map(cLAHistoryMapper::toDto);
    }

    /**
     * Get the "cityCategory" cLAHistory.
     *
     * @param cityCategory the cityCategory of the entity
     * @return the entity
     */
    @Override
    public Optional<CLAHistoryDTO> findOneByCityCategory(String cityCategory) {
        log.debug("Request to get CLAHistory : {}", cityCategory);
        return cLAHistoryRepository.findOneByCityCategoryOrderByDateDesc(cityCategory)
            .map(cLAHistoryMapper::toDto);
    }


    /**
     * Delete the cLAHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CLAHistory : {}", id);
        cLAHistoryRepository.deleteById(id);
    }
}
