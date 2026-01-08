package ch.hsg.supermarket.mvcsupermarket.view;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;
import ch.hsg.supermarket.mvcsupermarket.service.SupplierService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "", layout = MainView.class)
@RouteAlias(value = "suppliers", layout = MainView.class)
public class SupplierView extends VerticalLayout {

    public SupplierView(SupplierService supplierService) {

        TextField nameField = new TextField("Supplier name");
        Button addButton = new Button("Add supplier");

        Grid<Supplier> grid = new Grid<>(Supplier.class, false);
        grid.addColumn(Supplier::getId).setHeader("ID");
        grid.addColumn(Supplier::getName).setHeader("Name");

        grid.addComponentColumn(supplier ->
                new Button("Delete", e -> {
                    try {
                        supplierService.deleteSupplier(supplier.getId());
                        grid.setItems(supplierService.findAll());
                    } catch (Exception ex) {
                        Notification.show(ex.getMessage(), 3000,
                                Notification.Position.MIDDLE);
                    }
                })
        ).setHeader("Actions");

        addButton.addClickListener(e -> {
            supplierService.createSupplier(nameField.getValue());
            grid.setItems(supplierService.findAll());
            nameField.clear();
        });

        grid.setItems(supplierService.findAll());

        add(nameField, addButton, grid);
    }
}
