
package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Главная страница")
@Route(value = "", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class MainView extends VerticalLayout {
  public MainView() {
    add(new H1("Привет"));
  }
}
