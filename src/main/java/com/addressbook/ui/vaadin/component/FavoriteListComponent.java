
package com.addressbook.ui.vaadin.component;

import java.util.List;
import java.util.Locale;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.DetailsGenerator;
import com.vaadin.ui.Grid.RowReference;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;


public class FavoriteListComponent extends VerticalLayout {
   

	private static final long serialVersionUID = 1L;

    public FavoriteListComponent() {
        setMargin(true);
        
        Grid grid = new Grid();

        initializeGrid(grid);
        grid.setWidth("100%");
        grid.setHeight("100%");

        addComponent(grid);
    }

    private void initializeGrid(final Grid grid) {

        BeanItemContainer<Customer> customerContainer = createCustomerContainer();
        grid.setContainerDataSource(customerContainer);


        grid.setColumnOrder(new Object[]{"name", "phone", "email"});

        grid.getColumn("name").setHeaderCaption("Name");
        grid.getColumn("phone").setHeaderCaption("Phone");
        grid.getColumn("email").setHeaderCaption("Email");
        
       
        grid.getColumn("name").setMinimumWidth(400);
        grid.getColumn("phone").setMinimumWidth(400);
        grid.getColumn("email").setMinimumWidth(400);

      
        grid.getColumn("email").setLastFrozenColumn();

        grid.removeColumn("id");
        
//        grid.getColumn("completePercentage").setRenderer(
//                new NumberRenderer(NumberFormat.getPercentInstance(grid
//                        .getLocale())));


        grid.setColumnReorderingAllowed(true);

        grid.setDetailsGenerator(new DetailsGenerator() {
            @Override
            public Component getDetails(RowReference rowReference) {
                Customer order = (Customer) rowReference.getItemId();
                String detailsMessage = "This is a label with information about the customer "
                        + order.getName()
                        + " with "
                        + order.getPhone() 
                        + " and "
                        + order.getEmail()
                        + ".";

                Button deleteButton = new Button("Delete customer",
                        new Button.ClickListener() {
                            @Override
                            public void buttonClick(ClickEvent event) {
                                Notification.show("Button clicked");
                            }
                        });

                VerticalLayout layout = new VerticalLayout(new Label(
                        detailsMessage), deleteButton);
                layout.setMargin(true);
                layout.setSpacing(true);

                return layout;
            }
        });

        grid.addItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Object itemId = event.getItemId();
                    grid.setDetailsVisible(itemId,
                            !grid.isDetailsVisible(itemId));
                }
            }
        });

        grid.setEditorEnabled(false);

      
    }

    private static BeanItemContainer<Customer> createCustomerContainer() {
        return new BeanItemContainer<Customer>(Customer.class, AddressbookUI.getCustomerService().findAll());	
    }
}
