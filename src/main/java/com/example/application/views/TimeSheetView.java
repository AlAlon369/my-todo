package com.example.application.views;

import com.example.application.data.entity.TimeSheet;
import com.example.application.data.repository.TimeSheetRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import javax.annotation.security.RolesAllowed;

@Route(value = "timesheet", layout = AppLayoutBasic.class)
@RolesAllowed("USER")
public class TimeSheetView extends FormLayout {
    public TimeSheetView(TimeSheetRepository repository) {
        GridCrud<TimeSheet> crud = new GridCrud<>(TimeSheet.class);
        crud.setFindAllOperation(repository::findAll);
        crud.setAddOperation(repository::save);
        crud.setUpdateOperation(repository::save);
        crud.setDeleteOperation(repository::delete);
        CrudFormFactory<TimeSheet> crudFormFactory = crud.getCrudFormFactory();
        crudFormFactory.setVisibleProperties("employee", "date", "hours");
        add(crud);
    }
}
