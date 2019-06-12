package hu.mik.pte.bpnh16.repository.search;

import hu.mik.pte.bpnh16.domain.StockTakingItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StockTakingItem} entity.
 */
public interface StockTakingItemSearchRepository extends ElasticsearchRepository<StockTakingItem, Long> {
}
