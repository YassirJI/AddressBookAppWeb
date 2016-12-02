package com.addressbook.ui.vaadin.component;

import java.util.Date;

import com.addressbook.model.Customer;
import com.addressbook.ui.vaadin.event.AddressbookEvent.CloseOpenWindowsEvent;
import com.addressbook.ui.vaadin.event.AddressbookEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class AddressDetailsWindow extends Window {

    private final Label synopsis = new Label();

    private AddressDetailsWindow(final Customer customer) {
        addStyleName("addressdetailswindow");
        Responsive.makeResponsive(this);

        setCaption(customer.getName());
        center();
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        Panel detailsWrapper = new Panel(buildAddressDetails(customer));
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.PANEL_BORDERLESS);
        detailsWrapper.addStyleName("scroll-divider");
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        content.addComponent(buildFooter());
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Close");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                close();
            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    private Component buildAddressDetails(final Customer customer) {
        HorizontalLayout details = new HorizontalLayout();
//        details.setWidth(100.0f, Unit.PERCENTAGE);
//        details.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
//        details.setMargin(true);
//        details.setSpacing(true);
//
//        final Image coverImage = new Image(null, new ExternalResource(
//                customer.getThumbUrl()));
//        coverImage.addStyleName("cover");
//        details.addComponent(coverImage);
//
//        Component detailsForm = buildDetailsForm(customer);
//        details.addComponent(detailsForm);
//        details.setExpandRatio(detailsForm, 1);

        return details;
    }

//    private Component buildDetailsForm(final Customer customer) {
//        FormLayout fields = new FormLayout();
//        fields.setSpacing(false);
//        fields.setMargin(false);
//
//        Label label;
//        SimpleDateFormat df = new SimpleDateFormat();
//        if (startTime != null) {
//            df.applyPattern("dd-mm-yyyy");
//            label = new Label(df.format(startTime));
//            label.setSizeUndefined();
//            label.setCaption("Date");
//            fields.addComponent(label);
//
//            df.applyPattern("hh:mm a");
//            label = new Label(df.format(startTime));
//            label.setSizeUndefined();
//            label.setCaption("Starts");
//            fields.addComponent(label);
//        }
//
//        if (endTime != null) {
//            label = new Label(df.format(endTime));
//            label.setSizeUndefined();
//            label.setCaption("Ends");
//            fields.addComponent(label);
//        }
//
//        label = new Label(customer.getDuration() + " minutes");
//        label.setSizeUndefined();
//        label.setCaption("Duration");
//        fields.addComponent(label);
//
//        synopsis.setData(customer.getSynopsis());
//        synopsis.setCaption("Synopsis");
//        updateSynopsis(customer, false);
//        fields.addComponent(synopsis);
//
//        final Button more = new Button("More…");
//        more.addStyleName(ValoTheme.BUTTON_LINK);
//        fields.addComponent(more);
//        more.addClickListener(new ClickListener() {
//            @Override
//            public void buttonClick(final ClickEvent event) {
//                updateSynopsis(null, true);
//                event.getButton().setVisible(false);
//                CustomerDetailsWindow.this.focus();
//            }
//        });
//
//        return fields;
//    }

//    private void updateSynopsis(final Customer m, final boolean expand) {
//        String synopsisText = synopsis.getData().toString();
//        if (m != null) {
//            synopsisText = m.getSynopsis();
//            synopsis.setData(m.getSynopsis());
//        }
//        if (!expand) {
//            synopsisText = synopsisText.length() > 300 ? synopsisText
//                    .substring(0, 300) + "…" : synopsisText;
//
//        }
//        synopsis.setValue(synopsisText);
//    }

    public static void open(final Customer customer, final Date startTime,
            final Date endTime) {
        AddressbookEventBus.post(new CloseOpenWindowsEvent());
        Window w = new AddressDetailsWindow(customer);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}