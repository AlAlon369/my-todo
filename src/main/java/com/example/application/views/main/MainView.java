package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class MainView extends VerticalLayout {

//    private TextField name;
//    private Button sayHello;

    public MainView() {
        Button button = new Button ("Click Me");
        TextField name = new TextField("Name");
       // add(name, button);

        HorizontalLayout h1 = new HorizontalLayout(name, button);
        h1.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        button.addClickListener(click-> Notification.show("Hello " + name.getValue()));
        add(h1);


  /*      name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

       add(name, sayHello);
       */
    }
}
