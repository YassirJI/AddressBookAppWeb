package com.addressbook.ui.vaadin.view;

import com.addressbook.model.User;
import com.addressbook.ui.vaadin.component.ProfileWindow;
import com.addressbook.ui.vaadin.event.AddressbookEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.PostViewChangeEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.ProfileUpdatedEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.UserLoggedOutEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AddressbookMenu extends CustomComponent {

	    public static final String ID = "addressbook-menu";
	    private static final String STYLE_VISIBLE = "valo-menu-visible";
	    private MenuItem settingsItem;

	    public AddressbookMenu() {
	        setPrimaryStyleName("valo-menu");
	        setId(ID);
	        setSizeUndefined();

	        AddressbookEventBus.register(this);

	        setCompositionRoot(buildContent());
	    }

	    private Component buildContent() {
	        final CssLayout menuContent = new CssLayout();
	        menuContent.addStyleName("sidebar");
	        menuContent.addStyleName(ValoTheme.MENU_PART);
	        menuContent.addStyleName("no-vertical-drag-hints");
	        menuContent.addStyleName("no-horizontal-drag-hints");
	        menuContent.setWidth(null);
	        menuContent.setHeight("100%");

	        menuContent.addComponent(buildTitle());
	        menuContent.addComponent(buildUserMenu());
	        menuContent.addComponent(buildMenuItems());

	        return menuContent;
	    }

	    private Component buildTitle() {
	        Label logo = new Label("My <strong>Addressbook</strong>",
	                ContentMode.HTML);
	        logo.setSizeUndefined();
	        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
	        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
	        logoWrapper.addStyleName("valo-menu-title");
	        return logoWrapper;
	    }

	    private User getCurrentUser() {
	        return (User) VaadinSession.getCurrent()
	                .getAttribute(User.class.getName());
	    }

	    private Component buildUserMenu() {
	        final MenuBar settings = new MenuBar();
	        settings.addStyleName("user-menu");
	        final User user = getCurrentUser();
	        settingsItem = settings.addItem("",
	                new ThemeResource("img/profile-pic-300px.jpg"), null);
	        updateUserName(null);
	        settingsItem.addItem("Edit Profile", new Command() {
	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                ProfileWindow.open(user);
	            }
	        });
	        
	        settingsItem.addSeparator();
	        settingsItem.addItem("Sign Out", new Command() {
	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                AddressbookEventBus.post(new UserLoggedOutEvent());
	            }
	        });
	        return settings;
	    }

	    private Component buildMenuItems() {
	        CssLayout menuItemsLayout = new CssLayout();
	        menuItemsLayout.addStyleName("valo-menuitems");

	        for (final AddressbookViewType view : AddressbookViewType.values()) {
	            Component menuItemComponent = new ValoMenuItemButton(view);

	            if (view == AddressbookViewType.ADDRESSBOOK) {
	                menuItemComponent = buildBadgeWrapper(menuItemComponent);
	            }
	            menuItemsLayout.addComponent(menuItemComponent);
	        }
	        return menuItemsLayout;

	    }

	    private Component buildBadgeWrapper(final Component menuItemButton) {
	        CssLayout addressbookWrapper = new CssLayout(menuItemButton);
	        addressbookWrapper.addStyleName("badgewrapper");
	        addressbookWrapper.addStyleName(ValoTheme.MENU_ITEM);
	        return addressbookWrapper;
	    }

	    @Subscribe
	    public void postViewChange(final PostViewChangeEvent event) {
	        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
	    }

	    @Subscribe
	    public void updateUserName(final ProfileUpdatedEvent event) {
	        User user = getCurrentUser();
	        settingsItem.setText(user.getFirstName() + " " + user.getLastName());
	    }

	    public final class ValoMenuItemButton extends Button {

	        private static final String STYLE_SELECTED = "selected";

	        private final AddressbookViewType view;

	        public ValoMenuItemButton(final AddressbookViewType view) {
	            this.view = view;
	            setPrimaryStyleName("valo-menu-item");
	            setIcon(view.getIcon());
	            setCaption(view.getViewName().substring(0, 1).toUpperCase()
	                    + view.getViewName().substring(1));
	            AddressbookEventBus.register(this);
	            addClickListener(e -> { getUI().getNavigator().navigateTo(view.getViewName()); });
	        }

	        @Subscribe
	        public void postViewChange(final AddressbookEvent.PostViewChangeEvent event) {
	            removeStyleName(STYLE_SELECTED);
	            if (event.getView() == view) {
	                addStyleName(STYLE_SELECTED);
	            }
	        }
	    }
}
