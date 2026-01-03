package ch.hsg.supermarket.mvcsupermarket.domainModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double price;

    @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "product")
    private List<ProductBatch> batches = new ArrayList<>();

    @OneToOne(mappedBy = "product")
    private InventoryItem inventoryItem;

    protected Product() {}

    public Product(String name, double price, Supplier supplier) {
        this.name = name;
        this.price = price;
        this.supplier = supplier;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Supplier getSupplier() { return supplier; }
    public List<ProductBatch> getBatches() { return batches; }
    public InventoryItem getInventoryItem() { return inventoryItem; }
}
