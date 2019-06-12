package hu.mik.pte.bpnh16.repository;

import hu.mik.pte.bpnh16.domain.StockTakingItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockTakingItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockTakingItemRepository extends JpaRepository<StockTakingItem, Long> {

}
