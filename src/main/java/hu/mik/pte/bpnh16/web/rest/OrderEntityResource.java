package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.service.OrderEntityService;
import hu.mik.pte.bpnh16.web.rest.errors.BadRequestAlertException;
import hu.mik.pte.bpnh16.service.dto.OrderEntityDTO;

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
 * REST controller for managing {@link hu.mik.pte.bpnh16.domain.OrderEntity}.
 */
@RestController
@RequestMapping("/api")
public class OrderEntityResource {

    private final Logger log = LoggerFactory.getLogger(OrderEntityResource.class);

    private static final String ENTITY_NAME = "orderEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderEntityService orderEntityService;

    public OrderEntityResource(OrderEntityService orderEntityService) {
        this.orderEntityService = orderEntityService;
    }

    /**
     * {@code POST  /order-entities} : Create a new orderEntity.
     *
     * @param orderEntityDTO the orderEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderEntityDTO, or with status {@code 400 (Bad Request)} if the orderEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-entities")
    public ResponseEntity<OrderEntityDTO> createOrderEntity(@Valid @RequestBody OrderEntityDTO orderEntityDTO) throws URISyntaxException {
        log.debug("REST request to save OrderEntity : {}", orderEntityDTO);
        if (orderEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderEntityDTO result = orderEntityService.save(orderEntityDTO);
        return ResponseEntity.created(new URI("/api/order-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-entities} : Updates an existing orderEntity.
     *
     * @param orderEntityDTO the orderEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderEntityDTO,
     * or with status {@code 400 (Bad Request)} if the orderEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-entities")
    public ResponseEntity<OrderEntityDTO> updateOrderEntity(@Valid @RequestBody OrderEntityDTO orderEntityDTO) throws URISyntaxException {
        log.debug("REST request to update OrderEntity : {}", orderEntityDTO);
        if (orderEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderEntityDTO result = orderEntityService.save(orderEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-entities} : get all the orderEntities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderEntities in body.
     */
    @GetMapping("/order-entities")
    public ResponseEntity<List<OrderEntityDTO>> getAllOrderEntities(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of OrderEntities");
        Page<OrderEntityDTO> page = orderEntityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-entities/:id} : get the "id" orderEntity.
     *
     * @param id the id of the orderEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-entities/{id}")
    public ResponseEntity<OrderEntityDTO> getOrderEntity(@PathVariable Long id) {
        log.debug("REST request to get OrderEntity : {}", id);
        Optional<OrderEntityDTO> orderEntityDTO = orderEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderEntityDTO);
    }

    /**
     * {@code DELETE  /order-entities/:id} : delete the "id" orderEntity.
     *
     * @param id the id of the orderEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-entities/{id}")
    public ResponseEntity<Void> deleteOrderEntity(@PathVariable Long id) {
        log.debug("REST request to delete OrderEntity : {}", id);
        orderEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/order-entities?query=:query} : search for the orderEntity corresponding
     * to the query.
     *
     * @param query the query of the orderEntity search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/order-entities")
    public ResponseEntity<List<OrderEntityDTO>> searchOrderEntities(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of OrderEntities for query {}", query);
        Page<OrderEntityDTO> page = orderEntityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
