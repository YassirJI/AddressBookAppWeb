package com.addressbook.ui.vaadin.addressbook;

import com.addressbook.ui.vaadin.component.CustomersListComponent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
public abstract class AbstractListView extends Panel implements View, CustomersLoader {

	public static final String EDIT_ID = "addressbook-edit";
    public static final String TITLE_ID = "addressbook-title";

    private CssLayout addressbookPanels;
    private final VerticalLayout root;
    
    public AbstractListView() {
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

        root.addLayoutClickListener(event -> AddressbookEventBus.post(new CloseOpenWindowsEvent()));
    }

    private Component buildContent() {
        addressbookPanels = new CssLayout();
        addressbookPanels.addStyleName("addressbook-panels");
        Responsive.makeResponsive(addressbookPanels);

        addressbookPanels.addComponent(buildCustomersList());
        return addressbookPanels;
    }

    private Component buildCustomersList() {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("addressbook-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = createPanelToolbar();

        Component content =  new CustomersListComponent(this);
        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }

	private HorizontalLayout createPanelToolbar() {
		HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("addressbook-panel-toolbar");
        toolbar.setWidth("100%");

        Label caption = new Label(getToolbarTitle());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        
        MenuItem root = tools.addItem("", FontAwesome.COG, null);
        root.addItem("Export", menu ->  Notification.show("Not implemented"));
        
        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
		return toolbar;
	}

    
    protected abstract String getToolbarTitle();
}
