package com.qnenet.qaddons.views.qpublichomeview;

import com.qnenet.qaddons.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("QPublicHomeView")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class QPublicHomeViewView extends Composite<VerticalLayout> {

    public QPublicHomeViewView() {
        H1 h1 = new H1();
        H4 h4 = new H4();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        h1.setText("Welcome to QuickNEasy");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        h1.setWidth("max-content");
        h4.setText("Software that powers  the QNE Community");
        h4.setWidth("max-content");
        buttonSecondary.setText("Enter");
        buttonSecondary.setWidth("min-content");
        getContent().add(h1);
        getContent().add(h4);
        getContent().add(buttonSecondary);
    }
}
