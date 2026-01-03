package ch.hsg.supermarket.mvcsupermarket.repositories;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
