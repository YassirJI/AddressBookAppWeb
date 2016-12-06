package com.addressbook.ui.vaadin.addressbook;

import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.component.CustomersListComponent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class ImportListView extends Panel implements View {

	private static final long serialVersionUID = 1L;
	
	public static final String TITLE_ID = "addressbook-title";

    private CssLayout addressbookPanels;
    private final VerticalLayout root;
    
    public ImportListView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        AddressbookEventBus.register(this);

        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("addressbook-view");
        setContent(root);
        Responsive.makeResponsive(root);

        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                AddressbookEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

    private Component buildContent() {
        addressbookPanels = new CssLayout();
        addressbookPanels.addStyleName("addressbook-panels");
        Responsive.makeResponsive(addressbookPanels);

        addressbookPanels.addComponent(buildCustomersList());
        return addressbookPanels;
    }

    private Component buildCustomersList() {
        return new VerticalLayout();
    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }
}
