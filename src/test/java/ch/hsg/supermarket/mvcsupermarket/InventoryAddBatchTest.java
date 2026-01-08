package ch.hsg.supermarket.mvcsupermarket;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@DisplayName("Inventory – Add Batch Business Logic")
class InventoryAddBatchTest {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Autowired
    SupplierService supplierService;

    @Test
    @DisplayName("New product starts with zero inventory")
    void newProduct_hasZeroInitialQuantity() {
        // Arrange
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Milk", 2.5, supplier, 10);

        // Act
        int quantity = product.getInventoryItem().getTotalQuantity();

        // Assert
        assertEquals(0, quantity);
    }

    @Test
    @DisplayName("Adding a batch increases total inventory quantity")
    void addBatch_increasesTotalQuantity() {
        // Arrange
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Milk", 2.5, supplier, 10);

        InventoryItem item = product.getInventoryItem();
        int initialQuantity = item.getTotalQuantity();

        // Act
        inventoryService.addBatch(
                product,
                5,
                LocalDate.now().plusDays(10)
        );

        // Assert
        assertEquals(
                initialQuantity + 5,
                product.getInventoryItem().getTotalQuantity()
        );
    }

    @Test
    @DisplayName("Inventory accumulates quantity over multiple batch additions")
    void addBatch_accumulatesOverMultipleCalls() {

        // Arrange
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Milk", 2.5, supplier, 10);

        LocalDate date = LocalDate.now().plusDays(10);

        // Act
        inventoryService.addBatch(product, 5, date);
        inventoryService.addBatch(product, 3, date);

        // Assert
        assertEquals(
                8,
                product.getInventoryItem().getTotalQuantity()
        );
    }
}