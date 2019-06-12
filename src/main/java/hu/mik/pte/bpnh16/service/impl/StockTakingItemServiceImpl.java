package hu.mik.pte.bpnh16.service.impl;

import hu.mik.pte.bpnh16.service.StockTakingItemService;
import hu.mik.pte.bpnh16.domain.StockTakingItem;
import hu.mik.pte.bpnh16.repository.StockTakingItemRepository;
import hu.mik.pte.bpnh16.repository.search.StockTakingItemSearchRepository;
import hu.mik.pte.bpnh16.service.dto.StockTakingItemDTO;
import hu.mik.pte.bpnh16.service.mapper.StockTakingItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link StockTakingItem}.
 */
@Service
@Transactional
public class StockTakingItemServiceImpl implements StockTakingItemService {

    private final Logger log = LoggerFactory.getLogger(StockTakingItemServiceImpl.class);

    private final StockTakingItemRepository stockTakingItemRepository;

    private final StockTakingItemMapper stockTakingItemMapper;

    private final StockTakingItemSearchRepository stockTakingItemSearchRepository;

    public StockTakingItemServiceImpl(StockTakingItemRepository stockTakingItemRepository, StockTakingItemMapper stockTakingItemMapper, StockTakingItemSearchRepository stockTakingItemSearchRepository) {
        this.stockTakingItemRepository = stockTakingItemRepository;
        this.stockTakingItemMapper = stockTakingItemMapper;
        this.stockTakingItemSearchRepository = stockTakingItemSearchRepository;
    }

    /**
     * Save a stockTakingItem.
     *
     * @param stockTakingItemDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockTakingItemDTO save(StockTakingItemDTO stockTakingItemDTO) {
        log.debug("Request to save StockTakingItem : {}", stockTakingItemDTO);
        StockTakingItem stockTakingItem = stockTakingItemMapper.toEntity(stockTakingItemDTO);
        stockTakingItem = stockTakingItemRepository.save(stockTakingItem);
        StockTakingItemDTO result = stockTakingItemMapper.toDto(stockTakingItem);
        stockTakingItemSearchRepository.save(stockTakingItem);
        return result;
    }

    /**
     * Get all the stockTakingItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockTakingItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockTakingItems");
        return stockTakingItemRepository.findAll(pageable)
            .map(stockTakingItemMapper::toDto);
    }


    /**
     * Get one stockTakingItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockTakingItemDTO> findOne(Long id) {
        log.debug("Request to get StockTakingItem : {}", id);
        return stockTakingItemRepository.findById(id)
            .map(stockTakingItemMapper::toDto);
    }

    /**
     * Delete the stockTakingItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockTakingItem : {}", id);
        stockTakingItemRepository.deleteById(id);
        stockTakingItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockTakingItem corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockTakingItemDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockTakingItems for query {}", query);
        return stockTakingItemSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockTakingItemMapper::toDto);
    }
}
