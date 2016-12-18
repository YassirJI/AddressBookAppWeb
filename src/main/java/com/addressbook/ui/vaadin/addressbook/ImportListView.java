package com.addressbook.ui.vaadin.addressbook;

import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class ImportListView extends Panel implements View {

	private static final long serialVersionUID = 1L;
	
	public static final String TITLE_ID = "addressbook-title";

    private	VerticalLayout addressbookPanels;
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

        root.addLayoutClickListener(event-> AddressbookEventBus.post(new CloseOpenWindowsEvent()));
    }

    private Component buildContent() {
        addressbookPanels = new VerticalLayout();
        addressbookPanels.addStyleName("addressbook-panels");
        Responsive.makeResponsive(addressbookPanels);

        Component titleAndBox = buildImportComponent();
		addressbookPanels.addComponent(titleAndBox);
        addressbookPanels.setComponentAlignment(titleAndBox, Alignment.MIDDLE_CENTER);

        return addressbookPanels;
    }

    private Component buildImportComponent() {
        VerticalLayout titleAndBox = new VerticalLayout();
        titleAndBox.setSizeUndefined();
        titleAndBox.setSpacing(true);
        titleAndBox.addStyleName("drafts");
        
        Label importTitle = new Label("Import Addresses (CSV FILE)");
        importTitle.addStyleName(ValoTheme.LABEL_H1);
        importTitle.setSizeUndefined();
        titleAndBox.addComponent(importTitle);
        titleAndBox.setComponentAlignment(importTitle, Alignment.TOP_CENTER);

        titleAndBox.addComponent(buildImportBox());

        return titleAndBox;
    }

    private Component buildImportBox() {
        VerticalLayout importBox = new VerticalLayout();
        importBox.setWidth(160.0f, Unit.PIXELS);
        importBox.setHeight(200.0f, Unit.PIXELS);
        importBox.addStyleName("create");

        Button create = new Button("Browse csv file");
        create.addStyleName(ValoTheme.BUTTON_PRIMARY);
        create.addClickListener(event -> Notification.show("Not implemented"));
        
        importBox.addComponent(create);
        importBox.setComponentAlignment(create, Alignment.MIDDLE_CENTER);
        return importBox;
    }
    @Override
    public void enter(final ViewChangeEvent event) {
    }
}
