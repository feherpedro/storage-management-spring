package hu.mik.pte.bpnh16.service;

import hu.mik.pte.bpnh16.service.dto.StockTakingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link hu.mik.pte.bpnh16.domain.StockTaking}.
 */
public interface StockTakingService {

    /**
     * Save a stockTaking.
     *
     * @param stockTakingDTO the entity to save.
     * @return the persisted entity.
     */
    StockTakingDTO save(StockTakingDTO stockTakingDTO);

    /**
     * Get all the stockTakings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockTakingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" stockTaking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockTakingDTO> findOne(Long id);

    /**
     * Delete the "id" stockTaking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the stockTaking corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockTakingDTO> search(String query, Pageable pageable);
}
