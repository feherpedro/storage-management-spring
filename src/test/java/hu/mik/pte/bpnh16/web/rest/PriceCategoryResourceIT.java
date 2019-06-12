package hu.mik.pte.bpnh16.web.rest;

import hu.mik.pte.bpnh16.StorageManagementSpringApp;
import hu.mik.pte.bpnh16.domain.PriceCategory;
import hu.mik.pte.bpnh16.repository.PriceCategoryRepository;
import hu.mik.pte.bpnh16.repository.search.PriceCategorySearchRepository;
import hu.mik.pte.bpnh16.service.PriceCategoryService;
import hu.mik.pte.bpnh16.service.dto.PriceCategoryDTO;
import hu.mik.pte.bpnh16.service.mapper.PriceCategoryMapper;
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
 * Integration tests for the {@Link PriceCategoryResource} REST controller.
 */
@SpringBootTest(classes = StorageManagementSpringApp.class)
public class PriceCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    @Autowired
    private PriceCategoryMapper priceCategoryMapper;

    @Autowired
    private PriceCategoryService priceCategoryService;

    /**
     * This repository is mocked in the hu.mik.pte.bpnh16.repository.search test package.
     *
     * @see hu.mik.pte.bpnh16.repository.search.PriceCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private PriceCategorySearchRepository mockPriceCategorySearchRepository;

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

    private MockMvc restPriceCategoryMockMvc;

    private PriceCategory priceCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceCategoryResource priceCategoryResource = new PriceCategoryResource(priceCategoryService);
        this.restPriceCategoryMockMvc = MockMvcBuilders.standaloneSetup(priceCategoryResource)
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
    public static PriceCategory createEntity(EntityManager em) {
        PriceCategory priceCategory = new PriceCategory()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE);
        return priceCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceCategory createUpdatedEntity(EntityManager em) {
        PriceCategory priceCategory = new PriceCategory()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        return priceCategory;
    }

    @BeforeEach
    public void initTest() {
        priceCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceCategory() throws Exception {
        int databaseSizeBeforeCreate = priceCategoryRepository.findAll().size();

        // Create the PriceCategory
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.toDto(priceCategory);
        restPriceCategoryMockMvc.perform(post("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        PriceCategory testPriceCategory = priceCategoryList.get(priceCategoryList.size() - 1);
        assertThat(testPriceCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPriceCategory.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the PriceCategory in Elasticsearch
        verify(mockPriceCategorySearchRepository, times(1)).save(testPriceCategory);
    }

    @Test
    @Transactional
    public void createPriceCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceCategoryRepository.findAll().size();

        // Create the PriceCategory with an existing ID
        priceCategory.setId(1L);
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.toDto(priceCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceCategoryMockMvc.perform(post("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the PriceCategory in Elasticsearch
        verify(mockPriceCategorySearchRepository, times(0)).save(priceCategory);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCategoryRepository.findAll().size();
        // set the field null
        priceCategory.setName(null);

        // Create the PriceCategory, which fails.
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.toDto(priceCategory);

        restPriceCategoryMockMvc.perform(post("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceCategories() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);

        // Get all the priceCategoryList
        restPriceCategoryMockMvc.perform(get("/api/price-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getPriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);

        // Get the priceCategory
        restPriceCategoryMockMvc.perform(get("/api/price-categories/{id}", priceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceCategory() throws Exception {
        // Get the priceCategory
        restPriceCategoryMockMvc.perform(get("/api/price-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);

        int databaseSizeBeforeUpdate = priceCategoryRepository.findAll().size();

        // Update the priceCategory
        PriceCategory updatedPriceCategory = priceCategoryRepository.findById(priceCategory.getId()).get();
        // Disconnect from session so that the updates on updatedPriceCategory are not directly saved in db
        em.detach(updatedPriceCategory);
        updatedPriceCategory
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.toDto(updatedPriceCategory);

        restPriceCategoryMockMvc.perform(put("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeUpdate);
        PriceCategory testPriceCategory = priceCategoryList.get(priceCategoryList.size() - 1);
        assertThat(testPriceCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPriceCategory.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the PriceCategory in Elasticsearch
        verify(mockPriceCategorySearchRepository, times(1)).save(testPriceCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceCategory() throws Exception {
        int databaseSizeBeforeUpdate = priceCategoryRepository.findAll().size();

        // Create the PriceCategory
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.toDto(priceCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceCategoryMockMvc.perform(put("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PriceCategory in Elasticsearch
        verify(mockPriceCategorySearchRepository, times(0)).save(priceCategory);
    }

    @Test
    @Transactional
    public void deletePriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);

        int databaseSizeBeforeDelete = priceCategoryRepository.findAll().size();

        // Delete the priceCategory
        restPriceCategoryMockMvc.perform(delete("/api/price-categories/{id}", priceCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PriceCategory in Elasticsearch
        verify(mockPriceCategorySearchRepository, times(1)).deleteById(priceCategory.getId());
    }

    @Test
    @Transactional
    public void searchPriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);
        when(mockPriceCategorySearchRepository.search(queryStringQuery("id:" + priceCategory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(priceCategory), PageRequest.of(0, 1), 1));
        // Search the priceCategory
        restPriceCategoryMockMvc.perform(get("/api/_search/price-categories?query=id:" + priceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceCategory.class);
        PriceCategory priceCategory1 = new PriceCategory();
        priceCategory1.setId(1L);
        PriceCategory priceCategory2 = new PriceCategory();
        priceCategory2.setId(priceCategory1.getId());
        assertThat(priceCategory1).isEqualTo(priceCategory2);
        priceCategory2.setId(2L);
        assertThat(priceCategory1).isNotEqualTo(priceCategory2);
        priceCategory1.setId(null);
        assertThat(priceCategory1).isNotEqualTo(priceCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceCategoryDTO.class);
        PriceCategoryDTO priceCategoryDTO1 = new PriceCategoryDTO();
        priceCategoryDTO1.setId(1L);
        PriceCategoryDTO priceCategoryDTO2 = new PriceCategoryDTO();
        assertThat(priceCategoryDTO1).isNotEqualTo(priceCategoryDTO2);
        priceCategoryDTO2.setId(priceCategoryDTO1.getId());
        assertThat(priceCategoryDTO1).isEqualTo(priceCategoryDTO2);
        priceCategoryDTO2.setId(2L);
        assertThat(priceCategoryDTO1).isNotEqualTo(priceCategoryDTO2);
        priceCategoryDTO1.setId(null);
        assertThat(priceCategoryDTO1).isNotEqualTo(priceCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceCategoryMapper.fromId(null)).isNull();
    }
}
