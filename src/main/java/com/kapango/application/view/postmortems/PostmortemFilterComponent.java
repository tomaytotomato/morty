package com.kapango.application.view.postmortems;

import com.kapango.application.dto.PostmortemFilterDto;
import com.kapango.application.enums.PostmortemStatus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class PostmortemFilterComponent extends Div implements FilterComponent {

    private final PostmortemFilterDto filter = new PostmortemFilterDto();
    private final IntegerField id = new IntegerField("Postmortem Id");
    private final TextField reference = new TextField("Postmortem Reference");
    private final TextField name = new TextField("Postmortem Name");
    private final ComboBox<PostmortemStatus> status = new ComboBox<>("Status", PostmortemStatus.values());

    private final Binder<PostmortemFilterDto> binder;

    public PostmortemFilterComponent(Runnable onSearch) {

        setWidthFull();
        addClassName("filter-layout");
        addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM, LumoUtility.BoxSizing.BORDER);
        name.setPlaceholder("First or last name");

        // Action buttons
        Button resetBtn = new Button("Reset");
        resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        resetBtn.addClickListener(e -> {
            id.clear();
            name.clear();
            reference.clear();
            status.clear();
        });
        Button searchBtn = new Button("Search");
        searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchBtn.addClickListener(e -> onSearch.run());

        Div actions = new Div(resetBtn, searchBtn);
        actions.addClassName(LumoUtility.Gap.SMALL);
        actions.addClassName("actions");

        add(id, reference, name, status);

        status.setItemLabelGenerator(PostmortemStatus::getName);

        binder = new Binder<>(PostmortemFilterDto.class);

        binder.forField(id).bind("id");
        binder.forField(name).bind("name");
        binder.forField(reference).bind("reference");
        binder.forField(status).bind("status");
    }

    public PostmortemFilterDto getFilter() {
        return filter;
    }
}