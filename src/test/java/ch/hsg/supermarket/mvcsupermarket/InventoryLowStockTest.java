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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryLowStockTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Test
    @DisplayName("Product is low on stock when total quantity is below minimum stock")
    void isLowOnStock_returnsTrue_whenBelowMinimum() {

        // Arrange
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Milk", 2.5, supplier, 10);

        inventoryService.addBatch(
                product,
                5, // below minimum
                LocalDate.now().plusDays(10)
        );

        // Act
        boolean result = product.getInventoryItem().isLowOnStock();

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Product is not low on stock when total quantity meets minimum stock")
    void isLowOnStock_returnsFalse_whenAtOrAboveMinimum() {

        // Arrange
        Supplier supplier = supplierService.createSupplier("Coop");
        Product product =
                productService.createProduct("Butter", 3.2, supplier, 10);

        inventoryService.addBatch(
                product,
                10, // exactly minimum
                LocalDate.now().plusDays(10)
        );

        // Act
        boolean result = product.getInventoryItem().isLowOnStock();

        // Assert
        assertFalse(result);
    }
}