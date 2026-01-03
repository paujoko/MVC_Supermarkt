package ch.hsg.supermarket.mvcsupermarket.service;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import ch.hsg.supermarket.mvcsupermarket.repositories.InventoryItemRepository;
import ch.hsg.supermarket.mvcsupermarket.repositories.ProductBatchRepository;
import ch.hsg.supermarket.mvcsupermarket.repositories.ProductRepository;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductBatchRepository productBatchRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public ProductService(ProductRepository productRepository,
                          ProductBatchRepository productBatchRepository,
                          InventoryItemRepository inventoryItemRepository) {
        this.productRepository = productRepository;
        this.productBatchRepository = productBatchRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public Product createProduct(String name, double price, Supplier supplier) {
        Product product = new Product(name, price, supplier);
        productRepository.save(product);

        InventoryItem inventoryItem = new InventoryItem(product, 0);
        inventoryItemRepository.save(inventoryItem);

        return product;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findById(productId);

        // delete batches
        for (ProductBatch batch : product.getBatches()) {
            productBatchRepository.delete(batch);
        }

        // delete inventory item
        InventoryItem item = product.getInventoryItem();
        if (item != null) {
            inventoryItemRepository.delete(item);
        }

        // finally delete product
        productRepository.delete(product);
    }
}
