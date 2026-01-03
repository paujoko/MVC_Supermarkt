package ch.hsg.supermarket.mvcsupermarket.view;

import ch.hsg.supermarket.mvcsupermarket.domainModel.InventoryItem;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.service.InventoryService;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
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
