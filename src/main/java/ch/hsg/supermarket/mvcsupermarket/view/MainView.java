package ch.hsg.supermarket.mvcsupermarket.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainView extends AppLayout {

    public MainView() {
        H1 title = new H1("Supermarket Inventory");

        RouterLink suppliers = new RouterLink("Suppliers", SupplierView.class);
        RouterLink products = new RouterLink("Products", ProductView.class);
        RouterLink inventory = new RouterLink("Inventory", InventoryView.class);

        HorizontalLayout menu = new HorizontalLayout(suppliers, products, inventory);

        addToNavbar(title, menu);
    }
}
