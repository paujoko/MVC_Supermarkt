package ch.hsg.supermarket.mvcsupermarket.repositories;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ProductBatchRepository extends JpaRepository<ProductBatch, Long> {
    boolean existsByProductAndExpirationDateBefore(
            Product product,
            LocalDate date
    );
}
