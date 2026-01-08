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

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class InventoryLowStockBoundaryTest {

    @Autowired
    ProductService productService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    InventoryService inventoryService;

    private Product createProductWithStock(int quantity) {
        Supplier supplier = supplierService.createSupplier("Migros");
        Product product =
                productService.createProduct("Milk", 2.5, supplier, 10);

        inventoryService.addBatch(
                product,
                quantity,
                LocalDate.now().plusDays(10)
        );

        return product;
    }

    @Test
    @DisplayName("Boundary: quantity equals minimum stock")
    void quantity_equals_minimum() {
        Product product = createProductWithStock(10);
        assertFalse(product.getInventoryItem().isLowOnStock());
    }

    @Test
    @DisplayName("Boundary: quantity just above minimum stock")
    void quantity_just_above_minimum() {
        Product product = createProductWithStock(11);
        assertFalse(product.getInventoryItem().isLowOnStock());
    }

    @Test
    @DisplayName("Boundary: nominal quantity")
    void quantity_nominal() {
        Product product = createProductWithStock(15);
        assertFalse(product.getInventoryItem().isLowOnStock());
    }

    @Test
    @DisplayName("Boundary: quantity just below maximum")
    void quantity_just_below_maximum() {
        Product product = createProductWithStock(99);
        assertFalse(product.getInventoryItem().isLowOnStock());
    }

    @Test
    @DisplayName("Boundary: maximum quantity")
    void quantity_maximum() {
        Product product = createProductWithStock(100);
        assertFalse(product.getInventoryItem().isLowOnStock());
    }
}