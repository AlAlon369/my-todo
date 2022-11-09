package com.example.application.views;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Сотрудники | Vaadin CRM")
@Route(value = "Главная страница")
@RolesAllowed("USER")
public class AppLayoutBasic extends AppLayout {

    public AppLayoutBasic() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Меню");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);
    }


    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(
                createTab(VaadinIcon.SPECIALIST, "Сотрудники", EmployeesView.class),
                createTab(VaadinIcon.SPECIALIST, "Операции", OperationView.class),
                createTab(VaadinIcon.DATABASE, "Продукты", ProductView.class),
                createTab(VaadinIcon.USER_CHECK, "Клиенты", ClientsView.class),
                createTab(VaadinIcon.MONEY, "Типы расходов", CostView.class),
                createTab(VaadinIcon.SPECIALIST, "Учет операций", OperationAccountingView.class),
                createTab(VaadinIcon.HOURGLASS, "Общий табель учёта", GeneralTimeSheetView.class),
                createTab(VaadinIcon.HOURGLASS, "Табель учёта по операциям", TimeSheetView.class),
                createTab(VaadinIcon.TIME_BACKWARD, "Отчет по общему табелю", GeneralTimeSheetReportView.class),
                createTab(VaadinIcon.FACTORY, "Выпуск продукции", OutputView.class),
                createTab(VaadinIcon.MONEY_WITHDRAW, "Таблица расходов", CostAccountingView.class),
                createTab(VaadinIcon.USER_CHECK, "Заказы", BookingView.class)
        );
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> listViewClass) {
        Icon icon = viewIcon.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");
        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setTabIndex(-1);
        link.setRoute(listViewClass);

        return new Tab(link);

    }
}
