package com.addressbook.ui.vaadin.view;

import com.addressbook.ui.vaadin.addressbook.AddressbookView;
import com.addressbook.ui.vaadin.addressbook.FavoritesView;
import com.addressbook.ui.vaadin.addressbook.ImportListView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum AddressbookViewType {
	ADDRESSBOOK("All addresses", AddressbookView.class, FontAwesome.LIST_UL, false),
	FAVORITES("Favorites list", FavoritesView.class, FontAwesome.STAR_O, false),
	IMPORT("Import list", ImportListView.class, FontAwesome.UPLOAD, true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private AddressbookViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static AddressbookViewType getByViewName(final String viewName) {
    	AddressbookViewType result = null;
        for (AddressbookViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
