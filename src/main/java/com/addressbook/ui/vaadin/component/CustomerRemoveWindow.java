package com.addressbook.ui.vaadin.component;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.event.AddressbookEvent.AddressbookLineRemovedEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class CustomerRemoveWindow extends Window {

	public static final String ID = "removecustomerwindow";

	private Customer customer;

	public CustomerRemoveWindow(final Customer customer) {
		this.customer = customer;

		addStyleName("customer-edit-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		addCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(false);
		setHeight(200, Unit.PIXELS);
		setWidth(600, Unit.PIXELS);

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

		detailsWrapper.addComponent(buildContentTab());

		content.addComponent(buildFooter());
	}

	private Component buildContentTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Remove addressbook line");
		root.setIcon(FontAwesome.BOOK);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addStyleName("customer-form");

		Label section = new Label("Are you sure you want to delete this addressbook (Name : " + customer.getName()+ ") ?");
		section.addStyleName(ValoTheme.LABEL_H4);
		section.addStyleName(ValoTheme.LABEL_BOLD);
		root.addComponent(section);

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button cancel = new Button("Cancel");
		cancel.addClickListener(e -> close());
		cancel.setClickShortcut(KeyCode.ESCAPE, null);

		Button save = new Button("Confirm");
		save.addStyleName(ValoTheme.BUTTON_PRIMARY);
		save.addClickListener(e -> {
			AddressbookUI.getCustomerService().delete(customer);

			Notification success = new Notification("Addressbook deleted successfully");
			success.setDelayMsec(2000);
			success.setStyleName("bar success small");
			success.setPosition(Position.BOTTOM_CENTER);
			success.show(Page.getCurrent());

			AddressbookEventBus.post(new AddressbookLineRemovedEvent());
			close();
		});
		save.setClickShortcut(KeyCode.ENTER, null);

		footer.addComponents(cancel, save);
		footer.setExpandRatio(cancel, 1);
		footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final Customer customer) {
		AddressbookEventBus.post(new CloseOpenWindowsEvent());
		Window w = new CustomerRemoveWindow(customer);
		UI.getCurrent().addWindow(w);
		w.focus();
	}
}
