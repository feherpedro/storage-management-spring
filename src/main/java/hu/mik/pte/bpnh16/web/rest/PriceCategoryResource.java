package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.service.PriceCategoryService;
import hu.mik.pte.bpnh16.web.rest.errors.BadRequestAlertException;
import hu.mik.pte.bpnh16.service.dto.PriceCategoryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link hu.mik.pte.bpnh16.domain.PriceCategory}.
 */
@RestController
@RequestMapping("/api")
public class PriceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PriceCategoryResource.class);

    private static final String ENTITY_NAME = "priceCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriceCategoryService priceCategoryService;

    public PriceCategoryResource(PriceCategoryService priceCategoryService) {
        this.priceCategoryService = priceCategoryService;
    }

    /**
     * {@code POST  /price-categories} : Create a new priceCategory.
     *
     * @param priceCategoryDTO the priceCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priceCategoryDTO, or with status {@code 400 (Bad Request)} if the priceCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/price-categories")
    public ResponseEntity<PriceCategoryDTO> createPriceCategory(@Valid @RequestBody PriceCategoryDTO priceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save PriceCategory : {}", priceCategoryDTO);
        if (priceCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceCategoryDTO result = priceCategoryService.save(priceCategoryDTO);
        return ResponseEntity.created(new URI("/api/price-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /price-categories} : Updates an existing priceCategory.
     *
     * @param priceCategoryDTO the priceCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priceCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the priceCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priceCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/price-categories")
    public ResponseEntity<PriceCategoryDTO> updatePriceCategory(@Valid @RequestBody PriceCategoryDTO priceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update PriceCategory : {}", priceCategoryDTO);
        if (priceCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriceCategoryDTO result = priceCategoryService.save(priceCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, priceCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /price-categories} : get all the priceCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priceCategories in body.
     */
    @GetMapping("/price-categories")
    public ResponseEntity<List<PriceCategoryDTO>> getAllPriceCategories(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of PriceCategories");
        Page<PriceCategoryDTO> page = priceCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /price-categories/:id} : get the "id" priceCategory.
     *
     * @param id the id of the priceCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priceCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-categories/{id}")
    public ResponseEntity<PriceCategoryDTO> getPriceCategory(@PathVariable Long id) {
        log.debug("REST request to get PriceCategory : {}", id);
        Optional<PriceCategoryDTO> priceCategoryDTO = priceCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceCategoryDTO);
    }

    /**
     * {@code DELETE  /price-categories/:id} : delete the "id" priceCategory.
     *
     * @param id the id of the priceCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/price-categories/{id}")
    public ResponseEntity<Void> deletePriceCategory(@PathVariable Long id) {
        log.debug("REST request to delete PriceCategory : {}", id);
        priceCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/price-categories?query=:query} : search for the priceCategory corresponding
     * to the query.
     *
     * @param query the query of the priceCategory search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/price-categories")
    public ResponseEntity<List<PriceCategoryDTO>> searchPriceCategories(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of PriceCategories for query {}", query);
        Page<PriceCategoryDTO> page = priceCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
