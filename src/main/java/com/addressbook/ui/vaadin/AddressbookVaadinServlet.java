package com.addressbook.ui.vaadin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/*", name = "AddressbookVaadinServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = AddressbookUI.class, productionMode = false)
class AddressbookVaadinServlet extends VaadinServlet {
	 
	@Override
	    protected final void servletInitialized() throws ServletException {
	        super.servletInitialized();
	        getService().addSessionInitListener(new AddressbookSessionInitListener());
	    }
}