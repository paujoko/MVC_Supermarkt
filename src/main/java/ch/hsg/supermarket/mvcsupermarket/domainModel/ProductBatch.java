package ch.hsg.supermarket.mvcsupermarket.domainModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class ProductBatch {

    @Id
    @GeneratedValue
    private Long id;

    private int quantity;
    private LocalDate expirationDate;

    @ManyToOne
    private Product product;

    protected ProductBatch() {
    }

    public ProductBatch(Product product, int quantity, LocalDate expirationDate) {
        this.product = product;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Product getProduct() {
        return product;
    }
}
