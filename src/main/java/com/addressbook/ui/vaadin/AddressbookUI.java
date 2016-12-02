package com.addressbook.ui.vaadin;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.addressbook.model.User;
import com.addressbook.service.CustomerService;
import com.addressbook.service.UserService;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.UserLoggedOutEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.UserLoginRequestedEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.addressbook.ui.vaadin.view.LoginView;
import com.addressbook.ui.vaadin.view.MainView;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("addressbooktheme")
@Widgetset("com.vaadin.DefaultWidgetSet")
@Title("My Address Book")
@SuppressWarnings("serial")
@VaadinSessionScope
@SpringUI
@SpringViewDisplay
public final class AddressbookUI extends UI {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;

	private final AddressbookEventBus addressbookEventbus = new AddressbookEventBus();

	@Override
	protected void init(final VaadinRequest request) {
		setLocale(Locale.US);
		Responsive.makeResponsive(this);
		addStyleName(ValoTheme.UI_WITH_MENU);

		AddressbookEventBus.register(this);

		updateContent();
	}

	/**
	 * Updates the correct content for this UI based on the current user status.
	 * If the user is logged in with appropriate privileges, main view is shown.
	 * Otherwise login view is shown.
	 */
	private void updateContent() {
		User user = (User) VaadinSession.getCurrent()
				.getAttribute(User.class.getName());
		if (user != null && "admin".equals(user.getRole())) {
			// Authenticated user
			setContent(new MainView());
			removeStyleName("loginview");
			getUI().getNavigator().navigateTo(MainView.VIEWNAME);
		} else {
			setContent(new LoginView());
			addStyleName("loginview");
		}
	}

	@Subscribe
	public void userLoginRequested(final UserLoginRequestedEvent event) {
		User user = userService.authenticate(event.getUserName(),
				event.getPassword());
		VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
		updateContent();
	}

	@Subscribe
	public void userLoggedOut(final UserLoggedOutEvent event) {
		VaadinSession.getCurrent().close();
		Page.getCurrent().reload();
	}

	@Subscribe
	public void closeOpenWindows(final CloseOpenWindowsEvent event) {
		for (Window window : getWindows()) {
			window.close();
		}
	}

	public static CustomerService getCustomerService() {
		return ((AddressbookUI) getCurrent()).customerService;
	}

	public static UserService getUserService() {
		return ((AddressbookUI) getCurrent()).userService;
	}

	public static AddressbookEventBus getAddressbookEventbus() {
		return ((AddressbookUI) getCurrent()).addressbookEventbus;
	}

}
