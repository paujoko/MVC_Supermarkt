package ch.hsg.supermarket.mvcsupermarket;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import ch.hsg.supermarket.mvcsupermarket.service.SupplierService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@DisplayName("Inventory – Expired Batch Detection")
public class InventoryExpirationTest {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Autowired
    SupplierService supplierService;

    @Test
    @DisplayName("Product is expired when at least one batch is expired")
    void hasExpiredBatches_returnsTrue_whenExpiredBatchExists() {
        // Arrange
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Yogurt", 1.2, supplier, 5);

        inventoryService.addBatch(
                product,
                3,
                LocalDate.now().minusDays(1) // expired batch
        );

        // Act
        boolean result = inventoryService.hasExpiredBatches(product);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Product is NOT expired when all batches are valid")
    void hasExpiredBatches_returnsFalse_whenNoExpiredBatchExists() {
        // Arrange
        Supplier supplier = supplierService.createSupplier("Coop");
        Product product =
                productService.createProduct("Cheese", 4.0, supplier, 5);

        inventoryService.addBatch(
                product,
                3,
                LocalDate.now().plusDays(10) // valid batch
        );

        // Act
        boolean result = inventoryService.hasExpiredBatches(product);

        // Assert
        assertFalse(result);
    }
    @Test
    @DisplayName("Product is expired when at least one of multiple batches is expired")
    void hasExpiredBatches_detectsExpiredBatch_amongMultipleBatches() {

        // Arrange
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Yogurt", 1.2, supplier, 5);

        // valid batch
        inventoryService.addBatch(
                product,
                5,
                LocalDate.now().plusDays(10)
        );

        // expired batch
        inventoryService.addBatch(
                product,
                2,
                LocalDate.now().minusDays(1)
        );

        // Act
        boolean result = inventoryService.hasExpiredBatches(product);

        // Assert
        assertTrue(result);
    }
}