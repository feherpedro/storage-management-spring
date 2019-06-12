package hu.mik.pte.bpnh16.repository.search;

import hu.mik.pte.bpnh16.domain.PriceCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PriceCategory} entity.
 */
public interface PriceCategorySearchRepository extends ElasticsearchRepository<PriceCategory, Long> {
}
