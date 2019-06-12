package hu.mik.pte.bpnh16.service;

import hu.mik.pte.bpnh16.service.dto.ProductCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link hu.mik.pte.bpnh16.domain.ProductCategory}.
 */
public interface ProductCategoryService {

    /**
     * Save a productCategory.
     *
     * @param productCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProductCategoryDTO save(ProductCategoryDTO productCategoryDTO);

    /**
     * Get all the productCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" productCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the productCategory corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductCategoryDTO> search(String query, Pageable pageable);
}
