package com.kapango.application.view.postmortems;

import com.kapango.application.dto.PostmortemDto;
import com.kapango.application.mapper.PostmortemMapper;
import com.kapango.application.usecase.PostmortemUseCase;
import com.kapango.application.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Postmortems")
@Route(value = "postmortems", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed("USER")

public class PostmortemsView extends Div {

    private Grid<PostmortemDto> grid;

    private final PostmortemFilterComponent filter;
    private final PostmortemUseCase postmortemService;

    private final PostmortemMapper mapper;

    @Autowired
    public PostmortemsView(PostmortemUseCase postmortemService, PostmortemMapper mapper) {
        this.postmortemService = postmortemService;
        this.mapper = mapper;
        setSizeFull();
        addClassNames("gridwith-filters-view");

        filter = new PostmortemFilterComponent(this::refreshGrid);
        var layout = new VerticalLayout(createMobileFilters(), filter, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        var mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER, LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        var mobileIcon = new Icon("lumo", "plus");
        var filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filter.getClassNames().contains("visible")) {
                filter.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filter.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }



    private Component createGrid() {
        grid = new Grid<>(PostmortemDto.class, false);
        grid.addColumn("id").setAutoWidth(true);
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("created").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);

        grid.setItems(query -> postmortemService.findAll(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)), mapper.toFilterModel(filter.getFilter()) ).stream()
            .map(mapper::fromModel));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

}
