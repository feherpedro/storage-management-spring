package hu.mik.pte.bpnh16.service;

import hu.mik.pte.bpnh16.service.dto.StockTakingItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link hu.mik.pte.bpnh16.domain.StockTakingItem}.
 */
public interface StockTakingItemService {

    /**
     * Save a stockTakingItem.
     *
     * @param stockTakingItemDTO the entity to save.
     * @return the persisted entity.
     */
    StockTakingItemDTO save(StockTakingItemDTO stockTakingItemDTO);

    /**
     * Get all the stockTakingItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockTakingItemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockTakingItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockTakingItemDTO> findOne(Long id);

    /**
     * Delete the "id" stockTakingItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stockTakingItem corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockTakingItemDTO> search(String query, Pageable pageable);
}
