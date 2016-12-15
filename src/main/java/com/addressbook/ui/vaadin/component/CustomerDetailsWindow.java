package com.addressbook.ui.vaadin.component;

import java.util.Date;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class CustomerDetailsWindow extends Window {

    public CustomerDetailsWindow(final Customer customer) {
        addStyleName("customerdetailswindow");
        Responsive.makeResponsive(this);

        setCaption("Address details :");
        center();
        addCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        Panel detailsWrapper = new Panel(buildAddressDetails(customer));
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.PANEL_BORDERLESS);
        detailsWrapper.addStyleName("scroll-divider");
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        content.addComponent(buildFooter());
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Close");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(e -> close());
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    private Component buildAddressDetails(final Customer customer) {
        HorizontalLayout details = new HorizontalLayout();
        details.setWidth(100.0f, Unit.PERCENTAGE);
        details.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        details.setMargin(true);
        details.setSpacing(true);

        Component detailsForm = buildDetailsForm(customer);
        details.addComponent(detailsForm);
        details.setExpandRatio(detailsForm, 1);

        return details;
    }

    private Component buildDetailsForm(final Customer customer) {
        FormLayout fields = new FormLayout();
        fields.setSpacing(false);
        fields.setMargin(false);

        Label label = new Label(customer.getName());
            label.setSizeUndefined();
            label.setCaption("Name");
            fields.addComponent(label);

            label = new Label(customer.getPhone());
            label.setSizeUndefined();
            label.setCaption("Phone");
            fields.addComponent(label);
        
            label = new Label(customer.getEmail());
            label.setSizeUndefined();
            label.setCaption("Email");
            fields.addComponent(label);
        

        return fields;
    }

    public static void open(final Customer customer, final Date startTime,
            final Date endTime) {
        AddressbookEventBus.post(new CloseOpenWindowsEvent());
        Window w = new CustomerDetailsWindow(customer);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}