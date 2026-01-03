package ch.hsg.supermarket.mvcsupermarket.domainModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductBatch {
    @Id
    private int productBatchId;
}
