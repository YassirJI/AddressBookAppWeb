//package com.addressbook.ui.vaadin.view;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.vaadin.viritin.button.MButton;
//import org.vaadin.viritin.fields.MTable;
//import org.vaadin.viritin.fields.MTextField;
//import org.vaadin.viritin.fields.MValueChangeEvent;
//import org.vaadin.viritin.layouts.MHorizontalLayout;
//import org.vaadin.viritin.layouts.MVerticalLayout;
//
//import com.addressbook.model.Customer;
//import com.addressbook.service.CustomerService;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.spring.annotation.SpringView;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.CssLayout;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.TextField;
//
//@SuppressWarnings("serial")
//@UIScope
//@SpringView(name=MainView.VIEWNAME)
//public class Main2View extends CssLayout implements View {
//
//    @Autowired
//    CustomerService service;
//
//    // Instantiate and configure a Table to list PhoneBookEntries
//    MTable<Customer> entryList = new MTable<>(Customer.class)
//            .withHeight("450px")
//            .withFullWidth()
//            .withProperties("name", "phone")
//            .withColumnHeaders("Name", "Phone number");
//
//    Button addNew = new MButton(FontAwesome.PLUS, this::addNew);
//    Button delete = new MButton(FontAwesome.TRASH_O, this::deleteSelected);
//    TextField filter = new MTextField().withInputPrompt("filter...");
//
//    private void addNew(Button.ClickEvent e) {
//        entryList.setValue(null);
//     //   editEntry(new Customer());
//    }
//
//    private void deleteSelected(Button.ClickEvent e) {
//        service.delete(entryList.getValue());
//        listEntries();
//        entryList.setValue(null);
//    }
//
//    private void listEntries(String filter) {
//        //entryList.setBeans(service.getEntries(filter));
//        lazyListEntries(filter);
//    }
//
//    private void listEntries() {
//        listEntries(filter.getValue());
//    }
//
//    public void entryEditCanceled(Customer entry) {
//        editEntry(entryList.getValue());
//    }
//
//    public void entrySelected(MValueChangeEvent<Customer> event) {
//        editEntry(event.getValue());
//    }
//
//    /**
//     * Assigns the given entry to form for editing.
//     *
//     * @param entry
//     */
//    private void editEntry(Customer entry) {
//        if (entry == null) {
//            delete.setEnabled(false);
//        } else {
//            boolean persisted = entry.getId() != null;
//            if (persisted) {
//                // reattach (in case Hibernate is in use)
//    //            entry = service.loadFully(entry);
//            }
//            delete.setEnabled(persisted);
//            
//        }
//    }
//
//    public void entrySaved(Customer value) {
//        try {
//            service.save(value);
//          //  form.setVisible(false);
//        } catch (Exception e) {
//            // Most likely optimistic locking exception
//            Notification.show("Saving entity failed!", e.
//                    getLocalizedMessage(), Notification.Type.WARNING_MESSAGE);
//        }
//        // deselect the entity
//        entryList.setValue(null);
//        // refresh list
//        listEntries();
//    }
//    
//    
//    @PostConstruct
//    void init() {
//        // Add some event listners, e.g. to hook filter input to actually 
//        // filter the displayed entries
//        filter.addTextChangeListener(e -> {
//            listEntries(e.getText());
//        });
//        entryList.addMValueChangeListener(this::entrySelected);
////        form.setSavedHandler(this::entrySaved);
////        form.setResetHandler(this::entryEditCanceled);
//
//        addComponents(
//                new MVerticalLayout(
//                        new MHorizontalLayout(addNew, delete, filter),
//                        new MHorizontalLayout(entryList)
//                )
//        );
//
//        // List all entries and select first entry in the list
//        listEntries();
//        entryList.setValue(entryList.firstItemId());
//    }
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//    }
//
//    /**
//     * A simple example how to make lazy loading change all the way to the
//     * database and save JVM memory with large databases (and/or lots of users).
//     * Uses Viritin add-on and its MTable to do lazy binding.
//     */
//    private void lazyListEntries(String filter) {
//        entryList.lazyLoadFrom(
//                firstRow -> service.getEntriesPaged(filter, firstRow), 
//                () -> service.countEntries(filter)
//        );
//    }
//
//}
