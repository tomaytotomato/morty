package com.kapango.application.view.admin;

import com.kapango.application.dto.incident.IncidentSeverityDto;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Bottom;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Horizontal;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;

@PageTitle("Incident Severity Manager")
@Route(value = "incident-severity-manager", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class IncidentSeverityManager extends Main implements HasComponents, HasStyle, AdminCrudView<IncidentSeverityDto> {

    private final IncidentUseCase incidentService;
    private final IncidentMapper mapper;

    private final GridCrud<IncidentSeverityDto> grid = new GridCrud<>(IncidentSeverityDto.class);

    @Autowired
    public IncidentSeverityManager(IncidentService incidentService, IncidentMapper mapper) {
        this.incidentService = incidentService;
        this.mapper = mapper;
        constructUI();
    }

    private void constructUI() {

        var crudLayout = grid.getCrudLayout();
        addClassNames("crud-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Bottom.LARGE, Horizontal.LARGE);

        var container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getGrid().setColumns("id", "name", "description");
        grid.getGrid().setColumnReorderingAllowed(false);
        grid.getCrudFormFactory().setVisibleProperties("name", "description");

        grid.setFindAllOperation(this::findAll);
        grid.setAddOperation(this::upsert);
        grid.setUpdateOperation(this::upsert);
        grid.setDeleteOperation(this::delete);

        setSizeFull();
        add(container, grid);
    }

    @Override
    public List<IncidentSeverityDto> findAll() {
        return incidentService.findAllIncidentSeverities("").stream().map(mapper::fromModel).collect(Collectors.toList());
    }


    @Override
    public IncidentSeverityDto upsert(IncidentSeverityDto incidentSeverity) {
        try {
            var upsertedTag = incidentService.upsert(mapper.toModel(incidentSeverity));
            return mapper.fromModel(upsertedTag);
        } catch (UserExistsException e) {
            var notification = Notification
                .show("Unable to create Incident severity level, it already exists!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new IllegalArgumentException(e);
        }
    }
    @Override

    public void delete(IncidentSeverityDto incidentSeverity) {
        incidentService.delete(mapper.toModel(incidentSeverity));
        var notification = Notification
            .show("Incident severity level deleted!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
