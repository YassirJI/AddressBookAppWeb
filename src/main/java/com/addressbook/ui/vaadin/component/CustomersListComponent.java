package com.addressbook.ui.vaadin.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.addressbook.AddressbookEdit;
import com.addressbook.ui.vaadin.addressbook.AddressbookEdit.AddressbookEditListener;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class CustomersListComponent extends VerticalLayout implements AddressbookEditListener{

	private static final long serialVersionUID = 1L;

	private final Grid customersGrid;
	private Button addBtn;
	private Button editBtn;
	private Button removeBtn;
	private Button favoriteBtn;
	private Button shareBtn;

	private Customer selectedCustomer;

	public CustomersListComponent() {
		setSizeFull();
		addStyleName("customers-list");
		AddressbookEventBus.register(this);

		addComponent(buildToolbar());
		customersGrid = buildCustomersGrid();
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
		Button removeBtn = new Button();
		removeBtn.setId("REMOVE_ADDRESS");
		removeBtn.setIcon(FontAwesome.REMOVE);
		removeBtn.addStyleName("icon-remove");
		removeBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		removeBtn.setDescription("Remove Address");
		removeBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new AddressbookEdit(CustomersListComponent.this, null));
			}
		});
		removeBtn.setEnabled(false);
		return removeBtn;
	}

	private Button buildAddButton() {
		Button addBtn = new Button();
		addBtn.setId("ADD_ADDRESS");
		addBtn.setIcon(FontAwesome.PLUS);
		addBtn.addStyleName("icon-add");
		addBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		addBtn.setDescription("Add Address");
		addBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new CustomerEditWindow(null));

			}
		});
		return addBtn;
	}

	private Button buildEditButton() {
		Button editBtn = new Button();
		editBtn.setId("EDIT_ADDRESS");
		editBtn.setIcon(FontAwesome.EDIT);
		editBtn.addStyleName("icon-edit");
		editBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		editBtn.setDescription("Edit Address");
		editBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (selectedCustomer!=null) {
					getUI().addWindow(
							new CustomerEditWindow(selectedCustomer));
				} else {
					Notification.show("You must select a address !!");
				}				
			}
		});
		editBtn.setEnabled(false);
		return editBtn;
	}

	private Button buildShareButton() {
		Button shareBtn = new Button();
		shareBtn.setId("SHARE_ADDRESS");
		shareBtn.setIcon(FontAwesome.SHARE);
		shareBtn.addStyleName("icon-share");
		shareBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		shareBtn.setDescription("Share Address");
		shareBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new AddressbookEdit(CustomersListComponent.this, null));
			}
		});
		shareBtn.setEnabled(false);
		return shareBtn;
	}

	private Button buildFavoriteButton() {
		Button favoriteBtn = new Button();
		favoriteBtn.setId("FAVORITE_ADDRESS");
		favoriteBtn.setIcon(FontAwesome.STAR_O);
		favoriteBtn.addStyleName("icon-favorite");
		favoriteBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		favoriteBtn.setDescription("Favorite Address");
		favoriteBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				getUI().addWindow(
						new AddressbookEdit(CustomersListComponent.this, null));
			}
		});
		favoriteBtn.setEnabled(false);
		return favoriteBtn;
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
						customersGrid.setContainerDataSource(new BeanItemContainer<Customer>(Customer.class, findAllCustomers()));
					}
				});
		return filter;

	}

	private Grid buildCustomersGrid() {
		final Grid grid = new Grid();
		grid.setColumns("name", "phone", "email");
		List<Customer> customers = findAllCustomers();
		Collections.sort(customers, new Comparator<Customer>() {
			@Override
			public int compare(final Customer o1, final Customer o2) {
				return o2.getName().compareTo(o1.getName());
			}
		});

		grid.setContainerDataSource(new BeanItemContainer<Customer>(
				Customer.class, customers));

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				selectedCustomer = (Customer) event.getSelected().iterator().next();
				getUI().addWindow(new CustomerDetailsWindow(selectedCustomer));
				enableButtons(true);
			}
		});
		return grid;
	}

	protected void enableButtons(boolean state) {
		addBtn.setEnabled(state);
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

	private List<Customer> findAllCustomers() {
		return AddressbookUI.getCustomerService().findAll();
	}
}
