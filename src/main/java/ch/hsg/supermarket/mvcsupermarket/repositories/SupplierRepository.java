package ch.hsg.supermarket.mvcsupermarket.repositories;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
