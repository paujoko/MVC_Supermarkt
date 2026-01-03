package ch.hsg.supermarket.mvcsupermarket.service;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;
import ch.hsg.supermarket.mvcsupermarket.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier createSupplier(String name) {
        return supplierRepository.save(new Supplier(name));
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
    }

    @Transactional
    public void deleteSupplier(Long supplierId) {
        Supplier supplier = findById(supplierId);

        // NOW SAFE: products are loaded inside transaction
        if (!supplier.getProducts().isEmpty()) {
            throw new IllegalStateException(
                    "Supplier cannot be deleted because it still has products"
            );
        }

        supplierRepository.delete(supplier);
    }
}
