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
    private int minimumQuantity;

    @OneToOne
    private Product product;

    protected InventoryItem() {
    }

    public InventoryItem(Product product, int totalQuantity, int minimumQuantity) {
        this.product = product;
        this.totalQuantity = totalQuantity;
        this.minimumQuantity = minimumQuantity;
    }


    public Long getId() {
        return id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void updateQuantity(int newQuantity) {
        this.totalQuantity = newQuantity;
    }

    /* ======================
       BUSINESS LOGIC
       ====================== */

    public boolean isLowOnStock() {
        return totalQuantity < minimumQuantity;
    }
}