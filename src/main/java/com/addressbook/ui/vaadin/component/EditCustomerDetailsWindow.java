package com.addressbook.ui.vaadin.component;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class EditCustomerDetailsWindow extends Window {

    public static final String ID = "editcustomerwindow";

    private final BeanFieldGroup<Customer> fieldGroup;
    
    @PropertyId("name")
    private TextField nameField;
    @PropertyId("email")
    private TextField emailField;
    @PropertyId("phone")
    private TextField phoneField;
    
    private EditCustomerDetailsWindow(final Customer customer) {
        addStyleName("customerdetails-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());
        
        content.addComponent(buildFooter());

        fieldGroup = new BeanFieldGroup<Customer>(Customer.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(customer);
    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Profile");
        root.setIcon(FontAwesome.ENVELOPE);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        nameField = new TextField("Name");
        details.addComponent(nameField);
        
        Label section = new Label("Contact Info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        emailField = new TextField("Email");
        emailField.setWidth("100%");
        emailField.setRequired(true);
        details.addComponent(emailField);

        phoneField = new TextField("Phone");
        phoneField.setWidth("100%");
        details.addComponent(phoneField);

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    fieldGroup.commit();
                   
                    Notification success = new Notification(
                            "Profile updated successfully");
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());

					// TODO AddressbookEventBus.post(new ProfileUpdatedEvent());
                    Customer customer = fieldGroup.getItemDataSource().getBean();
                    AddressbookUI.getCustomerService().save(customer);
                    close();
                } catch (CommitException e) {
                    Notification.show("Error while updating customer",
                            Type.ERROR_MESSAGE);
                }

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public static void open(final Customer customer) {
        AddressbookEventBus.post(new CloseOpenWindowsEvent());
        Window w = new EditCustomerDetailsWindow(customer);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}
