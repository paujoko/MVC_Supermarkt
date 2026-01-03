package ch.hsg.supermarket.mvcsupermarket.view;

import ch.hsg.supermarket.mvcsupermarket.domainModel.Product;
import ch.hsg.supermarket.mvcsupermarket.domainModel.Supplier;
import ch.hsg.supermarket.mvcsupermarket.service.ProductService;
import ch.hsg.supermarket.mvcsupermarket.service.SupplierService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "products", layout = MainView.class)
public class ProductView extends VerticalLayout {

    public ProductView(ProductService productService,
                       SupplierService supplierService) {

        TextField name = new TextField("Product name");
        NumberField price = new NumberField("Price");

        ComboBox<Supplier> supplierBox = new ComboBox<>("Supplier");
        supplierBox.setItems(supplierService.findAll());
        supplierBox.setItemLabelGenerator(Supplier::getName);

        Button add = new Button("Add product");

        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumn(Product::getId).setHeader("ID");
        grid.addColumn(Product::getName).setHeader("Name");
        grid.addColumn(Product::getPrice).setHeader("Price");
        grid.addColumn(p -> p.getSupplier().getName()).setHeader("Supplier");

        grid.addComponentColumn(product ->
                new Button("Delete", e -> {
                    try {
                        productService.deleteProduct(product.getId());
                        grid.setItems(productService.findAll());
                        Notification.show("Product deleted");
                    } catch (Exception ex) {
                        Notification.show(ex.getMessage(), 3000,
                                Notification.Position.MIDDLE);
                    }
                })
        ).setHeader("Actions");

        add.addClickListener(e -> {
            productService.createProduct(
                    name.getValue(),
                    price.getValue(),
                    supplierBox.getValue()
            );
            grid.setItems(productService.findAll());
            name.clear();
            price.clear();
        });

        grid.setItems(productService.findAll());

        add(name, price, supplierBox, add, grid);
    }
}
