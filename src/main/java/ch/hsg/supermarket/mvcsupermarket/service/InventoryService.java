package ch.hsg.supermarket.mvcsupermarket.service;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import ch.hsg.supermarket.mvcsupermarket.repositories.InventoryItemRepository;
import ch.hsg.supermarket.mvcsupermarket.repositories.ProductBatchRepository;
import ch.hsg.supermarket.mvcsupermarket.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final ProductBatchRepository productBatchRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;

    public InventoryService(ProductBatchRepository productBatchRepository,
                            InventoryItemRepository inventoryItemRepository,
                            ProductRepository productRepository) {
        this.productBatchRepository = productBatchRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.productRepository = productRepository;
    }

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

        ProductBatch batch = productBatchRepository.findById(batchId)
                .orElseThrow();

        Product product = batch.getProduct();
        InventoryItem inventoryItem = product.getInventoryItem();

        inventoryItem.updateQuantity(
                inventoryItem.getTotalQuantity() - batch.getQuantity()
        );

        inventoryItemRepository.save(inventoryItem);
        productBatchRepository.delete(batch);
    }

    public List<ProductBatch> findBatchesByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow();
        return productBatchRepository.findByProduct(product);
    }

    public List<InventoryItem> findAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }
}
