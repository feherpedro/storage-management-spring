package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.service.StockTakingItemService;
import hu.mik.pte.bpnh16.web.rest.errors.BadRequestAlertException;
import hu.mik.pte.bpnh16.service.dto.StockTakingItemDTO;

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
 * REST controller for managing {@link hu.mik.pte.bpnh16.domain.StockTakingItem}.
 */
@RestController
@RequestMapping("/api")
public class StockTakingItemResource {

    private final Logger log = LoggerFactory.getLogger(StockTakingItemResource.class);

    private static final String ENTITY_NAME = "stockTakingItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockTakingItemService stockTakingItemService;

    public StockTakingItemResource(StockTakingItemService stockTakingItemService) {
        this.stockTakingItemService = stockTakingItemService;
    }

    /**
     * {@code POST  /stock-taking-items} : Create a new stockTakingItem.
     *
     * @param stockTakingItemDTO the stockTakingItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockTakingItemDTO, or with status {@code 400 (Bad Request)} if the stockTakingItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-taking-items")
    public ResponseEntity<StockTakingItemDTO> createStockTakingItem(@Valid @RequestBody StockTakingItemDTO stockTakingItemDTO) throws URISyntaxException {
        log.debug("REST request to save StockTakingItem : {}", stockTakingItemDTO);
        if (stockTakingItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockTakingItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockTakingItemDTO result = stockTakingItemService.save(stockTakingItemDTO);
        return ResponseEntity.created(new URI("/api/stock-taking-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-taking-items} : Updates an existing stockTakingItem.
     *
     * @param stockTakingItemDTO the stockTakingItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockTakingItemDTO,
     * or with status {@code 400 (Bad Request)} if the stockTakingItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockTakingItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-taking-items")
    public ResponseEntity<StockTakingItemDTO> updateStockTakingItem(@Valid @RequestBody StockTakingItemDTO stockTakingItemDTO) throws URISyntaxException {
        log.debug("REST request to update StockTakingItem : {}", stockTakingItemDTO);
        if (stockTakingItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockTakingItemDTO result = stockTakingItemService.save(stockTakingItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockTakingItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-taking-items} : get all the stockTakingItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockTakingItems in body.
     */
    @GetMapping("/stock-taking-items")
    public ResponseEntity<List<StockTakingItemDTO>> getAllStockTakingItems(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of StockTakingItems");
        Page<StockTakingItemDTO> page = stockTakingItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-taking-items/:id} : get the "id" stockTakingItem.
     *
     * @param id the id of the stockTakingItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockTakingItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-taking-items/{id}")
    public ResponseEntity<StockTakingItemDTO> getStockTakingItem(@PathVariable Long id) {
        log.debug("REST request to get StockTakingItem : {}", id);
        Optional<StockTakingItemDTO> stockTakingItemDTO = stockTakingItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockTakingItemDTO);
    }

    /**
     * {@code DELETE  /stock-taking-items/:id} : delete the "id" stockTakingItem.
     *
     * @param id the id of the stockTakingItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-taking-items/{id}")
    public ResponseEntity<Void> deleteStockTakingItem(@PathVariable Long id) {
        log.debug("REST request to delete StockTakingItem : {}", id);
        stockTakingItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/stock-taking-items?query=:query} : search for the stockTakingItem corresponding
     * to the query.
     *
     * @param query the query of the stockTakingItem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/stock-taking-items")
    public ResponseEntity<List<StockTakingItemDTO>> searchStockTakingItems(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of StockTakingItems for query {}", query);
        Page<StockTakingItemDTO> page = stockTakingItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
