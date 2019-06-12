package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.StorageManagementSpringApp;
import hu.mik.pte.bpnh16.domain.StockTakingItem;
import hu.mik.pte.bpnh16.repository.StockTakingItemRepository;
import hu.mik.pte.bpnh16.repository.search.StockTakingItemSearchRepository;
import hu.mik.pte.bpnh16.service.StockTakingItemService;
import hu.mik.pte.bpnh16.service.dto.StockTakingItemDTO;
import hu.mik.pte.bpnh16.service.mapper.StockTakingItemMapper;
import hu.mik.pte.bpnh16.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static hu.mik.pte.bpnh16.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link StockTakingItemResource} REST controller.
 */
@SpringBootTest(classes = StorageManagementSpringApp.class)
public class StockTakingItemResourceIT {

    private static final Long DEFAULT_OLD_QUANTITY = 1L;
    private static final Long UPDATED_OLD_QUANTITY = 2L;

    private static final Long DEFAULT_NEW_QUANTITY = 1L;
    private static final Long UPDATED_NEW_QUANTITY = 2L;

    private static final Long DEFAULT_DIFFERENCE = 1L;
    private static final Long UPDATED_DIFFERENCE = 2L;

    @Autowired
    private StockTakingItemRepository stockTakingItemRepository;

    @Autowired
    private StockTakingItemMapper stockTakingItemMapper;

    @Autowired
    private StockTakingItemService stockTakingItemService;

    /**
     * This repository is mocked in the hu.mik.pte.bpnh16.repository.search test package.
     *
     * @see hu.mik.pte.bpnh16.repository.search.StockTakingItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockTakingItemSearchRepository mockStockTakingItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStockTakingItemMockMvc;

    private StockTakingItem stockTakingItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockTakingItemResource stockTakingItemResource = new StockTakingItemResource(stockTakingItemService);
        this.restStockTakingItemMockMvc = MockMvcBuilders.standaloneSetup(stockTakingItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTakingItem createEntity(EntityManager em) {
        StockTakingItem stockTakingItem = new StockTakingItem()
            .oldQuantity(DEFAULT_OLD_QUANTITY)
            .newQuantity(DEFAULT_NEW_QUANTITY)
            .difference(DEFAULT_DIFFERENCE);
        return stockTakingItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTakingItem createUpdatedEntity(EntityManager em) {
        StockTakingItem stockTakingItem = new StockTakingItem()
            .oldQuantity(UPDATED_OLD_QUANTITY)
            .newQuantity(UPDATED_NEW_QUANTITY)
            .difference(UPDATED_DIFFERENCE);
        return stockTakingItem;
    }

    @BeforeEach
    public void initTest() {
        stockTakingItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockTakingItem() throws Exception {
        int databaseSizeBeforeCreate = stockTakingItemRepository.findAll().size();

        // Create the StockTakingItem
        StockTakingItemDTO stockTakingItemDTO = stockTakingItemMapper.toDto(stockTakingItem);
        restStockTakingItemMockMvc.perform(post("/api/stock-taking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingItemDTO)))
            .andExpect(status().isCreated());

        // Validate the StockTakingItem in the database
        List<StockTakingItem> stockTakingItemList = stockTakingItemRepository.findAll();
        assertThat(stockTakingItemList).hasSize(databaseSizeBeforeCreate + 1);
        StockTakingItem testStockTakingItem = stockTakingItemList.get(stockTakingItemList.size() - 1);
        assertThat(testStockTakingItem.getOldQuantity()).isEqualTo(DEFAULT_OLD_QUANTITY);
        assertThat(testStockTakingItem.getNewQuantity()).isEqualTo(DEFAULT_NEW_QUANTITY);
        assertThat(testStockTakingItem.getDifference()).isEqualTo(DEFAULT_DIFFERENCE);

        // Validate the StockTakingItem in Elasticsearch
        verify(mockStockTakingItemSearchRepository, times(1)).save(testStockTakingItem);
    }

    @Test
    @Transactional
    public void createStockTakingItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockTakingItemRepository.findAll().size();

        // Create the StockTakingItem with an existing ID
        stockTakingItem.setId(1L);
        StockTakingItemDTO stockTakingItemDTO = stockTakingItemMapper.toDto(stockTakingItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockTakingItemMockMvc.perform(post("/api/stock-taking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockTakingItem in the database
        List<StockTakingItem> stockTakingItemList = stockTakingItemRepository.findAll();
        assertThat(stockTakingItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockTakingItem in Elasticsearch
        verify(mockStockTakingItemSearchRepository, times(0)).save(stockTakingItem);
    }


    @Test
    @Transactional
    public void checkNewQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTakingItemRepository.findAll().size();
        // set the field null
        stockTakingItem.setNewQuantity(null);

        // Create the StockTakingItem, which fails.
        StockTakingItemDTO stockTakingItemDTO = stockTakingItemMapper.toDto(stockTakingItem);

        restStockTakingItemMockMvc.perform(post("/api/stock-taking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockTakingItem> stockTakingItemList = stockTakingItemRepository.findAll();
        assertThat(stockTakingItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockTakingItems() throws Exception {
        // Initialize the database
        stockTakingItemRepository.saveAndFlush(stockTakingItem);

        // Get all the stockTakingItemList
        restStockTakingItemMockMvc.perform(get("/api/stock-taking-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTakingItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].oldQuantity").value(hasItem(DEFAULT_OLD_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].newQuantity").value(hasItem(DEFAULT_NEW_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].difference").value(hasItem(DEFAULT_DIFFERENCE.intValue())));
    }
    
    @Test
    @Transactional
    public void getStockTakingItem() throws Exception {
        // Initialize the database
        stockTakingItemRepository.saveAndFlush(stockTakingItem);

        // Get the stockTakingItem
        restStockTakingItemMockMvc.perform(get("/api/stock-taking-items/{id}", stockTakingItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockTakingItem.getId().intValue()))
            .andExpect(jsonPath("$.oldQuantity").value(DEFAULT_OLD_QUANTITY.intValue()))
            .andExpect(jsonPath("$.newQuantity").value(DEFAULT_NEW_QUANTITY.intValue()))
            .andExpect(jsonPath("$.difference").value(DEFAULT_DIFFERENCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStockTakingItem() throws Exception {
        // Get the stockTakingItem
        restStockTakingItemMockMvc.perform(get("/api/stock-taking-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockTakingItem() throws Exception {
        // Initialize the database
        stockTakingItemRepository.saveAndFlush(stockTakingItem);

        int databaseSizeBeforeUpdate = stockTakingItemRepository.findAll().size();

        // Update the stockTakingItem
        StockTakingItem updatedStockTakingItem = stockTakingItemRepository.findById(stockTakingItem.getId()).get();
        // Disconnect from session so that the updates on updatedStockTakingItem are not directly saved in db
        em.detach(updatedStockTakingItem);
        updatedStockTakingItem
            .oldQuantity(UPDATED_OLD_QUANTITY)
            .newQuantity(UPDATED_NEW_QUANTITY)
            .difference(UPDATED_DIFFERENCE);
        StockTakingItemDTO stockTakingItemDTO = stockTakingItemMapper.toDto(updatedStockTakingItem);

        restStockTakingItemMockMvc.perform(put("/api/stock-taking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingItemDTO)))
            .andExpect(status().isOk());

        // Validate the StockTakingItem in the database
        List<StockTakingItem> stockTakingItemList = stockTakingItemRepository.findAll();
        assertThat(stockTakingItemList).hasSize(databaseSizeBeforeUpdate);
        StockTakingItem testStockTakingItem = stockTakingItemList.get(stockTakingItemList.size() - 1);
        assertThat(testStockTakingItem.getOldQuantity()).isEqualTo(UPDATED_OLD_QUANTITY);
        assertThat(testStockTakingItem.getNewQuantity()).isEqualTo(UPDATED_NEW_QUANTITY);
        assertThat(testStockTakingItem.getDifference()).isEqualTo(UPDATED_DIFFERENCE);

        // Validate the StockTakingItem in Elasticsearch
        verify(mockStockTakingItemSearchRepository, times(1)).save(testStockTakingItem);
    }

    @Test
    @Transactional
    public void updateNonExistingStockTakingItem() throws Exception {
        int databaseSizeBeforeUpdate = stockTakingItemRepository.findAll().size();

        // Create the StockTakingItem
        StockTakingItemDTO stockTakingItemDTO = stockTakingItemMapper.toDto(stockTakingItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockTakingItemMockMvc.perform(put("/api/stock-taking-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockTakingItem in the database
        List<StockTakingItem> stockTakingItemList = stockTakingItemRepository.findAll();
        assertThat(stockTakingItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockTakingItem in Elasticsearch
        verify(mockStockTakingItemSearchRepository, times(0)).save(stockTakingItem);
    }

    @Test
    @Transactional
    public void deleteStockTakingItem() throws Exception {
        // Initialize the database
        stockTakingItemRepository.saveAndFlush(stockTakingItem);

        int databaseSizeBeforeDelete = stockTakingItemRepository.findAll().size();

        // Delete the stockTakingItem
        restStockTakingItemMockMvc.perform(delete("/api/stock-taking-items/{id}", stockTakingItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<StockTakingItem> stockTakingItemList = stockTakingItemRepository.findAll();
        assertThat(stockTakingItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockTakingItem in Elasticsearch
        verify(mockStockTakingItemSearchRepository, times(1)).deleteById(stockTakingItem.getId());
    }

    @Test
    @Transactional
    public void searchStockTakingItem() throws Exception {
        // Initialize the database
        stockTakingItemRepository.saveAndFlush(stockTakingItem);
        when(mockStockTakingItemSearchRepository.search(queryStringQuery("id:" + stockTakingItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockTakingItem), PageRequest.of(0, 1), 1));
        // Search the stockTakingItem
        restStockTakingItemMockMvc.perform(get("/api/_search/stock-taking-items?query=id:" + stockTakingItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTakingItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].oldQuantity").value(hasItem(DEFAULT_OLD_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].newQuantity").value(hasItem(DEFAULT_NEW_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].difference").value(hasItem(DEFAULT_DIFFERENCE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockTakingItem.class);
        StockTakingItem stockTakingItem1 = new StockTakingItem();
        stockTakingItem1.setId(1L);
        StockTakingItem stockTakingItem2 = new StockTakingItem();
        stockTakingItem2.setId(stockTakingItem1.getId());
        assertThat(stockTakingItem1).isEqualTo(stockTakingItem2);
        stockTakingItem2.setId(2L);
        assertThat(stockTakingItem1).isNotEqualTo(stockTakingItem2);
        stockTakingItem1.setId(null);
        assertThat(stockTakingItem1).isNotEqualTo(stockTakingItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockTakingItemDTO.class);
        StockTakingItemDTO stockTakingItemDTO1 = new StockTakingItemDTO();
        stockTakingItemDTO1.setId(1L);
        StockTakingItemDTO stockTakingItemDTO2 = new StockTakingItemDTO();
        assertThat(stockTakingItemDTO1).isNotEqualTo(stockTakingItemDTO2);
        stockTakingItemDTO2.setId(stockTakingItemDTO1.getId());
        assertThat(stockTakingItemDTO1).isEqualTo(stockTakingItemDTO2);
        stockTakingItemDTO2.setId(2L);
        assertThat(stockTakingItemDTO1).isNotEqualTo(stockTakingItemDTO2);
        stockTakingItemDTO1.setId(null);
        assertThat(stockTakingItemDTO1).isNotEqualTo(stockTakingItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockTakingItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockTakingItemMapper.fromId(null)).isNull();
    }
}
