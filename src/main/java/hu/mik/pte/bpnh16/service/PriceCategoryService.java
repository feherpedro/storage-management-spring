package hu.mik.pte.bpnh16.service;

import hu.mik.pte.bpnh16.service.dto.PriceCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link hu.mik.pte.bpnh16.domain.PriceCategory}.
 */
public interface PriceCategoryService {

    /**
     * Save a priceCategory.
     *
     * @param priceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    PriceCategoryDTO save(PriceCategoryDTO priceCategoryDTO);

    /**
     * Get all the priceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriceCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" priceCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" priceCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the priceCategory corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriceCategoryDTO> search(String query, Pageable pageable);
}
