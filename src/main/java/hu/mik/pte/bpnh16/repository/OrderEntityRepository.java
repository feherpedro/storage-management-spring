package hu.mik.pte.bpnh16.repository;

import hu.mik.pte.bpnh16.domain.OrderEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {

}
