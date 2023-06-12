package com.kapango.application.view.admin;

import com.kapango.application.dto.UserDto;
import com.kapango.application.dto.UserRoleDto;
import com.kapango.application.mapper.UserMapper;
import com.kapango.application.usecase.UserUseCase;
import com.kapango.application.view.MainLayout;
import com.kapango.application.view.converter.LocalDateTimeToStringConverter;
import com.kapango.domain.service.UserExistsException;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
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

@PageTitle("User Management")
@Route(value = "users", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserManager extends Main implements HasComponents, HasStyle, AdminCrudView<UserDto> {

    private final UserUseCase userService;
    private final UserMapper mapper;

    GridCrud<UserDto> grid = new GridCrud<>(UserDto.class);

    @Autowired
    public UserManager(UserUseCase userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
        constructUI();
    }

    private void constructUI() {

        var tagFilter = new TextField();
        tagFilter.setClearButtonVisible(true);
        tagFilter.setPlaceholder("Username");
        add(tagFilter);

        var userRoleFilter = new MultiSelectComboBox<UserRoleDto>();
        userRoleFilter.setClearButtonVisible(true);
        userRoleFilter.setItems(userService.findAllUserRoles().stream().map(mapper::fromModel).collect(Collectors.toList()));

        CrudLayout crudLayout = grid.getCrudLayout();
        crudLayout.addFilterComponent(tagFilter);

        addClassNames("crud-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        var container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        grid.getCrudFormFactory().setUseBeanValidation(true);
        grid.getGrid().setColumns("id", "username", "firstName", "lastName", "accountType", "role", "created", "updated");
        grid.getCrudFormFactory().setConverter("created", new LocalDateTimeToStringConverter());
        grid.getCrudFormFactory().setConverter("updated", new LocalDateTimeToStringConverter());
        grid.getGrid().setColumnReorderingAllowed(false);
        grid.getCrudFormFactory().setVisibleProperties("username", "firstName", "lastName", "accountType", "role", "team");

        grid.setFindAllOperation(() -> findAllByUsername(tagFilter.getValue()));
        grid.setAddOperation(this::upsert);
        grid.setUpdateOperation(this::upsert);
        grid.setDeleteOperation(this::delete);

        setSizeFull();
        add(container, grid);
        tagFilter.addValueChangeListener(e -> grid.refreshGrid());
        tagFilter.addKeyDownListener(e -> grid.refreshGrid());
    }

    public List<UserDto> findAllByUsername(String username) {
        var users = userService.findAllByUsername(username);
        return users.stream().map(mapper::fromModel).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(mapper::fromModel).collect(Collectors.toList());
    }

    @Override
    public UserDto upsert(UserDto user) {
        try {
            var upsertedUser = userService.upsert(mapper.toModel(user));
            return mapper.fromModel(upsertedUser);
        } catch (UserExistsException e) {
            var notification = Notification
                .show("Unable to create user, they already exist!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            throw new IllegalArgumentException(e);
        }
    }
    @Override
    public void delete(UserDto userDto) {
        userService.delete(mapper.toModel(userDto));
        var notification = Notification
            .show("User deleted!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
