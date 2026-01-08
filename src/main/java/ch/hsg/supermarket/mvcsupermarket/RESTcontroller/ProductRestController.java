package ch.hsg.supermarket.mvcsupermarket.rest;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import ch.hsg.supermarket.mvcsupermarket.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;
    private final SupplierService supplierService;

    public ProductRestController(ProductService productService,
                                 SupplierService supplierService) {
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestParam String name,
                                 @RequestParam double price,
                                 @RequestParam Long supplierId,
                                 @RequestParam int minimunStock) {

        Supplier supplier = supplierService.findById(supplierId);
        return productService.createProduct(name, price, supplier,minimunStock);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
