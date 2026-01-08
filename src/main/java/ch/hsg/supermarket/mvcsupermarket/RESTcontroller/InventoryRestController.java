package ch.hsg.supermarket.mvcsupermarket.rest;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRestController {

    private final InventoryService inventoryService;

    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @GetMapping
    public List<InventoryItem> getInventory() {
        return inventoryService.findAllInventoryItems();
    }
}
