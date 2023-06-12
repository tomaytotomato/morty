package com.kapango.application.view.integrations;

import com.kapango.application.enums.IntegrationState;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public class IntegrationsCardComponent extends ListItem {

    private String title;
    private String subTitle;
    private IntegrationState state;

    private String imgUrl;


    public IntegrationsCardComponent(String text, String title,
                                     String subTitle, IntegrationState state, String imgUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.state = state;
        this.imgUrl = imgUrl;

        buildCard();
    }

    private void buildCard() {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
            BorderRadius.LARGE);

        var div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
            Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        var image = new Image();
        image.setWidth("100%");
        image.setSrc(imgUrl);
        image.setAlt(this.getText());

        div.add(image);

        var header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);

        var subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(subTitle);

        var description = new Paragraph(
            getText());
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.setText(state.name());
        switch (state) {
            case SOON -> badge.getElement().setAttribute("theme", "badge");
            case STABLE -> badge.getElement().setAttribute("theme", "badge success");
            case EXPERIMENTAL -> badge.getElement().setAttribute("theme", "badge error");
        }

        add(div, header, subtitle, description, badge);
    }

}
