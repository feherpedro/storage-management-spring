package hu.mik.pte.bpnh16.repository;

import hu.mik.pte.bpnh16.domain.StockTaking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockTaking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockTakingRepository extends JpaRepository<StockTaking, Long> {

}
