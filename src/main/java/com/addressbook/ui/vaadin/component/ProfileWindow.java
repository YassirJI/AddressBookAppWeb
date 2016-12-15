package com.addressbook.ui.vaadin.component;

import java.util.Arrays;

import com.addressbook.model.User;
import com.addressbook.ui.vaadin.AddressbookUI;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEvent.ProfileUpdatedEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ProfileWindow extends Window {

	public static final String ID = "profilewindow";

	private final BeanFieldGroup<User> fieldGroup;

	@PropertyId("firstName")
	private TextField firstNameField;
	@PropertyId("lastName")
	private TextField lastNameField;
	@PropertyId("title")
	private ComboBox titleField;
	@PropertyId("email")
	private TextField emailField;
	@PropertyId("location")
	private TextField locationField;
	@PropertyId("phone")
	private TextField phoneField;

	private ProfileWindow(final User user) {
		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		addCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(false);
		setHeight(90.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		setContent(content);

		TabSheet detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		detailsWrapper.addComponent(buildProfileTab());

		content.addComponent(buildFooter());

		fieldGroup = new BeanFieldGroup<User>(User.class);
		fieldGroup.bindMemberFields(this);
		fieldGroup.setItemDataSource(user);
	}

	private Component buildProfileTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Profile");
		root.setIcon(FontAwesome.USER);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addStyleName("profile-form");

		VerticalLayout pic = new VerticalLayout();
		pic.setSizeUndefined();
		pic.setSpacing(true);
		Image profilePic = new Image(null,
				new ThemeResource("img/profile-pic-300px.jpg"));
		profilePic.setWidth(100.0f, Unit.PIXELS);
		pic.addComponent(profilePic);

		root.addComponent(pic);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		firstNameField = new TextField("First Name");
		details.addComponent(firstNameField);
		lastNameField = new TextField("Last Name");
		details.addComponent(lastNameField);

		titleField = new ComboBox("Title",
				Arrays.asList("Mr.", "Mrs.", "Ms."));
		details.addComponent(titleField);


		Label section = new Label("Contact Info");
		section.addStyleName(ValoTheme.LABEL_H4);
		section.addStyleName(ValoTheme.LABEL_COLORED);
		details.addComponent(section);

		emailField = new TextField("Email");
		emailField.setWidth("100%");
		emailField.setRequired(true);
		details.addComponent(emailField);

		locationField = new TextField("Location");
		locationField.setWidth("100%");
		locationField.setRequired(true);
		details.addComponent(locationField);

		phoneField = new TextField("Phone");
		phoneField.setWidth("100%");
		details.addComponent(phoneField);

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener( event-> {
				try {
					fieldGroup.commit();

					Notification success = new Notification(
							"Profile updated successfully");
					success.setDelayMsec(2000);
					success.setStyleName("bar success small");
					success.setPosition(Position.BOTTOM_CENTER);
					success.show(Page.getCurrent());

					AddressbookEventBus.post(new ProfileUpdatedEvent());
					User user = fieldGroup.getItemDataSource().getBean();
					AddressbookUI.getUserService().save(user);
					refreshCurrentUserDetails(user);
					close();
				} catch (CommitException e) {
					Notification.show("Error while updating profile",
							Type.ERROR_MESSAGE);
				}
		});
		ok.focus();

		Button close = new Button("Close");
		close.addStyleName(ValoTheme.BUTTON_PRIMARY);
		close.addClickListener(e-> close());
		HorizontalLayout buttonsLayout = new HorizontalLayout(ok, close);
		buttonsLayout.setSpacing(true);
		footer.addComponent(buttonsLayout);
		footer.setComponentAlignment(buttonsLayout, Alignment.TOP_RIGHT);
		return footer;
	}

	private void refreshCurrentUserDetails(User user) {
		VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
	}
	
	public static void open(final User user) {
		AddressbookEventBus.post(new CloseOpenWindowsEvent());
		Window w = new ProfileWindow(user);
		UI.getCurrent().addWindow(w);
		w.focus();
	}
}
