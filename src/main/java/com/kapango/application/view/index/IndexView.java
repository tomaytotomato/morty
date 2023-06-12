package com.kapango.application.view.index;

import com.kapango.application.mapper.StatsMapper;
import com.kapango.application.usecase.StatisticsUseCase;
import com.kapango.application.view.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Bottom;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Top;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

@PageTitle("Index")
@Route(value = "index", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class IndexView extends VerticalLayout implements HasComponents, HasStyle {

    private final StatisticsUseCase statisticsUseCase;
    private final StatsMapper mapper;

    public IndexView(StatisticsUseCase statisticsUseCase, StatsMapper mapper) {
        this.statisticsUseCase = statisticsUseCase;
        this.mapper = mapper;

        addClassName("index-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);


        setSpacing(false);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);

        buildIncidentSummary();

    }


    void buildIncidentSummary() {
        var incidentStats = mapper.fromModel(statisticsUseCase.getIncidentStats());

        var summaryContainer = new VerticalLayout();
        var header = new H2("Incident Summary");
        header.addClassNames(Bottom.NONE, Top.NONE, FontSize.XLARGE);
        summaryContainer.add(header);

        OrderedList orderedList = new OrderedList(new NumberPanelComponent("Active", incidentStats.getActiveIncidents(), "good"),
            new NumberPanelComponent("False alarms", incidentStats.getFalseAlarms30Days(), "not-great-not-terrible"),
            new NumberPanelComponent("Last 30 days", incidentStats.getIncidentCount30Days(), "ok"),
            new NumberPanelComponent("This year", incidentStats.getIncidentCount(), "neutral"));
        summaryContainer.add(orderedList);
        add(summaryContainer);
    }
}
