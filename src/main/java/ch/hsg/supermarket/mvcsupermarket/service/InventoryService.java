package ch.hsg.supermarket.mvcsupermarket.service;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import ch.hsg.supermarket.mvcsupermarket.repositories.InventoryItemRepository;
import ch.hsg.supermarket.mvcsupermarket.repositories.ProductBatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final ProductBatchRepository productBatchRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryService(ProductBatchRepository productBatchRepository,
                            InventoryItemRepository inventoryItemRepository) {
        this.productBatchRepository = productBatchRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    // BUSINESS LOGIC 2
    public ProductBatch addBatch(Product product, int quantity, LocalDate expirationDate) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        ProductBatch batch = new ProductBatch(product, quantity, expirationDate);
        productBatchRepository.save(batch);

        InventoryItem inventoryItem = product.getInventoryItem();
        inventoryItem.updateQuantity(inventoryItem.getTotalQuantity() + quantity);
        inventoryItemRepository.save(inventoryItem);

        return batch;
    }
    public void removeBatch(Long batchId) {
        productBatchRepository.deleteById(batchId);
    }


    // BUSINESS LOGIC 3
    public int calculateTotalStock(Product product) {
        return product.getBatches()
                .stream()
                .mapToInt(ProductBatch::getQuantity)
                .sum();
    }

    public boolean hasExpiredBatches(Product product) {
        return productBatchRepository
                .existsByProductAndExpirationDateBefore(
                        product, LocalDate.now());
    }

    public List<InventoryItem> findAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }
}
