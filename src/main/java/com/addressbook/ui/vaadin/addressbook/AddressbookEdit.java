package com.addressbook.ui.vaadin.addressbook;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Simple name editor Window.
 */
@SuppressWarnings("serial")
public class AddressbookEdit extends Window {

    private final TextField nameField = new TextField("Name");
    private final CustomerListener listener;

    public AddressbookEdit(final CustomerListener listener,
            final String currentName) {
        this.listener = listener;
        setCaption("Edit Addressbook");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth(300.0f, Unit.PIXELS);

        addStyleName("edit-addressbook");

        setContent(buildContent(currentName));
    }

    private Component buildContent(final String currentName) {
        VerticalLayout result = new VerticalLayout();
        result.setMargin(true);
        result.setSpacing(true);

        nameField.setValue(currentName);
        nameField.addStyleName("caption-on-left");
        nameField.focus();

        result.addComponent(nameField);
        result.addComponent(buildFooter());

        return result;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                close();
            }
        });
        cancel.setClickShortcut(KeyCode.ESCAPE, null);

        Button save = new Button("Save");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                listener.addressbookNameEdited(nameField.getValue());
                close();
            }
        });
        save.setClickShortcut(KeyCode.ENTER, null);

        footer.addComponents(cancel, save);
        footer.setExpandRatio(cancel, 1);
        footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
        return footer;
    }

    public interface CustomerListener {
        void addressbookNameEdited(String name);
    }
}
