package com.addressbook.ui.vaadin.component;

import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.model.User;
import com.addressbook.model.User.Role;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.addressbook.CustomersLoader;
import com.addressbook.ui.vaadin.event.AddressbookEvent.AddressbookLineRemovedEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.AddressbookLineUpdatedEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class CustomersListComponent extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	private final Grid customersGrid;
	private Button addBtn;
	private Button editBtn;
	private Button removeBtn;
	private Button favoriteBtn;
	private Button shareBtn;

	private Customer selectedCustomer;

	private List<Customer> customersList;

	private CustomersLoader customersLoader;

	public CustomersListComponent(CustomersLoader customersLoader) {
		this.customersLoader = customersLoader;
		this.customersList = customersLoader.loadCustomers();
		addStyleName("customers-list");
		setSizeFull();

		addComponent(buildToolbar());
		customersGrid = buildCustomersGrid(customersList);
		addComponent(customersGrid);
		setExpandRatio(customersGrid, 1);

		Responsive.makeResponsive(this);

		AddressbookEventBus.register(this);
	}

	private Component buildToolbar() {
		addBtn=buildAddButton(true);
		editBtn=buildEditButton(false);
		removeBtn = buildRemoveButton(false);
		favoriteBtn = buildFavoriteButton(false);
		shareBtn=buildShareButton(false);
		HorizontalLayout header = new HorizontalLayout(buildFilter(),
				favoriteBtn, shareBtn);
		if (hasAdminRole()) {
			header.addComponents(editBtn, removeBtn, addBtn);
		}
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);

		return header;
	}

	private Button buildRemoveButton(boolean enabled) {
		return buildButton("REMOVE_ADDRESS","remove address", FontAwesome.REMOVE, enabled, e -> {
			if (selectedCustomer!=null) {
				getUI().addWindow(new CustomerRemoveWindow(selectedCustomer));
			} else {
				Notification.show("You must select a address !!");
			}
		});
	}

	private Button buildAddButton(boolean enabled) {
		Button button = buildButton("ADD_ADDRESS","Add new address", FontAwesome.PLUS, enabled,
				e -> getUI().addWindow(new CustomerEditWindow(new Customer("","",""))));
		button.setEnabled(true);
		return button;
	}

	private Button buildEditButton(boolean enabled) {
		return buildButton("EDIT_ADDRESS","Edit Address", FontAwesome.EDIT, enabled, e -> {
			if (selectedCustomer!=null) {
				getUI().addWindow(new CustomerEditWindow(selectedCustomer));
			} else {
				Notification.show("You must select a address !!");
			}
		});
	}

	private Button buildShareButton(boolean enabled) {
		return buildButton("SHARE_ADDRESS","Share Address", FontAwesome.SHARE, enabled, e -> Notification.show("Not implemented"));
	}

	private Button buildFavoriteButton(boolean enabled) {
		return buildButton("FAVORITE_ADDRESS","Favorite Address", FontAwesome.STAR_O, enabled, e ->  {
			if (selectedCustomer!=null) {
				if (getUserCustomers().contains(selectedCustomer)) {
					AddressbookUI.getUserService().removeFavoriteCustomer(getCurrentUser().getId(), selectedCustomer);
					ShowNotificationMessage("Addressbook of "+ selectedCustomer.getName() +" is removed from my favorite list");
					refreshDataGrid();
				} else {
					AddressbookUI.getUserService().addFavoriteCustomer(getCurrentUser().getId(), selectedCustomer);
					ShowNotificationMessage("Addressbook of "+ selectedCustomer.getName() +" is now in my favorite list");
				}
			} else {
				Notification.show("You must select a address !!");
			}
		});
	}

	private void ShowNotificationMessage(String message) {
		Notification success = new Notification(message);
		success.setDelayMsec(2000);
		success.setStyleName("bar success small");
		success.setPosition(Position.BOTTOM_CENTER);
		success.show(Page.getCurrent());

	}

	private List<Customer> getUserCustomers() {
		return AddressbookUI.getUserService().findCustomersByUser(getCurrentUser().getId());
	}

	private Button buildButton(String id, String description, Resource icon, boolean enabled, ClickListener clickListener) {
		Button button = new Button();
		button.setId(id);
		button.setIcon(icon);
		button.addStyleName("small-icon");
		button.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		button.setDescription(description);
		button.addClickListener(clickListener);
		button.setEnabled(enabled);
		return button;
	}

	private Component buildFilter() {
		final TextField filter = new TextField();
		filter.setInputPrompt("Filter");
		filter.setIcon(FontAwesome.SEARCH);
		filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		filter.addTextChangeListener(e -> {
			List<Customer> customersDataList = (e.getText().isEmpty()) ? customersList: AddressbookUI.getCustomerService().findByNameOrEmailOrPhone(e.getText());
			customersGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customersDataList ));
		});

		filter.addShortcutListener(
				new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
					@Override
					public void handleAction(final Object sender,
							final Object target) {
						filter.clear();
						customersGrid.setContainerDataSource(new BeanItemContainer<Customer>(Customer.class, customersList));
					}
				});
		return filter;

	}

	private Grid buildCustomersGrid(List<Customer> customersList) {
		Grid grid = new Grid();

		grid.setWidth("100%");
		grid.setHeight("90%");

		BeanItemContainer<Customer> customerContainer = new BeanItemContainer<Customer>(Customer.class, customersList);
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
		grid.removeColumn("users");
		grid.setColumnReorderingAllowed(true);

		grid.setDetailsGenerator(rowReference -> {
			HorizontalLayout layout = new HorizontalLayout(buildFavoriteButton(true),buildShareButton(true));
			if (hasAdminRole()) {
				layout.addComponents(buildEditButton(true),buildRemoveButton(true));
			}
			layout.setMargin(true);
			layout.setSpacing(true);
			return layout;
		});

		grid.addItemClickListener(e -> {
			if (e.isDoubleClick()) {
				Object itemId = e.getItemId();
				grid.setDetailsVisible(itemId, !grid.isDetailsVisible(itemId));
			}
		});

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addSelectionListener(e-> {
			if (e.getSelected().iterator().hasNext()) {
				selectedCustomer = (Customer) e.getSelected().iterator().next();
				enableButtons(true);
			} else {
				selectedCustomer = null;
				enableButtons(false);
			}
		});

		grid.setEditorEnabled(false);

		Responsive.makeResponsive(grid);

		return grid;
	}

	protected void enableButtons(boolean state) {
		editBtn.setEnabled(state);
		removeBtn.setEnabled(state);
		shareBtn.setEnabled(state);
		favoriteBtn.setEnabled(state);
	}


	@Subscribe
	public void updateAddressbookLine(final AddressbookLineUpdatedEvent event) {
		refreshDataGrid();
	}

	@Subscribe
	public void removeAddressbookLine(final AddressbookLineRemovedEvent event) {
		Customer selectedItem = (Customer) customersGrid.getSelectedRow();
		customersGrid.getContainerDataSource().removeItem(selectedItem);
	}
	
	private User getCurrentUser() {
		return (User) VaadinSession.getCurrent()
				.getAttribute(User.class.getName());
	}
	
	private boolean hasAdminRole() {
		return Role.ADMIN.equals(getCurrentUser().getRole());
	}

	private void refreshDataGrid(){
		List<Customer> customersList = customersLoader.loadCustomers();
		BeanItemContainer<Customer> customerContainer = new BeanItemContainer<Customer>(Customer.class, customersList);
		customersGrid.setContainerDataSource(customerContainer);
	}
}
