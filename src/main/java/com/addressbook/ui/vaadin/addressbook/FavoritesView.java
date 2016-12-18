package com.addressbook.ui.vaadin.addressbook;

import java.util.List;

import com.addressbook.model.Customer;
import com.addressbook.model.User;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.google.common.collect.Sets;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;

public final class FavoritesView extends AbstractListView{

	private static final long serialVersionUID = 1L;


	@Override
	public List<Customer> loadCustomers() {
		List<Customer> customers = AddressbookUI.getUserService().findCustomersByUser(getCurrentUser().getId());
		refreshCurrentUserCustomers(customers);
		return customers;
	}

    private void refreshCurrentUserCustomers(List<Customer> customers) {
    	getCurrentUser().setCustomers(Sets.newHashSet(customers));
	}

	private User getCurrentUser() {
        return (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
    }

	@Override
	protected String getToolbarTitle() {
		return "My favorites";
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}
}
