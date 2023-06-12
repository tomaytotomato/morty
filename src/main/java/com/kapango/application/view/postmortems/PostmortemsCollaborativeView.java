package com.kapango.application.view.postmortems;

import com.kapango.application.dto.PostmortemDto;
import com.kapango.application.dto.TagDto;
import com.kapango.application.mapper.PostmortemMapper;
import com.kapango.application.usecase.PostmortemUseCase;
import com.kapango.application.view.MainLayout;
import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.PermitAll;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Postmortems Collaborative")
@Route(value = "postmortems-collaborative/:ID?/:action?(edit)", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class PostmortemsCollaborativeView extends Div implements BeforeEnterObserver {

    private final String POSTMORTEM_ID = "ID";
    private final String POSTMORTEMDTO_EDIT_ROUTE_TEMPLATE = "postmortems-collaborative/%s/edit";

    private final Grid<PostmortemDto> grid = new Grid<>(PostmortemDto.class, false);

    CollaborationAvatarGroup avatarGroup;

    private TextField reference;
    private TextField name;
    private DateTimePicker created;
    private DateTimePicker updated;
    private DateTimePicker completed;
    private TextField description;
    private TextField status;
    private TextField source;
    private TextField severity;
    private TextField rootCause;
    private MultiSelectComboBox<TagDto> tags;
    private TextField author;
    private Checkbox createdByHuman;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final CollaborationBinder<PostmortemDto> binder;

    private PostmortemDto postmortemDto;

    private final PostmortemUseCase postmortemService;

    private final PostmortemMapper mapper;


    @Autowired
    public PostmortemsCollaborativeView(PostmortemUseCase postmortemService, PostmortemMapper mapper) {
        this.postmortemService = postmortemService;
        this.mapper = mapper;
        addClassNames("postmortems-collaborative-view");

        UserInfo userInfo = new UserInfo(UUID.randomUUID().toString(), "Steve Lange");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        avatarGroup = new CollaborationAvatarGroup(userInfo, null);
        avatarGroup.getStyle().set("visibility", "hidden");

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("reference").setAutoWidth(true);
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("created").setAutoWidth(true);
        grid.addColumn("updated").setAutoWidth(true);
        grid.addColumn("completed").setAutoWidth(true);
        grid.addColumn("description").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);
        grid.addColumn("source").setAutoWidth(true);
        grid.addColumn("severity").setAutoWidth(true);
        grid.addColumn("rootCause").setAutoWidth(true);
        grid.addColumn("tags").setAutoWidth(true);
        grid.addColumn("author").setAutoWidth(true);
        LitRenderer<PostmortemDto> createdByHumanRenderer = LitRenderer.<PostmortemDto>of(
                "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", createdByHuman -> createdByHuman.isCreatedByHuman() ? "check" : "minus")
                .withProperty("color",
                        createdByHuman -> createdByHuman.isCreatedByHuman()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(createdByHumanRenderer).setHeader("Created By Human").setAutoWidth(true);

        grid.setItems(query -> postmortemService.findAll(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream().map(mapper::fromModel));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(POSTMORTEMDTO_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PostmortemsCollaborativeView.class);
            }
        });

        // Configure Form
        binder = new CollaborationBinder<>(PostmortemDto.class, userInfo);

        binder.forField(tags, Set.class, TagDto.class);

        binder.bindInstanceFields(this);
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.postmortemDto == null) {
                    this.postmortemDto = new PostmortemDto();
                }
                binder.writeBean(this.postmortemDto);
                postmortemService.upsert(mapper.toModel(this.postmortemDto));
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(PostmortemsCollaborativeView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var postmortemId = event.getRouteParameters().get(POSTMORTEM_ID).map(Integer::parseInt);
        if (postmortemId.isPresent()) {
           var postmortem = postmortemService.findById(postmortemId.get()).map(mapper::fromModel);
            if (postmortem.isPresent()) {
                populateForm(postmortem.get());
            } else {
                Notification.show(
                        String.format("The requested postmortemDto was not found, ID = %d", postmortemId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PostmortemsCollaborativeView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        reference = new TextField("Reference");
        name = new TextField("Name");
        created = new DateTimePicker("Created");
        created.setStep(Duration.ofSeconds(1));
        updated = new DateTimePicker("Updated");

        updated.setStep(Duration.ofSeconds(1));
        completed = new DateTimePicker("Completed");
        completed.setStep(Duration.ofSeconds(1));
        description = new TextField("Description");
        status = new TextField("Status");
        source = new TextField("Source");
        severity = new TextField("Severity");
        rootCause = new TextField("Root Cause");
        tags = new MultiSelectComboBox<>("Tags");
        author = new TextField("Author");
        createdByHuman = new Checkbox("Created By Human");
        formLayout.add(reference, name, created, updated, completed, description, status, source, severity, rootCause,
                tags, author, createdByHuman);

        editorDiv.add(avatarGroup, formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(PostmortemDto value) {
        this.postmortemDto = value;
        String topic = null;
        if (this.postmortemDto != null && this.postmortemDto.getId() != null) {
            topic = "postmortemDto/" + this.postmortemDto.getId();
            avatarGroup.getStyle().set("visibility", "visible");
        } else {
            avatarGroup.getStyle().set("visibility", "hidden");
        }
        binder.setTopic(topic, () -> this.postmortemDto);
        avatarGroup.setTopic(topic);

    }
}
