package ch.hsg.supermarket.mvcsupermarket.repositories;

import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBatchRepository extends JpaRepository<ProductBatch, Long> {
}
