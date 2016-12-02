package com.addressbook.ui.vaadin.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class AddressesListComponent extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private final Table table;
	private Button createReport;
	private String filterValue = "";

	public AddressesListComponent() {
		setCaption("My contacts");

		setSizeFull();
		addStyleName("customers-list");
		AddressbookEventBus.register(this);

		addComponent(buildToolbar());
		table = buildTable();
		addComponent(table);
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

			table.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
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

	private Table buildTable() {
		final Table table = new Table();
		
		table.addStyleName(ValoTheme.TABLE_BORDERLESS);
		table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		table.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		table.addStyleName(ValoTheme.TABLE_SMALL);
		table.setSortEnabled(false);
		table.setColumnAlignment("name", Align.RIGHT);
		table.setRowHeaderMode(RowHeaderMode.INDEX);
		table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		table.setSizeFull();

        List<Customer> customers = findCustomers();
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(final Customer o1, final Customer o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });

        table.setContainerDataSource(new BeanItemContainer<Customer>(
                Customer.class, customers));

        table.setVisibleColumns(new String[]{"name"});
        table.setColumnHeaders(new String[]{"Name"});
        table.setColumnExpandRatio("name", 1);
        table.setSortAscending(false);
        
        return table;
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
}
