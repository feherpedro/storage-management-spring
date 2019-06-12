package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.StorageManagementSpringApp;
import hu.mik.pte.bpnh16.domain.StockTaking;
import hu.mik.pte.bpnh16.repository.StockTakingRepository;
import hu.mik.pte.bpnh16.repository.search.StockTakingSearchRepository;
import hu.mik.pte.bpnh16.service.StockTakingService;
import hu.mik.pte.bpnh16.service.dto.StockTakingDTO;
import hu.mik.pte.bpnh16.service.mapper.StockTakingMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@Link StockTakingResource} REST controller.
 */
@SpringBootTest(classes = StorageManagementSpringApp.class)
public class StockTakingResourceIT {

    private static final LocalDate DEFAULT_STOCK_TAKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STOCK_TAKING_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StockTakingRepository stockTakingRepository;

    @Autowired
    private StockTakingMapper stockTakingMapper;

    @Autowired
    private StockTakingService stockTakingService;

    /**
     * This repository is mocked in the hu.mik.pte.bpnh16.repository.search test package.
     *
     * @see hu.mik.pte.bpnh16.repository.search.StockTakingSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockTakingSearchRepository mockStockTakingSearchRepository;

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

    private MockMvc restStockTakingMockMvc;

    private StockTaking stockTaking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockTakingResource stockTakingResource = new StockTakingResource(stockTakingService);
        this.restStockTakingMockMvc = MockMvcBuilders.standaloneSetup(stockTakingResource)
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
    public static StockTaking createEntity(EntityManager em) {
        StockTaking stockTaking = new StockTaking()
            .stockTakingDate(DEFAULT_STOCK_TAKING_DATE);
        return stockTaking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockTaking createUpdatedEntity(EntityManager em) {
        StockTaking stockTaking = new StockTaking()
            .stockTakingDate(UPDATED_STOCK_TAKING_DATE);
        return stockTaking;
    }

    @BeforeEach
    public void initTest() {
        stockTaking = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockTaking() throws Exception {
        int databaseSizeBeforeCreate = stockTakingRepository.findAll().size();

        // Create the StockTaking
        StockTakingDTO stockTakingDTO = stockTakingMapper.toDto(stockTaking);
        restStockTakingMockMvc.perform(post("/api/stock-takings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingDTO)))
            .andExpect(status().isCreated());

        // Validate the StockTaking in the database
        List<StockTaking> stockTakingList = stockTakingRepository.findAll();
        assertThat(stockTakingList).hasSize(databaseSizeBeforeCreate + 1);
        StockTaking testStockTaking = stockTakingList.get(stockTakingList.size() - 1);
        assertThat(testStockTaking.getStockTakingDate()).isEqualTo(DEFAULT_STOCK_TAKING_DATE);

        // Validate the StockTaking in Elasticsearch
        verify(mockStockTakingSearchRepository, times(1)).save(testStockTaking);
    }

    @Test
    @Transactional
    public void createStockTakingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockTakingRepository.findAll().size();

        // Create the StockTaking with an existing ID
        stockTaking.setId(1L);
        StockTakingDTO stockTakingDTO = stockTakingMapper.toDto(stockTaking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockTakingMockMvc.perform(post("/api/stock-takings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockTaking in the database
        List<StockTaking> stockTakingList = stockTakingRepository.findAll();
        assertThat(stockTakingList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockTaking in Elasticsearch
        verify(mockStockTakingSearchRepository, times(0)).save(stockTaking);
    }


    @Test
    @Transactional
    public void checkStockTakingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockTakingRepository.findAll().size();
        // set the field null
        stockTaking.setStockTakingDate(null);

        // Create the StockTaking, which fails.
        StockTakingDTO stockTakingDTO = stockTakingMapper.toDto(stockTaking);

        restStockTakingMockMvc.perform(post("/api/stock-takings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingDTO)))
            .andExpect(status().isBadRequest());

        List<StockTaking> stockTakingList = stockTakingRepository.findAll();
        assertThat(stockTakingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockTakings() throws Exception {
        // Initialize the database
        stockTakingRepository.saveAndFlush(stockTaking);

        // Get all the stockTakingList
        restStockTakingMockMvc.perform(get("/api/stock-takings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTaking.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockTakingDate").value(hasItem(DEFAULT_STOCK_TAKING_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStockTaking() throws Exception {
        // Initialize the database
        stockTakingRepository.saveAndFlush(stockTaking);

        // Get the stockTaking
        restStockTakingMockMvc.perform(get("/api/stock-takings/{id}", stockTaking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockTaking.getId().intValue()))
            .andExpect(jsonPath("$.stockTakingDate").value(DEFAULT_STOCK_TAKING_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockTaking() throws Exception {
        // Get the stockTaking
        restStockTakingMockMvc.perform(get("/api/stock-takings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockTaking() throws Exception {
        // Initialize the database
        stockTakingRepository.saveAndFlush(stockTaking);

        int databaseSizeBeforeUpdate = stockTakingRepository.findAll().size();

        // Update the stockTaking
        StockTaking updatedStockTaking = stockTakingRepository.findById(stockTaking.getId()).get();
        // Disconnect from session so that the updates on updatedStockTaking are not directly saved in db
        em.detach(updatedStockTaking);
        updatedStockTaking
            .stockTakingDate(UPDATED_STOCK_TAKING_DATE);
        StockTakingDTO stockTakingDTO = stockTakingMapper.toDto(updatedStockTaking);

        restStockTakingMockMvc.perform(put("/api/stock-takings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingDTO)))
            .andExpect(status().isOk());

        // Validate the StockTaking in the database
        List<StockTaking> stockTakingList = stockTakingRepository.findAll();
        assertThat(stockTakingList).hasSize(databaseSizeBeforeUpdate);
        StockTaking testStockTaking = stockTakingList.get(stockTakingList.size() - 1);
        assertThat(testStockTaking.getStockTakingDate()).isEqualTo(UPDATED_STOCK_TAKING_DATE);

        // Validate the StockTaking in Elasticsearch
        verify(mockStockTakingSearchRepository, times(1)).save(testStockTaking);
    }

    @Test
    @Transactional
    public void updateNonExistingStockTaking() throws Exception {
        int databaseSizeBeforeUpdate = stockTakingRepository.findAll().size();

        // Create the StockTaking
        StockTakingDTO stockTakingDTO = stockTakingMapper.toDto(stockTaking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockTakingMockMvc.perform(put("/api/stock-takings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockTakingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockTaking in the database
        List<StockTaking> stockTakingList = stockTakingRepository.findAll();
        assertThat(stockTakingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockTaking in Elasticsearch
        verify(mockStockTakingSearchRepository, times(0)).save(stockTaking);
    }

    @Test
    @Transactional
    public void deleteStockTaking() throws Exception {
        // Initialize the database
        stockTakingRepository.saveAndFlush(stockTaking);

        int databaseSizeBeforeDelete = stockTakingRepository.findAll().size();

        // Delete the stockTaking
        restStockTakingMockMvc.perform(delete("/api/stock-takings/{id}", stockTaking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<StockTaking> stockTakingList = stockTakingRepository.findAll();
        assertThat(stockTakingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockTaking in Elasticsearch
        verify(mockStockTakingSearchRepository, times(1)).deleteById(stockTaking.getId());
    }

    @Test
    @Transactional
    public void searchStockTaking() throws Exception {
        // Initialize the database
        stockTakingRepository.saveAndFlush(stockTaking);
        when(mockStockTakingSearchRepository.search(queryStringQuery("id:" + stockTaking.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockTaking), PageRequest.of(0, 1), 1));
        // Search the stockTaking
        restStockTakingMockMvc.perform(get("/api/_search/stock-takings?query=id:" + stockTaking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockTaking.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockTakingDate").value(hasItem(DEFAULT_STOCK_TAKING_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockTaking.class);
        StockTaking stockTaking1 = new StockTaking();
        stockTaking1.setId(1L);
        StockTaking stockTaking2 = new StockTaking();
        stockTaking2.setId(stockTaking1.getId());
        assertThat(stockTaking1).isEqualTo(stockTaking2);
        stockTaking2.setId(2L);
        assertThat(stockTaking1).isNotEqualTo(stockTaking2);
        stockTaking1.setId(null);
        assertThat(stockTaking1).isNotEqualTo(stockTaking2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockTakingDTO.class);
        StockTakingDTO stockTakingDTO1 = new StockTakingDTO();
        stockTakingDTO1.setId(1L);
        StockTakingDTO stockTakingDTO2 = new StockTakingDTO();
        assertThat(stockTakingDTO1).isNotEqualTo(stockTakingDTO2);
        stockTakingDTO2.setId(stockTakingDTO1.getId());
        assertThat(stockTakingDTO1).isEqualTo(stockTakingDTO2);
        stockTakingDTO2.setId(2L);
        assertThat(stockTakingDTO1).isNotEqualTo(stockTakingDTO2);
        stockTakingDTO1.setId(null);
        assertThat(stockTakingDTO1).isNotEqualTo(stockTakingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockTakingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockTakingMapper.fromId(null)).isNull();
    }
}
