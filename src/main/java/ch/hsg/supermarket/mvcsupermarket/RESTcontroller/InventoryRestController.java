package ch.hsg.supermarket.mvcsupermarket.rest;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

    private final InventoryService inventoryService;
    private final ProductService productService;

    public InventoryRestController(InventoryService inventoryService,
                                   ProductService productService) {
        this.inventoryService = inventoryService;
        this.productService = productService;
    }

    @PostMapping("/batch")
    public ProductBatch addBatch(@RequestParam Long productId,
                                 @RequestParam int quantity,
                                 @RequestParam String expirationDate) {

        Product product = productService.findById(productId);
        LocalDate date = LocalDate.parse(expirationDate);

        return inventoryService.addBatch(product, quantity, date);
    }

    @GetMapping
    public List<InventoryItem> getInventory() {
        return inventoryService.findAllInventoryItems();
    }

    @GetMapping("/expired/{productId}")
    public boolean hasExpiredBatches(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        return inventoryService.hasExpiredBatches(product);
    }
    @DeleteMapping("/batch/{batchId}")
    public void deleteBatch(@PathVariable Long batchId) {
        inventoryService.removeBatch(batchId);
    }

}
