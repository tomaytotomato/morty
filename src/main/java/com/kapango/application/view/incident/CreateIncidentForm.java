package com.kapango.application.view.incident;

import com.kapango.application.dto.TagDto;
import com.kapango.application.dto.incident.CreateIncidentDto;
import com.kapango.application.dto.incident.IncidentSeverityDto;
import com.kapango.application.dto.incident.IncidentTypeDto;
import com.kapango.application.mapper.IncidentMapper;
import com.kapango.application.usecase.IncidentUseCase;
import com.kapango.application.view.MainLayout;
import com.kapango.domain.service.IncidentExistsException;
import com.kapango.domain.service.IncidentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Create Incident")
@Route(value = "create-incident", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed("USER")
public class CreateIncidentForm extends Div {

    private final IncidentUseCase incidentService;

    private final IncidentMapper mapper;

    private TextField name = new TextField("Incident Name");

    private TextField reference = new TextField("Incident Reference");

    private TextArea description = new TextArea("Description");

    private ComboBox<IncidentTypeDto> incidentType = new ComboBox<>("Incident Type");
    private Paragraph incidentTypeDescription = new Paragraph();

    private ComboBox<IncidentSeverityDto> severity = new ComboBox<>("Severity");

    private Paragraph severityDescription = new Paragraph();

    private MultiSelectComboBox<TagDto> tags = new MultiSelectComboBox<>("Tags");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Binder<CreateIncidentDto> binder = new Binder<>(CreateIncidentDto.class);

    public CreateIncidentForm(IncidentService incidentService, IncidentMapper mapper) {
        this.incidentService = incidentService;
        this.mapper = mapper;
        addClassName("form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());


        binder.bindInstanceFields(this);
        clearForm();
        configureComboboxFields();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(event -> {
            var newIncident = binder.getBean();

            try {
                incidentService.upsert(mapper.toModel(newIncident));
                Notification.show(newIncident.getClass().getSimpleName() + " details stored.");
            } catch (IncidentExistsException e) {
                var notification = Notification
                    .show(e.getLocalizedMessage());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            clearForm();
        });
    }

    private void configureComboboxFields() {
        var incidentTypes = incidentService.findAllIncidentTypes(null)
            .stream().map(mapper::fromModel)
            .toList();

        var severities = incidentService.findAllIncidentSeverities(null)
            .stream().map(mapper::fromModel)
            .toList();

        incidentType.setItems(incidentTypes);
        incidentType.setItemLabelGenerator(IncidentTypeDto::getName);
        incidentType.addValueChangeListener(event -> incidentType.setHelperText(event.getValue().getDescription()));
        incidentType.setValue(incidentTypes.get(0));

        severity.setItems(severities);
        severity.setItemLabelGenerator(IncidentSeverityDto::getName);
        severity.addValueChangeListener(event -> severity.setHelperText(event.getValue().getDescription()));
        severity.setValue(severities.get(0));

    }

    private void clearForm() {
        binder.setBean(new CreateIncidentDto());
    }

    private Component createTitle() {
        return new H3("Incident information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, reference, description, incidentType, severity, tags);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}
