package ch.hsg.supermarket.mvcsupermarket.domainModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class InventoryItem {

    @Id
    @GeneratedValue
    private Long id;

    private int totalQuantity;

    @OneToOne
    private Product product;

    protected InventoryItem() {
    }

    public InventoryItem(Product product, int totalQuantity) {
        this.product = product;
        this.totalQuantity = totalQuantity;
    }

    public Long getId() {
        return id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void updateQuantity(int newQuantity) {
        this.totalQuantity = newQuantity;
    }
}
