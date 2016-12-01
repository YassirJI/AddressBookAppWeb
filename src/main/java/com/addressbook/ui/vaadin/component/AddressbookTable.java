package com.addressbook.ui.vaadin.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class AddressbookTable extends Table {

	public AddressbookTable() {
		setCaption("My contacts");

		addStyleName(ValoTheme.TABLE_BORDERLESS);
		addStyleName(ValoTheme.TABLE_NO_STRIPES);
		addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		addStyleName(ValoTheme.TABLE_SMALL);
		setSortEnabled(false);
		setColumnAlignment("name", Align.RIGHT);
		setRowHeaderMode(RowHeaderMode.INDEX);
		setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		setSizeFull();

		List<Customer> customers = new ArrayList<Customer>(
				AddressbookUI.getCustomerService().findAll());
		Collections.sort(customers, new Comparator<Customer>() {
			@Override
			public int compare(final Customer o1, final Customer o2) {
				return o2.getId().compareTo(o1.getId());
			}
		});

		setContainerDataSource(new BeanItemContainer<Customer>(
				Customer.class, customers.subList(0, 10)));

		setVisibleColumns("name", "phone");
		setColumnHeaders("Name", "Phone");
		setColumnExpandRatio("name", 2);
		setColumnExpandRatio("phone", 1);

		setSortContainerPropertyId("phone");
		setSortAscending(false);
	}

}
