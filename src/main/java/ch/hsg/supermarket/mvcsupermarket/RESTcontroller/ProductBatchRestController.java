package ch.hsg.supermarket.mvcsupermarket.rest;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class ProductBatchRestController {

    private final InventoryService inventoryService;
    private final ProductService productService;

    public ProductBatchRestController(InventoryService inventoryService,
                                      ProductService productService) {
        this.inventoryService = inventoryService;
        this.productService = productService;
    }


    @GetMapping("/product/{productId}")
    public List<ProductBatch> getBatchesForProduct(@PathVariable Long productId) {
        return inventoryService.findBatchesByProductId(productId);
    }

    @PostMapping
    public ProductBatch addBatch(@RequestParam Long productId,
                                 @RequestParam int quantity,
                                 @RequestParam String expirationDate) {

        Product product = productService.findById(productId);
        LocalDate date = LocalDate.parse(expirationDate);

        return inventoryService.addBatch(product, quantity, date);
    }


    @DeleteMapping("/{batchId}")
    public void deleteBatch(@PathVariable Long batchId) {
        inventoryService.removeBatch(batchId);
    }
}
