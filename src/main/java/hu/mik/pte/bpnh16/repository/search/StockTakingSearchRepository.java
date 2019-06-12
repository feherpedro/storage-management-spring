package hu.mik.pte.bpnh16.repository.search;

import hu.mik.pte.bpnh16.domain.StockTaking;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockTaking} entity.
 */
public interface StockTakingSearchRepository extends ElasticsearchRepository<StockTaking, Long> {
}
