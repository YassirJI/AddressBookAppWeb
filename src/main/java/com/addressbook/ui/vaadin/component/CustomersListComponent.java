package com.addressbook.ui.vaadin.component;

import java.util.List;

import com.addressbook.model.Customer;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.DetailsGenerator;
import com.vaadin.ui.Grid.RowReference;
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

		setSizeFull();
		addStyleName("customers-list");
		AddressbookEventBus.register(this);

		addComponent(buildToolbar());
		customersGrid = buildCustomersGrid(customersList);
		addComponent(customersGrid);
	}

	private Component buildToolbar() {
		addBtn=buildAddButton();
		editBtn=buildEditButton();
		removeBtn = buildRemoveButton();
		favoriteBtn = buildFavoriteButton();
		shareBtn=buildShareButton();
		HorizontalLayout header = new HorizontalLayout(buildFilter(), 
				favoriteBtn, shareBtn, editBtn, removeBtn, addBtn);
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);

		return header;
	}

	private Button buildRemoveButton() {
		return buildButton("REMOVE_ADDRESS","remove address", FontAwesome.REMOVE, new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new CustomerEditWindow(null));

			}});
	}

	private Button buildAddButton() {
		Button button = buildButton("ADD_ADDRESS","Add new address", FontAwesome.PLUS, new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new CustomerEditWindow(null));

			}});
		button.setEnabled(true);
		return button;
	}

	private Button buildEditButton() {
		return buildButton("EDIT_ADDRESS","Edit Address", FontAwesome.EDIT, new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (selectedCustomer!=null) {
					getUI().addWindow(
							new CustomerEditWindow(selectedCustomer));
				} else {
					Notification.show("You must select a address !!");
				}
			}});
	}

	private Button buildShareButton() {
		return buildButton("SHARE_ADDRESS","Share Address", FontAwesome.SHARE, new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new AddressbookEdit(CustomersListComponent.this, null));
			}
		});
	}

	private Button buildFavoriteButton() {
		return buildButton("FAVORITE_ADDRESS","Favorite Address", FontAwesome.STAR_O, new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new AddressbookEdit(CustomersListComponent.this, null));
			}
		});
	}

	private Button buildButton(String id, String description, Resource icon, ClickListener clickListener) {
		Button button = new Button();
		button.setId(id);
		button.setIcon(icon);
		button.addStyleName("small-icon");
		button.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		button.setDescription(description);
		button.addClickListener(clickListener);
		button.setEnabled(false);
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
		grid.setHeight("100%");

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
		grid.setColumnReorderingAllowed(true);

		grid.setDetailsGenerator(new DetailsGenerator() {
			@Override
			public Component getDetails(RowReference rowReference) {
				Customer order = (Customer) rowReference.getItemId();

				HorizontalLayout layout = new HorizontalLayout(buildEditButton(),buildRemoveButton(), buildFavoriteButton(),buildShareButton());
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

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				selectedCustomer = (Customer) event.getSelected().iterator().next();
				//				getUI().addWindow(new CustomerDetailsWindow(selectedCustomer));
				enableButtons(true);
			}
		});

		grid.setEditorEnabled(false);

		return grid;
	}

	protected void enableButtons(boolean state) {
		editBtn.setEnabled(state);
		removeBtn.setEnabled(state);
		shareBtn.setEnabled(state);
		favoriteBtn.setEnabled(state);
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
}
