package com.addressbook.ui.vaadin.component;

import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.model.User;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.addressbook.AddressbookEdit;
import com.addressbook.ui.vaadin.addressbook.AddressbookEdit.CustomerListener;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
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


public final class CustomersListComponent extends VerticalLayout implements CustomerListener{

	private static final long serialVersionUID = 1L;

	private final Grid customersGrid;
	private Button addBtn;
	private Button editBtn;
	private Button removeBtn;
	private Button favoriteBtn;
	private Button shareBtn;

	private Customer selectedCustomer;

	private List<Customer> customersList;

	public CustomersListComponent(List<Customer> customersList) {
		this.customersList = customersList;

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
				favoriteBtn, shareBtn, editBtn, removeBtn, addBtn);
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);

		return header;
	}

	private Button buildRemoveButton(boolean enabled) {
		return buildButton("REMOVE_ADDRESS","remove address", FontAwesome.REMOVE, enabled,
				e -> getUI().addWindow(new CustomerEditWindow(null)));
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
		return buildButton("SHARE_ADDRESS","Share Address", FontAwesome.SHARE, enabled, e ->
		getUI().addWindow(new AddressbookEdit(CustomersListComponent.this, null)));
	}

	private Button buildFavoriteButton(boolean enabled) {
		return buildButton("FAVORITE_ADDRESS","Favorite Address", FontAwesome.STAR_O, enabled, e ->  {
			if (selectedCustomer!=null) {
				if (getUserCustomers().contains(selectedCustomer)) {
					AddressbookUI.getUserService().removeFavoriteCustomer(getCurrentUser().getId(), selectedCustomer);					
				} else {
					AddressbookUI.getUserService().addFavoriteCustomer(getCurrentUser().getId(), selectedCustomer);
				}
			} else {
				Notification.show("You must select a address !!");
			}
		});
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
			customersGrid.setContainerDataSource(new BeanItemContainer<>(Customer.class, AddressbookUI.getCustomerService().findByName(e.getText())));
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
			//Customer order = (Customer) rowReference.getItemId();

			HorizontalLayout layout = new HorizontalLayout(buildEditButton(true),buildRemoveButton(true), buildFavoriteButton(true),buildShareButton(true));
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
			}
			//				getUI().addWindow(new CustomerDetailsWindow(selectedCustomer));
			enableButtons(true);
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

	private void refreshGrid(Grid grid){
		grid.clearSortOrder();
	}

	void createNewReportFromSelection() {
		//        grid.getSelectedRow().ifPresent(customer -> {
		//            UI.getCurrent().getNavigator()
		//                    .navigateTo(AddressbookViewType.REPORTS.getViewName());
		//            AddressbookEventBus.post(new TransactionReportEvent(
		//                    Collections.singletonList(customer)));
		//        });
	}

	@Override
	public void addressbookNameEdited(final String name) {
		//titleLabel.setValue(name);
	}

	private User getCurrentUser() {
		return (User) VaadinSession.getCurrent()
				.getAttribute(User.class.getName());
	}
}
