package ch.hsg.supermarket.mvcsupermarket.view;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route(value = "inventory", layout = MainView.class)
public class InventoryView extends VerticalLayout {

    public InventoryView(InventoryService inventoryService,
                         ProductService productService) {

        ComboBox<Product> productBox = new ComboBox<>("Product");
        productBox.setItems(productService.findAll());
        productBox.setItemLabelGenerator(Product::getName);

        NumberField quantity = new NumberField("Quantity");
        DatePicker expiration = new DatePicker("Expiration date");

        Button addBatch = new Button("Add batch");

        Grid<InventoryItem> grid = new Grid<>(InventoryItem.class, false);
        grid.addColumn(i -> i.getProduct().getName()).setHeader("Product");
        grid.addColumn(InventoryItem::getTotalQuantity).setHeader("Total stock");

        grid.addComponentColumn(item -> {
            Span status = new Span();

            if (item.isLowOnStock()) {
                status.setText("LOW STOCK");
                status.getStyle().set("color", "red");
                status.getStyle().set("font-weight", "bold");
            } else {
                status.setText("OK");
                status.getStyle().set("color", "green");
            }

            return status;
        }).setHeader("Stock Status");

        grid.addComponentColumn(item -> {
            Span expired = new Span();

            boolean hasExpired = inventoryService.hasExpiredBatches(item.getProduct());

            if (hasExpired) {
                expired.setText("EXPIRED");
                expired.getStyle().set("color", "red");
                expired.getStyle().set("font-weight", "bold");
            } else {
                expired.setText("OK");
                expired.getStyle().set("color", "green");
            }

            return expired;
        }).setHeader("Expired");

        addBatch.addClickListener(e -> {
            inventoryService.addBatch(
                    productBox.getValue(),
                    quantity.getValue().intValue(),
                    expiration.getValue()
            );
            grid.setItems(inventoryService.findAllInventoryItems());
        });

        grid.setItems(inventoryService.findAllInventoryItems());

        add(productBox, quantity, expiration, addBatch, grid);
    }
}
