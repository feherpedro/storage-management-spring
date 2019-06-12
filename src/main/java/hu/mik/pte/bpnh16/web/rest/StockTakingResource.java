package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.service.StockTakingService;
import hu.mik.pte.bpnh16.web.rest.errors.BadRequestAlertException;
import hu.mik.pte.bpnh16.service.dto.StockTakingDTO;

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
 * REST controller for managing {@link hu.mik.pte.bpnh16.domain.StockTaking}.
 */
@RestController
@RequestMapping("/api")
public class StockTakingResource {

    private final Logger log = LoggerFactory.getLogger(StockTakingResource.class);

    private static final String ENTITY_NAME = "stockTaking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockTakingService stockTakingService;

    public StockTakingResource(StockTakingService stockTakingService) {
        this.stockTakingService = stockTakingService;
    }

    /**
     * {@code POST  /stock-takings} : Create a new stockTaking.
     *
     * @param stockTakingDTO the stockTakingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockTakingDTO, or with status {@code 400 (Bad Request)} if the stockTaking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-takings")
    public ResponseEntity<StockTakingDTO> createStockTaking(@Valid @RequestBody StockTakingDTO stockTakingDTO) throws URISyntaxException {
        log.debug("REST request to save StockTaking : {}", stockTakingDTO);
        if (stockTakingDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockTaking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockTakingDTO result = stockTakingService.save(stockTakingDTO);
        return ResponseEntity.created(new URI("/api/stock-takings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-takings} : Updates an existing stockTaking.
     *
     * @param stockTakingDTO the stockTakingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockTakingDTO,
     * or with status {@code 400 (Bad Request)} if the stockTakingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockTakingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-takings")
    public ResponseEntity<StockTakingDTO> updateStockTaking(@Valid @RequestBody StockTakingDTO stockTakingDTO) throws URISyntaxException {
        log.debug("REST request to update StockTaking : {}", stockTakingDTO);
        if (stockTakingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockTakingDTO result = stockTakingService.save(stockTakingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockTakingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-takings} : get all the stockTakings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockTakings in body.
     */
    @GetMapping("/stock-takings")
    public ResponseEntity<List<StockTakingDTO>> getAllStockTakings(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of StockTakings");
        Page<StockTakingDTO> page = stockTakingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-takings/:id} : get the "id" stockTaking.
     *
     * @param id the id of the stockTakingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockTakingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-takings/{id}")
    public ResponseEntity<StockTakingDTO> getStockTaking(@PathVariable Long id) {
        log.debug("REST request to get StockTaking : {}", id);
        Optional<StockTakingDTO> stockTakingDTO = stockTakingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockTakingDTO);
    }

    /**
     * {@code DELETE  /stock-takings/:id} : delete the "id" stockTaking.
     *
     * @param id the id of the stockTakingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-takings/{id}")
    public ResponseEntity<Void> deleteStockTaking(@PathVariable Long id) {
        log.debug("REST request to delete StockTaking : {}", id);
        stockTakingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/stock-takings?query=:query} : search for the stockTaking corresponding
     * to the query.
     *
     * @param query the query of the stockTaking search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/stock-takings")
    public ResponseEntity<List<StockTakingDTO>> searchStockTakings(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of StockTakings for query {}", query);
        Page<StockTakingDTO> page = stockTakingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
