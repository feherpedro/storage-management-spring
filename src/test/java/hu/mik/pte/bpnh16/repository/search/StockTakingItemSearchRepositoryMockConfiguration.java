package hu.mik.pte.bpnh16.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link StockTakingItemSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StockTakingItemSearchRepositoryMockConfiguration {

    @MockBean
    private StockTakingItemSearchRepository mockStockTakingItemSearchRepository;

}
