package hu.mik.pte.bpnh16.repository;

import hu.mik.pte.bpnh16.domain.PriceCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriceCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {

}
