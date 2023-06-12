package com.kapango.application.view.admin;

import com.kapango.application.dto.incident.IncidentTypeDto;
import com.kapango.application.mapper.IncidentMapper;
import com.kapango.application.usecase.IncidentUseCase;
import com.kapango.application.view.MainLayout;
import com.kapango.domain.service.IncidentService;
import com.kapango.domain.service.UserExistsException;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Bottom;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Horizontal;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.CrudLayout;

@PageTitle("Incident Type Manager")
@Route(value = "incident-type-manager", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class IncidentTypeManager extends Main implements HasComponents, HasStyle, AdminCrudView<IncidentTypeDto> {

    private final IncidentUseCase incidentService;
    private final IncidentMapper mapper;
    private final GridCrud<IncidentTypeDto> grid = new GridCrud<>(IncidentTypeDto.class);

    private final TextField filter = new TextField();

    @Autowired
    public IncidentTypeManager(IncidentService incidentService, IncidentMapper mapper) {
        this.incidentService = incidentService;
        this.mapper = mapper;
        constructUI();
    }

    private void constructUI() {
        addClassNames("crud-view");
        addClassNames(Bottom.LARGE, Horizontal.LARGE);

        filter.setClearButtonVisible(true);
        filter.setPlaceholder("Incident type name");
        CrudLayout crudLayout = grid.getCrudLayout();
        crudLayout.addFilterComponent(filter);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> grid.refreshGrid());

        var container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getGrid().removeAllColumns();
        grid.getGrid().addColumn(IncidentTypeDto::getId).setHeader("id").setWidth("5%");
        grid.getGrid().addColumn(IncidentTypeDto::getName).setHeader("Name").setWidth("30%");
        grid.getGrid().addColumn(IncidentTypeDto::getDescription).setHeader("Description").setWidth("65%");
        grid.getCrudFormFactory().setVisibleProperties("name", "description");
        grid.getCrudFormFactory().setFieldType("description", TextArea.class);
        grid.setFindAllOperation(this::findAll);
        grid.setAddOperation(this::upsert);
        grid.setUpdateOperation(this::upsert);
        grid.setDeleteOperation(this::delete);
        grid.getGrid().addItemDoubleClickListener(event -> grid.getUpdateButton().click());
        setSizeFull();
        add(container, grid);
    }

    @Override
    public List<IncidentTypeDto> findAll() {
        return incidentService.findAllIncidentTypes(filter.getValue()).stream().map(mapper::fromModel).collect(Collectors.toList());
    }


    @Override
    public IncidentTypeDto upsert(IncidentTypeDto incidentType) {
        try {
            var upsertedTag = incidentService.upsert(mapper.toModel(incidentType));
            return mapper.fromModel(upsertedTag);
        } catch (UserExistsException e) {
            var notification = Notification
                .show("Unable to create Incident type, it already exists!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new IllegalArgumentException(e);
        }
    }

    @Override

    public void delete(IncidentTypeDto incidentType) {
        incidentService.delete(mapper.toModel(incidentType));
        var notification = Notification
            .show("Incident type deleted!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
