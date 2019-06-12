package hu.mik.pte.bpnh16.service.impl;

import hu.mik.pte.bpnh16.service.PriceCategoryService;
import hu.mik.pte.bpnh16.domain.PriceCategory;
import hu.mik.pte.bpnh16.repository.PriceCategoryRepository;
import hu.mik.pte.bpnh16.repository.search.PriceCategorySearchRepository;
import hu.mik.pte.bpnh16.service.dto.PriceCategoryDTO;
import hu.mik.pte.bpnh16.service.mapper.PriceCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PriceCategory}.
 */
@Service
@Transactional
public class PriceCategoryServiceImpl implements PriceCategoryService {

    private final Logger log = LoggerFactory.getLogger(PriceCategoryServiceImpl.class);

    private final PriceCategoryRepository priceCategoryRepository;

    private final PriceCategoryMapper priceCategoryMapper;

    private final PriceCategorySearchRepository priceCategorySearchRepository;

    public PriceCategoryServiceImpl(PriceCategoryRepository priceCategoryRepository, PriceCategoryMapper priceCategoryMapper, PriceCategorySearchRepository priceCategorySearchRepository) {
        this.priceCategoryRepository = priceCategoryRepository;
        this.priceCategoryMapper = priceCategoryMapper;
        this.priceCategorySearchRepository = priceCategorySearchRepository;
    }

    /**
     * Save a priceCategory.
     *
     * @param priceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PriceCategoryDTO save(PriceCategoryDTO priceCategoryDTO) {
        log.debug("Request to save PriceCategory : {}", priceCategoryDTO);
        PriceCategory priceCategory = priceCategoryMapper.toEntity(priceCategoryDTO);
        priceCategory = priceCategoryRepository.save(priceCategory);
        PriceCategoryDTO result = priceCategoryMapper.toDto(priceCategory);
        priceCategorySearchRepository.save(priceCategory);
        return result;
    }

    /**
     * Get all the priceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PriceCategories");
        return priceCategoryRepository.findAll(pageable)
            .map(priceCategoryMapper::toDto);
    }


    /**
     * Get one priceCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceCategoryDTO> findOne(Long id) {
        log.debug("Request to get PriceCategory : {}", id);
        return priceCategoryRepository.findById(id)
            .map(priceCategoryMapper::toDto);
    }

    /**
     * Delete the priceCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceCategory : {}", id);
        priceCategoryRepository.deleteById(id);
        priceCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the priceCategory corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PriceCategories for query {}", query);
        return priceCategorySearchRepository.search(queryStringQuery(query), pageable)
            .map(priceCategoryMapper::toDto);
    }
}
