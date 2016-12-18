package com.addressbook.ui.vaadin;

import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.PostViewChangeEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.addressbook.ui.vaadin.view.AddressbookViewType;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
public class AddressbookNavigator extends Navigator {

	private static final AddressbookViewType ERROR_VIEW = AddressbookViewType.ADDRESSBOOK;
	private ViewProvider errorViewProvider;

	public AddressbookNavigator(final ComponentContainer container) {
		super(UI.getCurrent(), container);

		initViewChangeListener();
		initViewProviders();

	}

	private void initViewChangeListener() {
		addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event) {
				AddressbookViewType view = AddressbookViewType.getByViewName(event
						.getViewName());
				AddressbookEventBus.post(new PostViewChangeEvent(view));
				AddressbookEventBus.post(new CloseOpenWindowsEvent());

			}
		});
	}

	private void initViewProviders() {
		for (final AddressbookViewType viewType : AddressbookViewType.values()) {
			ViewProvider viewProvider = new ClassBasedViewProvider(
					viewType.getViewName(), viewType.getViewClass()) {
				private View cachedInstance;

				@Override
				public View getView(final String viewName) {
					View result = null;
					if (viewType.getViewName().equals(viewName)) {
						if (viewType.isStateful()) {
							if (cachedInstance == null) {
								cachedInstance = super.getView(viewType
										.getViewName());
							}
							result = cachedInstance;
						} else {
							result = super.getView(viewType.getViewName());
						}
					}
					return result;
				}
			};

			if (viewType == ERROR_VIEW) {
				errorViewProvider = viewProvider;
			}

			addProvider(viewProvider);
		}

		setErrorProvider(new ViewProvider() {
			@Override
			public String getViewName(final String viewAndParameters) {
				return ERROR_VIEW.getViewName();
			}

			@Override
			public View getView(final String viewName) {
				return errorViewProvider.getView(ERROR_VIEW.getViewName());
			}
		});
	}
}