package com.kapango.application.view.integrations;

import com.kapango.application.enums.IntegrationState;
import com.kapango.application.view.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import jakarta.annotation.security.RolesAllowed;
import org.apache.commons.lang3.StringUtils;

@PageTitle("Integrations")
@Route(value = "Integration", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class IntegrationsView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public IntegrationsView() {
        constructUI();

        imageContainer.add(
            new IntegrationsCardComponent("Create incidents and postmortems from the comfort of your organisation's Slack chat.",
                "Slack",
                "Create incidents and postmortems from the comfort of your organisation's Slack chat.",
                IntegrationState.SOON, "/images/integrations/slack.png"));
        imageContainer.add(
            new IntegrationsCardComponent("Create incidents and postmortems from your Discord channel.",
                "Discord",
                "Allow Discord to manage your incidents and postmortems.",
                IntegrationState.SOON, "/images/integrations/discord.png"));
        imageContainer.add(
            new IntegrationsCardComponent("Create incidents and postmortems from your IRC channel.",
                "IRC",
                "All the cool people are still on IRC.",
                IntegrationState.SOON, "/images/integrations/irc.png"));
    }

    private void constructUI() {
        addClassNames("integrations-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        var container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        var headerContainer = new VerticalLayout();
        var header = new H2("Integrations");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);

        var description = new Paragraph(
            "Integrations allow you to connect Morty with a variety of other apps or protocols.");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        Select<IntegrationState> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems(IntegrationState.values());
        sortBy.setItemLabelGenerator(integrationState -> StringUtils.capitalize(integrationState.name()));

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);


        var additionalInfo = new Paragraph(
            new Span("If you would like to suggest an integration please make a request on our Github.  "),
            new Anchor("https://github.com/tomaytotomato/morty/issues", "Link"));
        add(container, imageContainer, additionalInfo);

        container.add(headerContainer, sortBy);

    }
}
