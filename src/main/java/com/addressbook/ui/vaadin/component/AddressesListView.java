package com.addressbook.ui.vaadin.component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial"})
public final class AddressesListView extends VerticalLayout implements View {

	private final Grid grid;
	private Button createReport;
	private String filterValue = "";

	public AddressesListView() {
		setCaption("My contacts");

		setSizeFull();
		addStyleName("transactions");
		AddressbookEventBus.register(this);

		addComponent(buildToolbar());

		grid = buildGrid();

		HorizontalLayout main = new HorizontalLayout(grid);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setExpandRatio(grid, 1);

		addComponent(main);
	}

	private Component buildToolbar() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);


		createReport = buildCreateReport();
		HorizontalLayout tools = new HorizontalLayout(buildFilter(),
				createReport);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private Button buildCreateReport() {
		final Button createReport = new Button("Create Report");
		createReport.setDescription(
				"Create a new report from the selected transactions");
		createReport.addClickListener(event -> createNewReportFromSelection());
		createReport.setEnabled(false);
		return createReport;
	}

	private Component buildFilter() {
		final TextField filter = new TextField();

		filter.addValueChangeListener(event -> {

			Collection<Customer> customers = findCustomers().stream().filter(customer -> {
				filterValue = filter.getValue().trim().toLowerCase();
				return passesFilter(customer.getName())
						|| passesFilter(customer.getPhone())
						|| passesFilter(customer.getEmail());
			}).collect(Collectors.toList());

			grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
		});

		filter.setInputPrompt("Filter");
		filter.setIcon(FontAwesome.SEARCH);
		filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filter.addShortcutListener(
				new ShortcutListener("Clear", KeyCode.ESCAPE, null) {
					@Override
					public void handleAction(final Object sender,
							final Object target) {
						filter.setValue("");
					}
				});
		return filter;
	}

	private List<Customer> findCustomers() {
		return AddressbookUI.getCustomerService().findAll();
	}

	private Grid buildGrid() {
		final Grid grid = new Grid();
		grid.setSizeFull();

		grid.setColumns("name", "phone", "email");
		grid.setColumnReorderingAllowed(true);

		grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, findCustomers()));

		grid.addSelectionListener(event -> createReport
				.setEnabled(!grid.getSelectedRows().isEmpty()));
		return grid;
	}


	void createNewReportFromSelection() {
		//        grid.getSelectedRow().ifPresent(customer -> {
		//            UI.getCurrent().getNavigator()
		//                    .navigateTo(AddressbookViewType.REPORTS.getViewName());
		//            AddressbookEventBus.post(new TransactionReportEvent(
		//                    Collections.singletonList(customer)));
		//        });
	}

	private boolean passesFilter(String subject) {
		if (subject == null) {
			return false;
		}
		return subject.trim().toLowerCase().contains(filterValue);
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}
}
