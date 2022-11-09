package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Route;

@Route(value = "operation_accounting", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class OperationAccountingView extends FormLayout {
  public OperationAccountingView() {
    add();
  }
}
