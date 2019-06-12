package hu.mik.pte.bpnh16.repository.search;

import hu.mik.pte.bpnh16.domain.OrderEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrderEntity} entity.
 */
public interface OrderEntitySearchRepository extends ElasticsearchRepository<OrderEntity, Long> {
}
