package com.addressbook.ui.vaadin.view;

import com.addressbook.ui.vaadin.AddressbookNavigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
@SpringView(name=MainView.VIEWNAME)
public class MainView extends HorizontalLayout implements View{
	
	public final static String VIEWNAME = "main";
	
    public MainView() {
        setSizeFull();
        addStyleName("mainview");

        addComponent(new AddressbookMenu());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new AddressbookNavigator(content);
    }

	@Override
	public void enter(ViewChangeEvent event) {
		
	}
}
