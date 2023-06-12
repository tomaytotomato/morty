package com.kapango.application.view.admin;

import com.kapango.application.dto.TagDto;
import com.kapango.application.mapper.TagMapper;
import com.kapango.application.usecase.TagUseCase;
import com.kapango.application.view.MainLayout;
import com.kapango.application.view.converter.LocalDateTimeToStringConverter;
import com.kapango.domain.service.UserExistsException;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.layout.CrudLayout;

@PageTitle("Tag Manager")
@Route(value = "tag-manager", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class TagManager extends Main implements HasComponents, HasStyle, AdminCrudView<TagDto> {

    private final TagUseCase tagService;
    private final TagMapper mapper;

    GridCrud<TagDto> grid = new GridCrud<>(TagDto.class);

    @Autowired
    public TagManager(TagUseCase tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
        constructUI();
    }

    private void constructUI() {

        var tagFilter = new TextField();
        tagFilter.setClearButtonVisible(true);
        tagFilter.setPlaceholder("Tag name");
        add(tagFilter);
        CrudLayout crudLayout = grid.getCrudLayout();
        crudLayout.addFilterComponent(tagFilter);

        addClassNames("crud-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        var container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getGrid().setColumns("id", "name", "created");
        grid.getCrudFormFactory().setConverter("created", new LocalDateTimeToStringConverter());
        grid.getCrudFormFactory().setConverter("updated", new LocalDateTimeToStringConverter());
        grid.getGrid().setColumnReorderingAllowed(false);
        grid.getCrudFormFactory().setVisibleProperties("name");

        grid.setFindAllOperation(() -> findAllByTagName(tagFilter.getValue()));
        grid.setAddOperation(this::upsert);
        grid.setUpdateOperation(this::upsert);
        grid.setDeleteOperation(this::delete);

        setSizeFull();
        add(container, grid);
        tagFilter.addValueChangeListener(e -> grid.refreshGrid());
        tagFilter.addKeyDownListener(e -> grid.refreshGrid());
    }

    public List<TagDto> findAllByTagName(String tagName) {
        var tags = tagService.findAllByTagName(tagName);
        return tags.stream().map(mapper::fromModel).collect(Collectors.toList());
    }

    @Override
    public List<TagDto> findAll() {
        return tagService.findAll().stream().map(mapper::fromModel).collect(Collectors.toList());
    }

    @Override

    public TagDto upsert(TagDto newTag) {
        try {
            var upsertedTag = tagService.upsert(mapper.toModel(newTag));
            return mapper.fromModel(upsertedTag);
        } catch (UserExistsException e) {
            var notification = Notification
                .show("Unable to create tag, it already exists!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new IllegalArgumentException(e);
        }
    }
    @Override

    public void delete(TagDto tagDto) {
        tagService.delete(mapper.toModel(tagDto));
        var notification = Notification
            .show("Tag deleted!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
