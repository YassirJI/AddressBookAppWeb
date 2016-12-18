package com.addressbook.ui.vaadin.addressbook;

import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;


public final class AddressbookView extends AbstractListView {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Customer> loadCustomers() {
		return  AddressbookUI.getCustomerService().findAll();
	}

	@Override
	protected String getToolbarTitle() {
		return "My addresses";
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}
}
