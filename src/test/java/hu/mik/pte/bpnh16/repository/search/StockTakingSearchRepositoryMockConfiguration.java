package hu.mik.pte.bpnh16.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link StockTakingSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StockTakingSearchRepositoryMockConfiguration {

    @MockBean
    private StockTakingSearchRepository mockStockTakingSearchRepository;

}
