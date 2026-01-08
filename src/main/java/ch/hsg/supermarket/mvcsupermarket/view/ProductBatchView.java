package ch.hsg.supermarket.mvcsupermarket.view;

import ch.hsg.supermarket.mvcsupermarket.domainModel.ProductBatch;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.time.LocalDate;

@Route(value = "batches/:productId", layout = MainView.class)
public class ProductBatchView extends VerticalLayout
        implements BeforeEnterObserver {

    private final InventoryService inventoryService;
    private final Grid<ProductBatch> grid = new Grid<>(ProductBatch.class, false);

    public ProductBatchView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

        grid.addColumn(ProductBatch::getQuantity)
                .setHeader("Quantity");

        grid.addColumn(ProductBatch::getExpirationDate)
                .setHeader("Expiration date");

        grid.addComponentColumn(batch -> {
            Span status = new Span();
            if (batch.getExpirationDate().isBefore(LocalDate.now())) {
                status.setText("EXPIRED");
                status.getStyle().set("color", "red");
                status.getStyle().set("font-weight", "bold");
            } else {
                status.setText("OK");
                status.getStyle().set("color", "green");
            }
            return status;
        }).setHeader("Status");

        add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long productId = Long.valueOf(
                event.getRouteParameters().get("productId").orElseThrow()
        );

        grid.setItems(inventoryService.findBatchesByProductId(productId));
    }
}
